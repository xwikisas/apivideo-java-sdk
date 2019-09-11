package video.api.java.sdk.domain.exception;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;

public class ResponseException extends Exception {


    private final HttpResponse<JsonNode> response;

    public ResponseException(HttpResponse<JsonNode> response, String message) {
        super(message + "--> Body : " + response.getBody().toString());
        this.response = response;
    }

    public int getStatus() {
        return this.response.getStatus();
    }

}
