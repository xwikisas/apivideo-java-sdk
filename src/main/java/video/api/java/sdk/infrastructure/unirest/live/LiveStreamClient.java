package video.api.java.sdk.infrastructure.unirest.live;

import kong.unirest.JsonNode;
import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.domain.live.LiveStream;
import video.api.java.sdk.domain.pagination.PageQuery;
import video.api.java.sdk.infrastructure.pagination.IteratorIterable;
import video.api.java.sdk.infrastructure.pagination.PageIterator;
import video.api.java.sdk.infrastructure.unirest.RequestExecutor;
import video.api.java.sdk.infrastructure.unirest.pagination.UriPageLoader;
import video.api.java.sdk.infrastructure.unirest.request.RequestBuilder;
import video.api.java.sdk.infrastructure.unirest.request.RequestBuilderFactory;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;

import java.io.File;
import java.io.IOException;

import static kong.unirest.HttpMethod.*;

public class LiveStreamClient implements video.api.java.sdk.domain.live.LiveStreamClient {

    private final RequestBuilderFactory      requestBuilderFactory;
    private final JsonSerializer<LiveStream> serializer;
    private final RequestExecutor            requestExecutor;

    public LiveStreamClient(RequestBuilderFactory requestBuilderFactory, JsonSerializer<LiveStream> serializer, RequestExecutor requestExecutor) {
        this.requestBuilderFactory = requestBuilderFactory;
        this.serializer            = serializer;
        this.requestExecutor       = requestExecutor;
    }

    public LiveStream get(String liveStreamId) throws ResponseException {
        RequestBuilder request = requestBuilderFactory
                .create(GET, "/live-streams/" + liveStreamId);

        JsonNode responseBody = requestExecutor.executeJson(request);

        return serializer.deserialize(responseBody.getObject());
    }

    public LiveStream create(LiveStream liveStream) throws ResponseException {
        RequestBuilder request = requestBuilderFactory
                .create(POST, "/live-streams")
                .withJson(serializer.serialize(liveStream));

        JsonNode responseBody = requestExecutor.executeJson(request);

        return serializer.deserialize(responseBody.getObject());
    }

    public LiveStream uploadThumbnail(String liveStreamId, File file) throws ResponseException, IOException {
        RequestBuilder request = requestBuilderFactory
                .create(POST, "/live-streams/" + liveStreamId + "/thumbnail")
                .withFile(file);

        JsonNode responseBody = requestExecutor.executeJson(request);

        return serializer.deserialize(responseBody.getObject());
    }

    public LiveStream update(LiveStream liveStream) throws ResponseException {
        RequestBuilder request = requestBuilderFactory
                .create(PATCH, "/live-streams/" + liveStream.liveStreamId)
                .withJson(serializer.serialize(liveStream));

        JsonNode responseBody = requestExecutor.executeJson(request);

        return serializer.deserialize(responseBody.getObject());
    }

    public void delete(String liveStreamId) throws ResponseException {
        RequestBuilder request = requestBuilderFactory
                .create(DELETE, "/live-streams/" + liveStreamId);

        requestExecutor.executeJson(request);
    }

    public Iterable<LiveStream> list() throws ResponseException, IllegalArgumentException {
        return list(new QueryParams());
    }

    public Iterable<LiveStream> list(QueryParams queryParams) throws ResponseException, IllegalArgumentException {
        return new IteratorIterable<>(new PageIterator<>(new UriPageLoader<>(
                requestBuilderFactory.create(GET, "/live-streams"),
                requestExecutor,
                serializer
        ), new PageQuery()));
    }

}

