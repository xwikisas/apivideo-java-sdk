package video.api.java.sdk.domain.analytic.analyticLive;

import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.infrastructure.pagination.PageIterator;


public interface AnalyticsLiveClient {
    AnalyticLive get(String liveStreamId, String period) throws ResponseException;

    PageIterator<AnalyticLive> list() throws ResponseException, IllegalArgumentException;

    PageIterator<AnalyticLive> search(QueryParams queryParams) throws Exception;
}
