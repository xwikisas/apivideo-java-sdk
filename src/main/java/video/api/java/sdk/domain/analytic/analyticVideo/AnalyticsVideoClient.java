package video.api.java.sdk.domain.analytic.analyticVideo;

import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.infrastructure.pagination.PageIterator;


public interface AnalyticsVideoClient {
    AnalyticVideo get(String videoId, String period) throws ResponseException, IllegalArgumentException;

    PageIterator<AnalyticVideo> list() throws ResponseException, IllegalArgumentException;

    PageIterator<AnalyticVideo> search(QueryParams queryParams) throws ResponseException, IllegalArgumentException;
}
