package video.api.java.sdk.infrastructure.unirest;

import kong.unirest.HttpRequest;
import kong.unirest.JsonNode;
import video.api.java.sdk.domain.exception.ResponseException;

public interface RequestExecutor {
    JsonNode executeJson(HttpRequest<?> request) throws ResponseException;
}
