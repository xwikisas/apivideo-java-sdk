package video.api.java.sdk.domain.analytic.analyticLive;

import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.exception.ResponseException;

import java.util.Iterator;


public interface LiveStreamAnalyticsClient {
    AnalyticLive get(String liveStreamId, String period) throws ResponseException;

    Iterable<AnalyticLive> list() throws ResponseException, IllegalArgumentException;

    Iterable<AnalyticLive> search(QueryParams queryParams) throws Exception;
}
