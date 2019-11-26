package video.api.java.sdk.infrastructure.unirest.token;

import kong.unirest.HttpRequest;
import kong.unirest.JsonNode;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.infrastructure.unirest.AuthRequestExecutor;
import video.api.java.sdk.infrastructure.unirest.RequestBuilder;

public class TokenClient {
    private final RequestBuilder      requestBuilder;
    private final AuthRequestExecutor requestExecutor;

    public TokenClient(RequestBuilder requestBuilder, AuthRequestExecutor requestExecutor) {
        this.requestBuilder  = requestBuilder;
        this.requestExecutor = requestExecutor;
    }

    public String get() throws ResponseException {
        HttpRequest request = requestBuilder
                .post("/tokens");

        JsonNode responseBody = requestExecutor.executeJson(request);

        // TODO use serializer
        return responseBody.getObject().getString("token");
    }
}
