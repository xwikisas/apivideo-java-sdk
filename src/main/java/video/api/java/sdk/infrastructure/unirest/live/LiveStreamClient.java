package video.api.java.sdk.infrastructure.unirest.live;

import kong.unirest.HttpRequest;
import kong.unirest.JsonNode;
import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.domain.live.LiveStream;
import video.api.java.sdk.infrastructure.pagination.IteratorIterable;
import video.api.java.sdk.infrastructure.pagination.PageIterator;
import video.api.java.sdk.infrastructure.unirest.RequestBuilder;
import video.api.java.sdk.infrastructure.unirest.RequestExecutor;
import video.api.java.sdk.infrastructure.unirest.pagination.UriPageLoader;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class LiveStreamClient implements video.api.java.sdk.domain.live.LiveStreamClient {

    private final RequestBuilder             requestBuilder;
    private final JsonSerializer<LiveStream> serializer;
    private final RequestExecutor            requestExecutor;

    public LiveStreamClient(RequestBuilder requestBuilder, JsonSerializer<LiveStream> serializer, RequestExecutor requestExecutor) {
        this.requestBuilder  = requestBuilder;
        this.serializer      = serializer;
        this.requestExecutor = requestExecutor;
    }

    public LiveStream get(String liveStreamId) throws ResponseException {

        HttpRequest request = requestBuilder.get("/live-streams/" + liveStreamId);

        JsonNode responseBody = requestExecutor.executeJson(request);

        return serializer.deserialize(responseBody.getObject());
    }


    public LiveStream create(LiveStream liveStream) throws ResponseException {
        HttpRequest request = requestBuilder.post("/live-streams").body(serializer.serialize(liveStream));

        JsonNode responseBody = requestExecutor.executeJson(request);

        return serializer.deserialize(responseBody.getObject());

    }


    public LiveStream uploadThumbnail(String liveStreamId, String thumbnailSource) throws ResponseException, IllegalArgumentException {
        try {

            File            FileToUpload      = new File(thumbnailSource);
            FileInputStream inputStreamToFile = new FileInputStream(FileToUpload);
            HttpRequest request = requestBuilder.post("/live-streams/" + liveStreamId + "/thumbnail").field(
                    "file", inputStreamToFile, FileToUpload.getName());

            JsonNode responseBody = requestExecutor.executeJson(request);

            inputStreamToFile.close();
            return serializer.deserialize(responseBody.getObject());

        } catch (IOException e) {
            throw new IllegalArgumentException("uploadThumbnail : " + e.getMessage());
        }
    }

    public LiveStream update(LiveStream liveStream) throws ResponseException {

        HttpRequest request = requestBuilder.patch("/live-streams/" + liveStream.liveStreamId).body(serializer.serialize(liveStream));

        JsonNode responseBody = requestExecutor.executeJson(request);

        return serializer.deserialize(responseBody.getObject());

    }

    public void delete(String liveStreamId) throws ResponseException {
        HttpRequest request = requestBuilder.delete("/live-streams/" + liveStreamId);

        requestExecutor.executeJson(request);
    }


    /////////////////////////Iterators//////////////////////////////

    public Iterable<LiveStream> list() throws ResponseException, IllegalArgumentException {
        return search(new QueryParams());
    }

    public Iterable<LiveStream> search(QueryParams queryParams) throws ResponseException, IllegalArgumentException {
        return new IteratorIterable<>(new PageIterator<>(new UriPageLoader<>(
                "/live-streams",
                requestBuilder,
                requestExecutor,
                serializer
        ), queryParams));
    }

}

