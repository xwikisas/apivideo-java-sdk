package video.api.java.sdk.infrastructure.unirest.account;

import kong.unirest.HttpRequest;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import video.api.java.sdk.domain.RequestExecutor;
import video.api.java.sdk.domain.account.Account;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.infrastructure.unirest.RequestFactory;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;

import static kong.unirest.HttpMethod.GET;

public class AccountClient {
    private final RequestFactory          requestFactory;
    private final JsonSerializer<Account> serializer;
    private final RequestExecutor         requestExecutor;

    public AccountClient(RequestFactory requestFactory, JsonSerializer<Account> serializer, RequestExecutor requestExecutor) {
        this.requestFactory  = requestFactory;
        this.serializer      = serializer;
        this.requestExecutor = requestExecutor;
    }

    public Account get() throws ResponseException {
        HttpRequest request = requestFactory.create(GET, "/account");

        HttpResponse<JsonNode> response = requestExecutor.executeJson(request);

        return serializer.deserialize(response.getBody().getObject());
    }

}