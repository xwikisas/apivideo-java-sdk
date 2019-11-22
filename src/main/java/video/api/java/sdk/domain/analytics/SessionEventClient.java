package video.api.java.sdk.domain.analytics;

import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.exception.ResponseException;


public interface SessionEventClient {
    Iterable<SessionEvent> list(String sessionId) throws ResponseException;

    Iterable<SessionEvent> search(QueryParams queryParams, String sessionId) throws ResponseException;
}
