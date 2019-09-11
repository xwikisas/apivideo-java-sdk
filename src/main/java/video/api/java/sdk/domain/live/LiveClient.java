package video.api.java.sdk.domain.live;


import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.infrastructure.pagination.PageIterator;


public interface LiveClient {
    Live get(String liveStreamId) throws ResponseException;

    Live create(Live live) throws ResponseException;

    PageIterator<Live> search(QueryParams queryParams) throws ResponseException, IllegalArgumentException;

    Live uploadThumbnail(String liveStreamId, String thumbnailSource) throws ResponseException, IllegalArgumentException;

    Live update(Live live) throws ResponseException;

    int delete(String liveStreamId) throws ResponseException;

    PageIterator<Live> list() throws ResponseException, IllegalArgumentException;
}
