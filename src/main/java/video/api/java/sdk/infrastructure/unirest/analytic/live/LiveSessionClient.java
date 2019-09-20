package video.api.java.sdk.infrastructure.unirest.analytic.live;

import kong.unirest.HttpRequest;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.json.JSONObject;
import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.RequestExecutor;
import video.api.java.sdk.domain.analytic.analyticLive.LiveSession;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.domain.pagination.Page;
import video.api.java.sdk.infrastructure.pagination.IteratorIterable;
import video.api.java.sdk.infrastructure.pagination.PageIterator;
import video.api.java.sdk.infrastructure.pagination.PageLoader;
import video.api.java.sdk.infrastructure.pagination.PageSerializer;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;

public class LiveSessionClient implements video.api.java.sdk.domain.analytic.analyticLive.LiveSessionClient, PageLoader<LiveSession> {
    private final JsonSerializer<LiveSession> serializer;
    private final RequestExecutor             requestExecutor;
    private final String                      baseUri;

    public LiveSessionClient(JsonSerializer<LiveSession> serializer, RequestExecutor requestExecutor, String baseUri) {
        this.serializer      = serializer;
        this.requestExecutor = requestExecutor;
        this.baseUri         = baseUri;
    }


    public LiveSession get(String liveStreamId, String period) throws ResponseException, IllegalArgumentException {


        JSONObject parameters = new JSONObject();
        if (period != null) {
            parameters.put("period", period);
        }


        QueryParams queryParams = new QueryParams();
        String      url         = queryParams.queryBuilder(parameters, "/analytics/live-streams/" + liveStreamId + "/");


        HttpResponse<JsonNode> response = requestExecutor.executeJson(Unirest.get(url));

        return getAnalyticLiveResponse(response);

    }


    public Iterable<LiveSession> list() throws ResponseException, IllegalArgumentException {
        QueryParams queryParams = new QueryParams();
        return new IteratorIterable<>(new PageIterator<>(this, queryParams));
    }


    public Iterable<LiveSession> search(QueryParams queryParams) throws ResponseException, IllegalArgumentException {
        return new IteratorIterable<>(new PageIterator<>(this, queryParams));

    }

    /////////////////////////Functions//////////////////////////////


    public String toString(LiveSession liveSession) {

        return serializer.serialize(liveSession).toString();
    }

    public JSONObject toJSONObject(LiveSession liveSession) {

        return serializer.serialize(liveSession);
    }

    private LiveSession getAnalyticLiveResponse(HttpResponse<JsonNode> response) {

        return serializer.deserialize(response.getBody().getObject());
    }

    @Override
    public Page<LiveSession> load(QueryParams queryParams) throws ResponseException {
        String      url     = queryParams.create(baseUri + "/analytics/live-streams/");
        HttpRequest request = Unirest.get(url);

        HttpResponse<JsonNode> response = requestExecutor.executeJson(request);

        JSONObject body = response.getBody().getObject();

        return new PageSerializer<>(serializer).deserialize(body);
    }
}
