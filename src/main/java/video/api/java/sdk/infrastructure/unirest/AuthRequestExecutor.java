package video.api.java.sdk.infrastructure.unirest;

import kong.unirest.HttpRequest;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import org.json.JSONObject;
import video.api.java.sdk.domain.exception.ClientException;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.domain.exception.ServerException;

public class AuthRequestExecutor implements RequestExecutor {

    private final RequestBuilder requestBuilder;
    private final String         apiKey;
    private       String         accessToken;
    private       String         refreshToken;

    public AuthRequestExecutor(RequestBuilder requestBuilder, String apiKey) {
        this.requestBuilder = requestBuilder;
        this.apiKey         = apiKey;
    }

    public JsonNode executeJson(HttpRequest<?> request) throws ResponseException {
        if (accessToken == null) {
            this.requestAccessToken();
        }

        request.headerReplace("Authorization", "Bearer " + accessToken);
        HttpResponse<JsonNode> response = request.asJson();

        if (response.getStatus() == 401 && response.getBody().getObject().getString("hint").equals("Access token is invalid")) {
            this.refreshToken();

            return executeJson(request);
        }

        if (response.getStatus() >= 500) {
            throw new ServerException("A server issue occurred.", response.getBody() , response.getStatus());
        }

        if (response.getStatus() >= 400) {
            throw new ClientException("A client issue occurred.", response.getBody(), response.getStatus());
        }

        return response.getBody();
    }

    private void requestAccessToken() throws ClientException {
        JSONObject requestBody = new JSONObject()
            .put("apiKey", apiKey);

        HttpResponse<JsonNode> response = requestBuilder.post("/auth/api-key")
                .body(requestBody)
                .asJson();

        JSONObject responseBody = response.getBody().getObject();

        if (response.getStatus() >= 400) {
            throw new ClientException("Authentication failed.", response.getBody(), response.getStatus());
        }

        this.setAccessToken(responseBody.get("access_token"), responseBody.get("refresh_token"));
    }

    private void setAccessToken(Object accessToken, Object refreshToken) {
        this.accessToken  = accessToken.toString();
        this.refreshToken = refreshToken.toString();
    }

    private void refreshToken() throws ClientException {
        JSONObject authBody = new JSONObject();
        authBody.accumulate("refreshToken", this.refreshToken);
        HttpResponse<JsonNode> response = requestBuilder.post("/auth/refresh")
                .header("Content-Type", "application/json; utf-8")
                .header("Accept", "application/json")
                .body(authBody)
                .asJson();

        JSONObject responseBody = response.getBody().getObject();
        if (response.getStatus() >= 400) {
            throw new ClientException("Authentication failed.", response.getBody(), response.getStatus());
        }

        this.setAccessToken(responseBody.get("access_token"), responseBody.get("refresh_token"));
    }
}


