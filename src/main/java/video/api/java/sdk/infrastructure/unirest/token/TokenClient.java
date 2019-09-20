package video.api.java.sdk.infrastructure.unirest.token;

import kong.unirest.HttpRequest;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.infrastructure.unirest.AuthRequestExecutor;

public class TokenClient {


    private final AuthRequestExecutor requestExecutor;
    private final String              baseUri;

    public TokenClient(AuthRequestExecutor requestExecutor, String baseUri) {
        this.requestExecutor = requestExecutor;
        this.baseUri         = baseUri;
    }


    public String get() throws ResponseException {


        HttpRequest request = Unirest.post(baseUri + "/tokens");

        HttpResponse<JsonNode> response = requestExecutor.executeJson(request);

        return getAccountResponse(response);
    }

    private String getAccountResponse(HttpResponse<JsonNode> response) {

        return response.getBody().getObject().getString("token");
    }
}
