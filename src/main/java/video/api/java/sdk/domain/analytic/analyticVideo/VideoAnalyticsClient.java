package video.api.java.sdk.domain.analytic.analyticVideo;

import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.exception.ResponseException;

import java.util.Iterator;


public interface VideoAnalyticsClient {
    AnalyticVideo get(String videoId, String period) throws ResponseException, IllegalArgumentException;

    Iterator<AnalyticVideo> list() throws ResponseException, IllegalArgumentException;

    Iterator<AnalyticVideo> search(QueryParams queryParams) throws ResponseException, IllegalArgumentException;
}
