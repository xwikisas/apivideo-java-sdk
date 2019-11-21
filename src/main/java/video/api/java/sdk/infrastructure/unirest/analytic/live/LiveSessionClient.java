package video.api.java.sdk.infrastructure.unirest.analytic.live;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.RequestExecutor;
import video.api.java.sdk.domain.analytic.analyticLive.LiveSession;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.infrastructure.pagination.IteratorIterable;
import video.api.java.sdk.infrastructure.pagination.PageIterator;
import video.api.java.sdk.infrastructure.unirest.pagination.UriPageLoader;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class LiveSessionClient implements video.api.java.sdk.domain.analytic.analyticLive.LiveSessionClient {
    private final JsonSerializer<LiveSession> serializer;
    private final RequestExecutor             requestExecutor;
    private final String                      baseUri;

    public LiveSessionClient(JsonSerializer<LiveSession> serializer, RequestExecutor requestExecutor, String baseUri) {
        this.serializer      = serializer;
        this.requestExecutor = requestExecutor;
        this.baseUri         = baseUri;
    }

    public LiveSession get(String liveStreamId, final String period) throws ResponseException, IllegalArgumentException, URISyntaxException {
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

    public Iterable<LiveSession> list() throws ResponseException, IllegalArgumentException {
        return search(new QueryParams());
    }

    public Iterable<LiveSession> search(QueryParams queryParams) throws ResponseException, IllegalArgumentException {
        // TODO remove route
        return new IteratorIterable<>(new PageIterator<>(new UriPageLoader<>(baseUri + "/analytics/live-streams", requestExecutor, serializer), queryParams));
    }

    private LiveSession getAnalyticLiveResponse(HttpResponse<JsonNode> response) {
        return serializer.deserialize(response.getBody().getObject());
    }

}
