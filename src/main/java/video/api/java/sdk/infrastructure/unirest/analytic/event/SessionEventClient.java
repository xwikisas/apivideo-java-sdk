package video.api.java.sdk.infrastructure.unirest.analytic.event;

import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.RequestExecutor;
import video.api.java.sdk.domain.analytic.SessionEvent;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.infrastructure.pagination.IteratorIterable;
import video.api.java.sdk.infrastructure.pagination.PageIterator;
import video.api.java.sdk.infrastructure.unirest.pagination.UriPageLoader;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;

public class SessionEventClient implements video.api.java.sdk.domain.analytic.SessionEventClient {

    private final JsonSerializer<SessionEvent> serializer;
    private final RequestExecutor              requestExecutor;
    private final String                       baseUri;

    public SessionEventClient(JsonSerializer<SessionEvent> serializer, RequestExecutor requestExecutor, String baseUri) {
        this.serializer      = serializer;
        this.requestExecutor = requestExecutor;
        this.baseUri         = baseUri;
    }

    public Iterable<SessionEvent> list(String sessionId) throws ResponseException, IllegalArgumentException {
        return search(new QueryParams(), sessionId);
    }

    public Iterable<SessionEvent> search(QueryParams queryParams, String sessionId) throws ResponseException, IllegalArgumentException {
        return new IteratorIterable<>(new PageIterator<>(new UriPageLoader<>(baseUri + "/analytics/sessions/" + sessionId + "/events", requestExecutor, serializer), queryParams));
    }

}
