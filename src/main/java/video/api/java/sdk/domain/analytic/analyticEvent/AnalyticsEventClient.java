package video.api.java.sdk.domain.analytic.analyticEvent;

import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.infrastructure.pagination.PageIterator;


public interface AnalyticsEventClient {

    PageIterator<AnalyticEvent> list(String sessionId) throws ResponseException, IllegalArgumentException;

    PageIterator<AnalyticEvent> search(QueryParams queryParams, String sessionId) throws ResponseException, IllegalArgumentException;


}
