package video.api.java.sdk.infrastructure.unirest.pagination;

import kong.unirest.HttpRequest;
import kong.unirest.JsonNode;
import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.domain.pagination.Page;
import video.api.java.sdk.infrastructure.pagination.PageLoader;
import video.api.java.sdk.infrastructure.pagination.PageSerializer;
import video.api.java.sdk.infrastructure.unirest.RequestBuilder;
import video.api.java.sdk.infrastructure.unirest.RequestExecutor;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;

public class UriPageLoader<T> implements PageLoader<T> {
    private final String            url;
    private final RequestBuilder    requestBuilder;
    private final RequestExecutor   requestExecutor;
    private final JsonSerializer<T> serializer;

    public UriPageLoader(String url, RequestBuilder requestBuilder, RequestExecutor requestExecutor, JsonSerializer<T> serializer) {
        this.url             = url;
        this.requestBuilder  = requestBuilder;
        this.requestExecutor = requestExecutor;
        this.serializer      = serializer;
    }

    @Override
    public Page<T> load(QueryParams queryParams) throws ResponseException {
        HttpRequest request = requestBuilder
                .get(url)
                .queryString(queryParams.createJSONObject().toMap());

        JsonNode responseBody = requestExecutor.executeJson(request);

        return new PageSerializer<>(serializer).deserialize(responseBody.getObject());
    }
}
