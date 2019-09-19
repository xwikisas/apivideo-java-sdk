package video.api.java.sdk.domain.analytic.analyticEvent;

import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.exception.ResponseException;


public interface SessionEventAnalyticsClient {
    Iterable<AnalyticEvent> list(String sessionId) throws ResponseException;

    Iterable<AnalyticEvent> search(QueryParams queryParams, String sessionId) throws ResponseException;
}
