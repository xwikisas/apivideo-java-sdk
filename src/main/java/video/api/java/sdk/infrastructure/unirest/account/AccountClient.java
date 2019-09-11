package video.api.java.sdk.infrastructure.unirest.account;

import kong.unirest.HttpRequest;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import video.api.java.sdk.domain.RequestExecutor;
import video.api.java.sdk.domain.account.Account;
import video.api.java.sdk.domain.exception.ResponseException;

public class AccountClient {
    private final AccountJsonSerializer serializer;
    private final RequestExecutor       requestExecutor;
    private final String                baseUri;


    public AccountClient(AccountJsonSerializer serializer, RequestExecutor requestExecutor, String baseUri) {
        this.serializer      = serializer;
        this.requestExecutor = requestExecutor;
        this.baseUri         = baseUri;
    }


    public Account get() throws ResponseException {


        HttpRequest request = Unirest.get(baseUri + "/account");

        HttpResponse<JsonNode> response = requestExecutor.executeJson(request);

        return getAccountResponse(response);
    }

    private Account getAccountResponse(HttpResponse<JsonNode> response) {

        return serializer.deserialize(response.getBody().getObject());
    }

}