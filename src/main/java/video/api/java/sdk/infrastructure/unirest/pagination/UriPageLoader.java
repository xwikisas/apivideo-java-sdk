package video.api.java.sdk.infrastructure.unirest.pagination;

import kong.unirest.HttpMethod;
import kong.unirest.HttpRequest;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import org.json.JSONObject;
import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.RequestExecutor;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.domain.pagination.Page;
import video.api.java.sdk.infrastructure.pagination.PageLoader;
import video.api.java.sdk.infrastructure.pagination.PageSerializer;
import video.api.java.sdk.infrastructure.unirest.RequestFactory;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;

public class UriPageLoader<T> implements PageLoader<T> {
    private final String            url;
    private final RequestFactory    requestFactory;
    private final RequestExecutor   requestExecutor;
    private final JsonSerializer<T> serializer;

    public UriPageLoader(String url, RequestFactory requestFactory, RequestExecutor requestExecutor, JsonSerializer<T> serializer) {
        this.url             = url;
        this.requestFactory  = requestFactory;
        this.requestExecutor = requestExecutor;
        this.serializer      = serializer;
    }

    @Override
    public Page<T> load(QueryParams queryParams) throws ResponseException {
        HttpRequest request = requestFactory.create(HttpMethod.GET, url)
                .queryString(queryParams.createJSONObject().toMap());

        HttpResponse<JsonNode> response = requestExecutor.executeJson(request);

        JSONObject body = response.getBody().getObject();

        return new PageSerializer<>(serializer).deserialize(body);
    }
}
