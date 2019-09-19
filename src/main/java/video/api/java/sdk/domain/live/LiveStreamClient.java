package video.api.java.sdk.domain.live;


import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.exception.ResponseException;

import java.util.Iterator;


public interface LiveStreamClient {
    LiveStream get(String liveStreamId) throws ResponseException;

    LiveStream create(LiveStream liveStream) throws ResponseException;

    Iterator<LiveStream> search(QueryParams queryParams) throws ResponseException, IllegalArgumentException;

    LiveStream uploadThumbnail(String liveStreamId, String thumbnailSource) throws ResponseException, IllegalArgumentException;

    LiveStream update(LiveStream liveStream) throws ResponseException;

    int delete(String liveStreamId) throws ResponseException;

    Iterator<LiveStream> list() throws ResponseException, IllegalArgumentException;
}
