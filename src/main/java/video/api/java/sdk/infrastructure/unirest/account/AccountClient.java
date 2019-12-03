package video.api.java.sdk.infrastructure.unirest.account;

import kong.unirest.JsonNode;
import video.api.java.sdk.domain.account.Account;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.infrastructure.unirest.RequestExecutor;
import video.api.java.sdk.infrastructure.unirest.request.RequestBuilder;
import video.api.java.sdk.infrastructure.unirest.request.RequestBuilderFactory;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;

import static kong.unirest.HttpMethod.GET;

public class AccountClient {
    private final RequestBuilderFactory   requestBuilderFactory;
    private final JsonSerializer<Account> serializer;
    private final RequestExecutor         requestExecutor;

    public AccountClient(RequestBuilderFactory requestBuilderFactory, JsonSerializer<Account> serializer, RequestExecutor requestExecutor) {
        this.requestBuilderFactory = requestBuilderFactory;
        this.serializer            = serializer;
        this.requestExecutor       = requestExecutor;
    }

    public Account get() throws ResponseException {
        RequestBuilder request = requestBuilderFactory
                .create(GET, "/account");

        JsonNode responseBody = requestExecutor.executeJson(request);

        return serializer.deserialize(responseBody.getObject());
    }

}