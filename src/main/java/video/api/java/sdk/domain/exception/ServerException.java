package video.api.java.sdk.domain.exception;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;

public class ServerException extends ResponseException {


    public ServerException(HttpResponse<JsonNode> response, String message) {
        super(response, "Server ERROR ->" + "\n Response Code : " + response.getStatus() + "\n" + message);


    }


}
