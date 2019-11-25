package video.api.java.sdk.infrastructure.unirest.token;

import kong.unirest.HttpRequest;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.infrastructure.unirest.AuthRequestExecutor;
import video.api.java.sdk.infrastructure.unirest.RequestFactory;

import static kong.unirest.HttpMethod.POST;

public class TokenClient {
    private final RequestFactory requestFactory;
    private final AuthRequestExecutor requestExecutor;

    public TokenClient(RequestFactory requestFactory, AuthRequestExecutor requestExecutor) {
        this.requestFactory  = requestFactory;
        this.requestExecutor = requestExecutor;
    }

    public String get() throws ResponseException {
        HttpRequest request = requestFactory.create(POST, "/tokens");

        HttpResponse<JsonNode> response = requestExecutor.executeJson(request);

        // TODO use normalizer
        return response.getBody().getObject().getString("token");
    }

}
