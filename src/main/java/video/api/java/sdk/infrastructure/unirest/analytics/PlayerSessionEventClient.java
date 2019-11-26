package video.api.java.sdk.infrastructure.unirest.analytics;

import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.analytics.PlayerSessionEvent;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.infrastructure.pagination.IteratorIterable;
import video.api.java.sdk.infrastructure.pagination.PageIterator;
import video.api.java.sdk.infrastructure.unirest.RequestBuilder;
import video.api.java.sdk.infrastructure.unirest.RequestExecutor;
import video.api.java.sdk.infrastructure.unirest.pagination.UriPageLoader;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;

public class PlayerSessionEventClient implements video.api.java.sdk.domain.analytics.PlayerSessionEventClient {

    private final RequestBuilder                     requestBuilder;
    private final JsonSerializer<PlayerSessionEvent> serializer;
    private final RequestExecutor                    requestExecutor;

    public PlayerSessionEventClient(RequestBuilder requestBuilder, JsonSerializer<PlayerSessionEvent> serializer, RequestExecutor requestExecutor) {
        this.requestBuilder  = requestBuilder;
        this.serializer      = serializer;
        this.requestExecutor = requestExecutor;
    }

    public Iterable<PlayerSessionEvent> list(String sessionId) throws ResponseException, IllegalArgumentException {
        return new IteratorIterable<>(new PageIterator<>(new UriPageLoader<>(
                "/analytics/sessions/" + sessionId + "/events",
                requestBuilder,
                requestExecutor,
                serializer
        ), new QueryParams()));
    }
}
