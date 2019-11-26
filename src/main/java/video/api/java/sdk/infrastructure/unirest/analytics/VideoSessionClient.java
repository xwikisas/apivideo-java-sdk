package video.api.java.sdk.infrastructure.unirest.analytics;

import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.analytics.PlayerSession;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.infrastructure.pagination.IteratorIterable;
import video.api.java.sdk.infrastructure.pagination.PageIterator;
import video.api.java.sdk.infrastructure.unirest.RequestBuilder;
import video.api.java.sdk.infrastructure.unirest.RequestExecutor;
import video.api.java.sdk.infrastructure.unirest.pagination.UriPageLoader;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;

public class VideoSessionClient implements video.api.java.sdk.domain.analytics.VideoSessionClient {
    private final RequestBuilder                requestBuilder;
    private final JsonSerializer<PlayerSession> serializer;
    private final RequestExecutor               requestExecutor;

    public VideoSessionClient(RequestBuilder requestBuilder, JsonSerializer<PlayerSession> serializer, RequestExecutor requestExecutor) {
        this.requestBuilder  = requestBuilder;
        this.serializer      = serializer;
        this.requestExecutor = requestExecutor;
    }

    public Iterable<PlayerSession> list(String videoId) throws ResponseException, IllegalArgumentException {
        return list(videoId, null, new QueryParams());
    }

    public Iterable<PlayerSession> list(String videoId, String period) throws ResponseException, IllegalArgumentException {
        return list(videoId, period, new QueryParams());
    }

    public Iterable<PlayerSession> list(String videoId, String period, QueryParams queryParams) throws ResponseException, IllegalArgumentException {
        queryParams.period = period;

        return new IteratorIterable<>(new PageIterator<>(new UriPageLoader<>(
                "/analytics/videos/" + videoId,
                requestBuilder,
                requestExecutor,
                serializer
        ), queryParams));
    }
}
