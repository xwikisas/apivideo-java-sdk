package video.api.java.sdk.domain.analytic;

import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.exception.ResponseException;

import java.net.URISyntaxException;


public interface LiveStreamSessionClient {
    Iterable<PlayerSession> list(String liveStreamId) throws ResponseException, IllegalArgumentException;

    Iterable<PlayerSession> list(String liveStreamId, String period) throws ResponseException, IllegalArgumentException;

    Iterable<PlayerSession> list(String liveStreamId, String period, QueryParams queryParams) throws Exception;
}
