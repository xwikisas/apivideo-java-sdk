package video.api.java.sdk.infrastructure.unirest.analytic.live;

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

public class LiveStreamSessionClient implements video.api.java.sdk.domain.analytic.LiveStreamSessionClient {
    private final JsonSerializer<PlayerSession> serializer;
    private final RequestExecutor               requestExecutor;
    private final String                        baseUri;

    public LiveStreamSessionClient(JsonSerializer<PlayerSession> serializer, RequestExecutor requestExecutor, String baseUri) {
        this.serializer      = serializer;
        this.requestExecutor = requestExecutor;
        this.baseUri         = baseUri;
    }


    public PlayerSession get(String liveStreamId, final String period) throws ResponseException, IllegalArgumentException, URISyntaxException {
        List<NameValuePair> parameters = new ArrayList<>();
        if (period != null) {
            parameters.add(new BasicNameValuePair("period", period));
        }

        String url = new URIBuilder(baseUri + "/analytics/live-streams/" + liveStreamId)
                .addParameters(parameters)
                .toString();

        HttpResponse<JsonNode> response = requestExecutor.executeJson(Unirest.get(url));

        return getAnalyticLiveResponse(response);
    }

    public Iterable<PlayerSession> list(String videoId) throws ResponseException, IllegalArgumentException {
        return list(videoId, null, new QueryParams());
    }

    public Iterable<PlayerSession> list(String videoId, String period) throws ResponseException, IllegalArgumentException {
        return list(videoId, period, new QueryParams());
    }

    public Iterable<PlayerSession> list(String liveStreamId, String period, QueryParams queryParams) throws ResponseException, IllegalArgumentException {
        queryParams.period = period;

        return new IteratorIterable<>(new PageIterator<>(new UriPageLoader<>(
                baseUri + "/analytics/live-streams/" + liveStreamId,
                requestExecutor,
                serializer
        ), queryParams));
    }

    private PlayerSession getAnalyticLiveResponse(HttpResponse<JsonNode> response) {
        return serializer.deserialize(response.getBody().getObject());
    }

}
