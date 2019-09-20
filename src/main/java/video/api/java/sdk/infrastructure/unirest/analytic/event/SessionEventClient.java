package video.api.java.sdk.infrastructure.unirest.analytic.event;

import kong.unirest.HttpRequest;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.json.JSONObject;
import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.RequestExecutor;
import video.api.java.sdk.domain.analytic.analyticEvent.SessionEvent;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.domain.pagination.Page;
import video.api.java.sdk.infrastructure.pagination.IteratorIterable;
import video.api.java.sdk.infrastructure.pagination.PageIterator;
import video.api.java.sdk.infrastructure.pagination.PageLoader;
import video.api.java.sdk.infrastructure.pagination.PageSerializer;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;

public class SessionEventClient implements video.api.java.sdk.domain.analytic.analyticEvent.SessionEventClient, PageLoader<SessionEvent> {

    private final JsonSerializer<SessionEvent> serializer;
    private final RequestExecutor              requestExecutor;
    private final String                       baseUri;
    private       String                       sessionId;

    public SessionEventClient(JsonSerializer<SessionEvent> serializer, RequestExecutor requestExecutor, String baseUri) {
        this.serializer      = serializer;
        this.requestExecutor = requestExecutor;
        this.baseUri         = baseUri;

    }


    public Iterable<SessionEvent> list(String sessionId) throws ResponseException, IllegalArgumentException {
        QueryParams queryParams = new QueryParams();
        this.sessionId = sessionId;
        return new IteratorIterable<>(new PageIterator<>(this, queryParams));
    }

    public Iterable<SessionEvent> search(QueryParams queryParams, String sessionId) throws ResponseException, IllegalArgumentException {
        this.sessionId = sessionId;
        return new IteratorIterable<>(new PageIterator<>(this, queryParams));
    }


    /////////////////////////Functions//////////////////////////////

    @Override
    public Page<SessionEvent> load(QueryParams queryParams) throws ResponseException {
        String      url     = queryParams.create(baseUri + "/analytics/sessions/" + sessionId + "/events");
        HttpRequest request = Unirest.get(url);

        HttpResponse<JsonNode> response = requestExecutor.executeJson(request);

        JSONObject body = response.getBody().getObject();

        return new PageSerializer<>(serializer).deserialize(body);
    }

}
