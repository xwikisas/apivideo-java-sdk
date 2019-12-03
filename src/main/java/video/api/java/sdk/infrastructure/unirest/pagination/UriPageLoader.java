package video.api.java.sdk.infrastructure.unirest.pagination;

import kong.unirest.JsonNode;
import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.domain.pagination.Page;
import video.api.java.sdk.domain.pagination.PageQuery;
import video.api.java.sdk.infrastructure.pagination.PageLoader;
import video.api.java.sdk.infrastructure.pagination.PageSerializer;
import video.api.java.sdk.infrastructure.unirest.RequestExecutor;
import video.api.java.sdk.infrastructure.unirest.request.RequestBuilder;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;

public class UriPageLoader<T> implements PageLoader<T> {
    private final RequestBuilder    requestBuilder;
    private final RequestExecutor   requestExecutor;
    private final JsonSerializer<T> serializer;

    public UriPageLoader(RequestBuilder requestBuilder, RequestExecutor requestExecutor, JsonSerializer<T> serializer) {
        this.requestBuilder  = requestBuilder;
        this.requestExecutor = requestExecutor;
        this.serializer      = serializer;
    }

    @Override
    public Page<T> load(PageQuery pageQuery) throws ResponseException {
        RequestBuilder request = requestBuilder
                .withQueryParams(pageQuery.toMap());

        JsonNode responseBody = requestExecutor.executeJson(request);

        return new PageSerializer<>(serializer).deserialize(responseBody.getObject());
    }
}
