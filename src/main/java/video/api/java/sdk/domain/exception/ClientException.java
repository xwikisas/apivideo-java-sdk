package video.api.java.sdk.domain.exception;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;

public class ClientException extends ResponseException {

    public ClientException(HttpResponse<JsonNode> response, String message) {
        super(response, "Client ERROR -> Client Exception\n" + "\n Response Code : " + response.getStatus() + "\n" + message);
    }

}
