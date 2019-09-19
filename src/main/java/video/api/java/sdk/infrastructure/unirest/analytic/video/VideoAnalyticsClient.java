package video.api.java.sdk.infrastructure.unirest.analytic.video;

import kong.unirest.HttpRequest;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.json.JSONObject;
import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.RequestExecutor;
import video.api.java.sdk.domain.analytic.analyticVideo.AnalyticVideo;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.domain.pagination.Page;
import video.api.java.sdk.infrastructure.pagination.IteratorIterable;
import video.api.java.sdk.infrastructure.pagination.PageIterator;
import video.api.java.sdk.infrastructure.pagination.PageLoader;


public class VideoAnalyticsClient implements video.api.java.sdk.domain.analytic.analyticVideo.VideoAnalyticsClient, PageLoader<AnalyticVideo> {

    private final AnalyticVideoJsonSerializer serializer;
    private final RequestExecutor             requestExecutor;
    private final String                      baseUri;

    public VideoAnalyticsClient(AnalyticVideoJsonSerializer serializer, RequestExecutor requestExecutor, String baseUri) {

        this.serializer      = serializer;
        this.requestExecutor = requestExecutor;
        this.baseUri         = baseUri;

    }


    public AnalyticVideo get(String videoId, String period) throws ResponseException, IllegalArgumentException {

        JSONObject parameters = new JSONObject();
        if (period != null) {
            parameters.put("period", period);
        }
        QueryParams            queryParams = new QueryParams();
        String                 url         = queryParams.queryBuilder(parameters, "/analytics/videos/" + videoId);
        HttpResponse<JsonNode> response    = requestExecutor.executeJson(Unirest.get(url));
        return getAnalyticVideoResponse(response);


    }


    public Iterable<AnalyticVideo> list() throws ResponseException, IllegalArgumentException {
        QueryParams queryParams = new QueryParams();
        return new IteratorIterable<>(new PageIterator<>(this, queryParams));
    }


    public Iterable<AnalyticVideo> search(QueryParams queryParams) throws ResponseException, IllegalArgumentException {

        return new IteratorIterable<>(new PageIterator<>(this, queryParams));

    }

    /////////////////////////Functions//////////////////////////////


    public String toString(AnalyticVideo analyticVideo) {
        return serializer.serialize(analyticVideo).toString();

    }

    public JSONObject toJSONObject(AnalyticVideo analyticVideo) {
        return serializer.serialize(analyticVideo);

    }

    private AnalyticVideo getAnalyticVideoResponse(HttpResponse<JsonNode> response) {

        return serializer.deserialize(response.getBody().getObject());
    }


    @Override
    public Page<AnalyticVideo> load(QueryParams queryParams) throws ResponseException {

        String      url     = queryParams.create(baseUri + "/analytics/videos/");
        HttpRequest request = Unirest.get(url);

        HttpResponse<JsonNode> response = requestExecutor.executeJson(request);

        return new Page<>(
                serializer.deserialize(response.getBody().getObject().getJSONArray("data")),
                response.getBody().getObject().getJSONObject("pagination").getInt("pagesTotal"),
                response.getBody().getObject().getJSONObject("pagination").getInt("currentPage")
        );

    }
}
