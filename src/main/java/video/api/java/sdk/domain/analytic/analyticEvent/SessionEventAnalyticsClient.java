package video.api.java.sdk.domain.analytic.analyticEvent;

import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.exception.ResponseException;

import java.util.Iterator;


public interface SessionEventAnalyticsClient {

    Iterator<AnalyticEvent> list(String sessionId) throws ResponseException, IllegalArgumentException;

    Iterator<AnalyticEvent> search(QueryParams queryParams, String sessionId) throws ResponseException, IllegalArgumentException;


}
