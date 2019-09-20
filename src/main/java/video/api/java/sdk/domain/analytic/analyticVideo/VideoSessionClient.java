package video.api.java.sdk.domain.analytic.analyticVideo;

import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.exception.ResponseException;


public interface VideoSessionClient {
    VideoSession get(String videoId, String period) throws ResponseException, IllegalArgumentException;

    Iterable<VideoSession> list() throws ResponseException, IllegalArgumentException;

    Iterable<VideoSession> search(QueryParams queryParams) throws ResponseException, IllegalArgumentException;
}
