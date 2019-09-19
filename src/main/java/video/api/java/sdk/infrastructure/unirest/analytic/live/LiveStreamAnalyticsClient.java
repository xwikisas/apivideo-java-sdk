package video.api.java.sdk.infrastructure.unirest.analytic.live;

import kong.unirest.HttpRequest;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.json.JSONObject;
import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.RequestExecutor;
import video.api.java.sdk.domain.analytic.analyticLive.AnalyticLive;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.domain.pagination.Page;
import video.api.java.sdk.infrastructure.pagination.IteratorIterable;
import video.api.java.sdk.infrastructure.pagination.PageIterator;
import video.api.java.sdk.infrastructure.pagination.PageLoader;

import java.util.Iterator;

public class LiveStreamAnalyticsClient implements video.api.java.sdk.domain.analytic.analyticLive.LiveStreamAnalyticsClient, PageLoader<AnalyticLive> {


    private final AnalyticLiveJsonSerializer serializer;
    private final RequestExecutor            requestExecutor;
    private final String                     baseUri;

    public LiveStreamAnalyticsClient(AnalyticLiveJsonSerializer serializer, RequestExecutor requestExecutor, String baseUri) {

        this.serializer      = serializer;
        this.requestExecutor = requestExecutor;
        this.baseUri         = baseUri;

    }


    public AnalyticLive get(String liveStreamId, String period) throws ResponseException, IllegalArgumentException {


        JSONObject parameters = new JSONObject();
        if (period != null) {
            parameters.put("period", period);
        }


        QueryParams queryParams = new QueryParams();
        String      url         = queryParams.queryBuilder(parameters, "/analytics/live-streams/" + liveStreamId + "/");


        HttpResponse<JsonNode> response = requestExecutor.executeJson(Unirest.get(url));

        return getAnalyticLiveResponse(response);

    }


    public Iterable<AnalyticLive> list() throws ResponseException, IllegalArgumentException {
        QueryParams queryParams = new QueryParams();
        return new IteratorIterable<>(new PageIterator<>(this, queryParams));
    }


    public Iterable<AnalyticLive> search(QueryParams queryParams) throws ResponseException, IllegalArgumentException {
        return new IteratorIterable<>(new PageIterator<>(this, queryParams));

    }

    /////////////////////////Functions//////////////////////////////


    public String toString(AnalyticLive analyticLive) {

        return serializer.serialize(analyticLive).toString();
    }

    public JSONObject toJSONObject(AnalyticLive analyticLive) {

        return serializer.serialize(analyticLive);
    }

    private AnalyticLive getAnalyticLiveResponse(HttpResponse<JsonNode> response) {

        return serializer.deserialize(response.getBody().getObject());
    }

    @Override
    public Page<AnalyticLive> load(QueryParams queryParams) throws ResponseException {
        String      url     = queryParams.create(baseUri + "/analytics/live-streams/");
        HttpRequest request = Unirest.get(url);

        HttpResponse<JsonNode> response = requestExecutor.executeJson(request);

        return new Page<>(
                serializer.deserialize(response.getBody().getObject().getJSONArray("data")),
                response.getBody().getObject().getJSONObject("pagination").getInt("pagesTotal"),
                response.getBody().getObject().getJSONObject("pagination").getInt("currentPage")
        );

    }
}
