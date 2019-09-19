package video.api.java.sdk.infrastructure.unirest;

import kong.unirest.HttpRequest;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.json.JSONObject;
import video.api.java.sdk.domain.RequestExecutor;
import video.api.java.sdk.domain.exception.ClientException;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.domain.exception.ServerException;

public class AuthRequestExecutor implements RequestExecutor {

    private final String baseUri;
    private final String apiKey;
    private       String tokenType;
    private       String accessToken;
    private       String refreshToken;

    public AuthRequestExecutor(String baseUri, String apiKey) {
        this.baseUri      = baseUri;
        this.apiKey       = apiKey;
        this.tokenType    = "Bearer";
        this.accessToken  = null;
        this.refreshToken = null;

    }


    public HttpResponse<JsonNode> executeJson(HttpRequest<?> request) throws ResponseException {
        if (accessToken == null) {
            this.requestAccessToken();
        }

        request.headerReplace("Authorization", this.tokenType + " " + accessToken);
        HttpResponse<JsonNode> response = request.asJson();

        if (response.getStatus() == 401 && response.getHeaders().all().get(1).getValue().equals("application/problem+json") && response.getBody().getObject().getString("hint").equals("Access token is invalid")) {
            this.refreshToken();

            return executeJson(request);
        }

        if (response.getStatus() >= 500) {
            throw new ServerException(response, " REQUEST = " + request.getHttpMethod() + ", Url : " + request.getUrl() + " ,body -->" + response.getBody().toString());
        }
        if (response.getStatus() >= 400) {
            throw new ClientException(response, " REQUEST = " + request.getHttpMethod() + ", Url : " + request.getUrl() + " ,body -->" + response.getBody().toString());
        }

        return response;
    }

    private JSONObject requestAccessToken() throws ClientException {
        JSONObject authBody = new JSONObject();
        authBody.accumulate("apiKey", apiKey);

        HttpResponse<JsonNode> jsonResponse = Unirest.post(baseUri + "/auth/api-key")
                .header("Content-Type", "application/json; utf-8")
                .header("Accept", "application/json")
                .body(authBody)
                .asJson();
        JSONObject responseBody = jsonResponse.getBody().getObject();

        if (jsonResponse.getStatus() >= 400) {
            throw new ClientException(jsonResponse, "Authentication Failed : " + responseBody.toString());
        }

        return this.setAccessToken(responseBody.get("token_type"), responseBody.get("access_token"), responseBody.get("refresh_token"));
    }

    private JSONObject setAccessToken(Object tokenType, Object accessToken, Object refreshToken) {
        this.tokenType    = tokenType.toString();
        this.accessToken  = accessToken.toString();
        this.refreshToken = refreshToken.toString();
        JSONObject token = new JSONObject();
        token.put("tokenType", this.tokenType);
        token.put("accessToken", this.accessToken);
        token.put("refreshToken", this.refreshToken);
        return token;
    }

    private JSONObject refreshToken() throws ClientException {
        JSONObject authBody = new JSONObject();
        authBody.accumulate("refreshToken", this.refreshToken);
        HttpResponse<JsonNode> jsonResponse = Unirest.post(baseUri + "/auth/refresh")
                .header("Content-Type", "application/json; utf-8")
                .header("Accept", "application/json")
                .body(authBody)
                .asJson();

        JSONObject responseBody = jsonResponse.getBody().getObject();
        if (jsonResponse.getStatus() >= 400) {
            throw new ClientException(jsonResponse, "Authentication Failed : " + responseBody.toString());
        }

        return this.setAccessToken(responseBody.get("token_type"), responseBody.get("access_token"), responseBody.get("refresh_token"));
    }
}


