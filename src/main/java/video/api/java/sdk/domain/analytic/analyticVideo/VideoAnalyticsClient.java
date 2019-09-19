package video.api.java.sdk.domain.analytic.analyticVideo;

import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.exception.ResponseException;


public interface VideoAnalyticsClient {
    AnalyticVideo get(String videoId, String period) throws ResponseException, IllegalArgumentException;

    Iterable<AnalyticVideo> list() throws ResponseException, IllegalArgumentException;

    Iterable<AnalyticVideo> search(QueryParams queryParams) throws ResponseException, IllegalArgumentException;
}
