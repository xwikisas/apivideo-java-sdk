package video.api.java.sdk.infrastructure.unirest.account;

import kong.unirest.HttpRequest;
import kong.unirest.JsonNode;
import video.api.java.sdk.domain.account.Account;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.infrastructure.unirest.RequestBuilder;
import video.api.java.sdk.infrastructure.unirest.RequestExecutor;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;

public class AccountClient {
    private final RequestBuilder          requestBuilder;
    private final JsonSerializer<Account> serializer;
    private final RequestExecutor         requestExecutor;

    public AccountClient(RequestBuilder requestBuilder, JsonSerializer<Account> serializer, RequestExecutor requestExecutor) {
        this.requestBuilder  = requestBuilder;
        this.serializer      = serializer;
        this.requestExecutor = requestExecutor;
    }

    public Account get() throws ResponseException {
        HttpRequest request = requestBuilder.get("/account");

        JsonNode responseBody = requestExecutor.executeJson(request);

        return serializer.deserialize(responseBody.getObject());
    }

}