package video.api.java.sdk.domain.live;


import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.exception.ResponseException;


public interface LiveStreamClient {
    LiveStream get(String liveStreamId) throws ResponseException;

    LiveStream create(LiveStream liveStream) throws ResponseException;

    Iterable<LiveStream> search(QueryParams queryParams) throws ResponseException, IllegalArgumentException;

    LiveStream uploadThumbnail(String liveStreamId, String thumbnailSource) throws ResponseException, IllegalArgumentException;

    LiveStream update(LiveStream liveStream) throws ResponseException;

    void delete(String liveStreamId) throws ResponseException;

    Iterable<LiveStream> list() throws ResponseException, IllegalArgumentException;
}
