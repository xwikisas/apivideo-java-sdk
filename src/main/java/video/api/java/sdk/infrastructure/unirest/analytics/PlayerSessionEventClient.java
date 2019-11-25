package video.api.java.sdk.infrastructure.unirest.analytics;

import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.RequestExecutor;
import video.api.java.sdk.domain.analytics.PlayerSessionEvent;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.infrastructure.pagination.IteratorIterable;
import video.api.java.sdk.infrastructure.pagination.PageIterator;
import video.api.java.sdk.infrastructure.unirest.pagination.UriPageLoader;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;

public class PlayerSessionEventClient implements video.api.java.sdk.domain.analytics.PlayerSessionEventClient {

    private final JsonSerializer<PlayerSessionEvent> serializer;
    private final RequestExecutor                    requestExecutor;
    private final String                             baseUri;

    public PlayerSessionEventClient(JsonSerializer<PlayerSessionEvent> serializer, RequestExecutor requestExecutor, String baseUri) {
        this.serializer      = serializer;
        this.requestExecutor = requestExecutor;
        this.baseUri         = baseUri;
    }

    public Iterable<PlayerSessionEvent> list(String sessionId) throws ResponseException, IllegalArgumentException {
        return new IteratorIterable<>(new PageIterator<>(new UriPageLoader<>(
                baseUri + "/analytics/sessions/" + sessionId + "/events",
                requestExecutor,
                serializer
        ), new QueryParams()));
    }
}