package video.api.java.sdk.infrastructure.unirest.analytic.event;

import kong.unirest.HttpRequest;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.json.JSONObject;
import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.RequestExecutor;
import video.api.java.sdk.domain.analytic.analyticEvent.AnalyticEvent;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.infrastructure.pagination.Page;
import video.api.java.sdk.infrastructure.pagination.PageIterator;
import video.api.java.sdk.infrastructure.pagination.PageLoader;

public class SessionEventAnalyticsClient implements video.api.java.sdk.domain.analytic.analyticEvent.AnalyticsEventClient, PageLoader<AnalyticEvent> {

    private final AnalyticEventJsonSerializer serializer;
    private final RequestExecutor             requestExecutor;
    private final String                      baseUri;
    private       String                      sessionId;

    public SessionEventAnalyticsClient(AnalyticEventJsonSerializer serializer, RequestExecutor requestExecutor, String baseUri) {

        this.serializer      = serializer;
        this.requestExecutor = requestExecutor;
        this.baseUri         = baseUri;

    }


    public PageIterator<AnalyticEvent> list(String sessionId) throws ResponseException, IllegalArgumentException {
        QueryParams queryParams = new QueryParams();
        this.sessionId = sessionId;
        return new PageIterator<>(this, queryParams);
    }

    public PageIterator<AnalyticEvent> search(QueryParams queryParams, String sessionId) throws ResponseException, IllegalArgumentException {
        this.sessionId = sessionId;
        return new PageIterator<>(this, queryParams);
    }


    /////////////////////////Functions//////////////////////////////

    public String toString(AnalyticEvent analyticEvent) {
        return serializer.serialize(analyticEvent).toString();
    }

    public JSONObject toJSONObject(AnalyticEvent analyticEvent) {
        return serializer.serialize(analyticEvent);
    }

    private AnalyticEvent getAnalyticEventResponse(HttpResponse<JsonNode> response) {

        return serializer.deserialize(response.getBody().getObject());
    }


    @Override
    public Page<AnalyticEvent> load(QueryParams queryParams) throws ResponseException {
        String      url     = queryParams.create(baseUri + "/analytics/sessions/" + sessionId + "/events");
        HttpRequest request = Unirest.get(url);

        HttpResponse<JsonNode> response = requestExecutor.executeJson(request);

        return new Page<>(
                serializer.deserialize(response.getBody().getObject().getJSONArray("data")),
                response.getBody().getObject().getJSONObject("pagination").getInt("pagesTotal"),
                response.getBody().getObject().getJSONObject("pagination").getInt("currentPage")
        );

    }

}
