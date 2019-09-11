package video.api.java.sdk.domain;

import kong.unirest.HttpRequest;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import video.api.java.sdk.domain.exception.ResponseException;

public interface RequestExecutor {
    HttpResponse<JsonNode> executeJson(HttpRequest<?> request) throws ResponseException;
}
