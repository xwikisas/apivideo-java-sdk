package video.api.java.sdk.infrastructure.unirest.analytic.video;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.RequestExecutor;
import video.api.java.sdk.domain.analytic.PlayerSession;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.infrastructure.pagination.IteratorIterable;
import video.api.java.sdk.infrastructure.pagination.PageIterator;
import video.api.java.sdk.infrastructure.unirest.pagination.UriPageLoader;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


public class VideoSessionClient implements video.api.java.sdk.domain.analytic.VideoSessionClient {
    private final JsonSerializer<PlayerSession> serializer;
    private final RequestExecutor               requestExecutor;
    private final String                        baseUri;

    public VideoSessionClient(JsonSerializer<PlayerSession> serializer, RequestExecutor requestExecutor, String baseUri) {

        this.serializer      = serializer;
        this.requestExecutor = requestExecutor;
        this.baseUri         = baseUri;

    }

    public PlayerSession get(String videoId) throws ResponseException, IllegalArgumentException, URISyntaxException {
        return get(videoId, null);
    }

    public PlayerSession get(String videoId, String period) throws ResponseException, IllegalArgumentException, URISyntaxException {
        List<NameValuePair> parameters = new ArrayList<>();
        if (period != null) {
            parameters.add(new BasicNameValuePair("period", period));
        }

        String url = new URIBuilder(baseUri + "/analytics/videos/" + videoId)
                .addParameters(parameters)
                .toString();

        HttpResponse<JsonNode> response = requestExecutor.executeJson(Unirest.get(url));

        return getAnalyticVideoResponse(response);
    }

    public Iterable<PlayerSession> list(String videoId) throws ResponseException, IllegalArgumentException {
        return list(videoId, null, new QueryParams());
    }

    public Iterable<PlayerSession> list(String videoId, String period) throws ResponseException, IllegalArgumentException {
        return list(videoId, period, new QueryParams());
    }

    public Iterable<PlayerSession> list(String videoId, String period, QueryParams queryParams) throws ResponseException, IllegalArgumentException {
        queryParams.period = period;

        return new IteratorIterable<>(new PageIterator<>(new UriPageLoader<>(baseUri + "/analytics/videos/" + videoId, requestExecutor, serializer), queryParams));
    }

    /////////////////////////Functions//////////////////////////////

    private PlayerSession getAnalyticVideoResponse(HttpResponse<JsonNode> response) {

        return serializer.deserialize(response.getBody().getObject());
    }

}
