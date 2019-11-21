package video.api.java.sdk.domain.analytic.analyticVideo;

import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.exception.ResponseException;

import java.net.URISyntaxException;


public interface VideoSessionClient {
    Iterable<VideoSession> list(String videoId) throws ResponseException, IllegalArgumentException;

    Iterable<VideoSession> search(String videoId, String period, QueryParams queryParams) throws ResponseException, IllegalArgumentException;
}
