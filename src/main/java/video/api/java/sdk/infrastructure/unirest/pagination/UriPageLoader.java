package video.api.java.sdk.infrastructure.unirest.pagination;

import kong.unirest.HttpRequest;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.json.JSONObject;
import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.RequestExecutor;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.domain.pagination.Page;
import video.api.java.sdk.infrastructure.pagination.PageLoader;
import video.api.java.sdk.infrastructure.pagination.PageSerializer;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;

public class UriPageLoader<T> implements PageLoader<T> {
    private final String            url;
    private final RequestExecutor   requestExecutor;
    private final JsonSerializer<T> serializer;

    public UriPageLoader(String url, RequestExecutor requestExecutor, JsonSerializer<T> serializer) {
        this.url             = url;
        this.requestExecutor = requestExecutor;
        this.serializer      = serializer;
    }

    @Override
    public Page<T> load(QueryParams queryParams) throws ResponseException {
        HttpRequest request = Unirest.get(queryParams.create(url));

        HttpResponse<JsonNode> response = requestExecutor.executeJson(request);

        JSONObject body = response.getBody().getObject();

        return new PageSerializer<>(serializer).deserialize(body);
    }
}
