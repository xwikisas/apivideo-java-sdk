package video.api.java.sdk.domain.analytic.analyticLive;

import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.exception.ResponseException;


public interface LiveSessionClient {
    LiveSession get(String liveStreamId, String period) throws ResponseException;

    Iterable<LiveSession> list() throws ResponseException, IllegalArgumentException;

    Iterable<LiveSession> search(QueryParams queryParams) throws Exception;
}
