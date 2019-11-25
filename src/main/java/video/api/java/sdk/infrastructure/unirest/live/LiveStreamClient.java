package video.api.java.sdk.infrastructure.unirest.live;

import kong.unirest.HttpRequest;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.RequestExecutor;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.domain.live.LiveStream;
import video.api.java.sdk.infrastructure.pagination.IteratorIterable;
import video.api.java.sdk.infrastructure.pagination.PageIterator;
import video.api.java.sdk.infrastructure.unirest.RequestFactory;
import video.api.java.sdk.infrastructure.unirest.pagination.UriPageLoader;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static kong.unirest.HttpMethod.*;

public class LiveStreamClient implements video.api.java.sdk.domain.live.LiveStreamClient {

    private final RequestFactory             requestFactory;
    private final JsonSerializer<LiveStream> serializer;
    private final RequestExecutor            requestExecutor;

    public LiveStreamClient(RequestFactory requestFactory, JsonSerializer<LiveStream> serializer, RequestExecutor requestExecutor) {
        this.requestFactory  = requestFactory;
        this.serializer      = serializer;
        this.requestExecutor = requestExecutor;
    }

    public LiveStream get(String liveStreamId) throws ResponseException {

        HttpRequest request = requestFactory.create(GET, "/live-streams/" + liveStreamId);

        HttpResponse<JsonNode> response = requestExecutor.executeJson(request);

        return getLiveResponse(response);
    }


    public LiveStream create(LiveStream liveStream) throws ResponseException {
        HttpRequest request = requestFactory.create(POST, "/live-streams").body(serializer.serialize(liveStream));

        HttpResponse<JsonNode> response = requestExecutor.executeJson(request);

        return getLiveResponse(response);

    }


    public LiveStream uploadThumbnail(String liveStreamId, String thumbnailSource) throws ResponseException, IllegalArgumentException {
        try {

            File            FileToUpload      = new File(thumbnailSource);
            FileInputStream inputStreamToFile = new FileInputStream(FileToUpload);
            HttpRequest request = requestFactory.create(POST, "/live-streams/" + liveStreamId + "/thumbnail").field(
                    "file", inputStreamToFile,
                    kong.unirest.ContentType.APPLICATION_OCTET_STREAM, FileToUpload.getName());

            HttpResponse<JsonNode> responseSubmit = requestExecutor.executeJson(request);

            inputStreamToFile.close();
            return getLiveResponse(responseSubmit);

        } catch (IOException e) {
            throw new IllegalArgumentException("uploadThumbnail : " + e.getMessage());
        }
    }

    public LiveStream update(LiveStream liveStream) throws ResponseException {

        HttpRequest request = requestFactory.create(PATCH, "/live-streams/" + liveStream.liveStreamId).body(serializer.serialize(liveStream));

        HttpResponse<JsonNode> responseSubmit = requestExecutor.executeJson(request);

        return getLiveResponse(responseSubmit);

    }

    public void delete(String liveStreamId) throws ResponseException {
        HttpRequest request = requestFactory.create(DELETE, "/live-streams/" + liveStreamId);

        requestExecutor.executeJson(request);
    }


    /////////////////////////Iterators//////////////////////////////

    public Iterable<LiveStream> list() throws ResponseException, IllegalArgumentException {
        return search(new QueryParams());
    }

    public Iterable<LiveStream> search(QueryParams queryParams) throws ResponseException, IllegalArgumentException {
        return new IteratorIterable<>(new PageIterator<>(new UriPageLoader<>(
                "/live-streams",
                requestFactory,
                requestExecutor,
                serializer
        ), queryParams));
    }


    /////////////////////////Functions//////////////////////////////

    private LiveStream getLiveResponse(HttpResponse<JsonNode> response) {

        return serializer.deserialize(response.getBody().getObject());
    }

}

