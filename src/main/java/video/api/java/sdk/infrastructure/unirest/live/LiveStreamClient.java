package video.api.java.sdk.infrastructure.unirest.live;

import kong.unirest.HttpRequest;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.json.JSONObject;
import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.RequestExecutor;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.domain.live.LiveStream;
import video.api.java.sdk.domain.pagination.Page;
import video.api.java.sdk.infrastructure.pagination.PageIterator;
import video.api.java.sdk.infrastructure.pagination.PageLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

public class LiveStreamClient implements video.api.java.sdk.domain.live.LiveStreamClient, PageLoader<LiveStream> {

    private final LiveStreamJsonSerializer serializer;
    private final RequestExecutor          requestExecutor;
    private final String                   baseUri;

    public LiveStreamClient(LiveStreamJsonSerializer serializer, RequestExecutor requestExecutor, String baseUri) {

        this.serializer      = serializer;
        this.requestExecutor = requestExecutor;
        this.baseUri         = baseUri;

    }

    public LiveStream get(String liveStreamId) throws ResponseException {

        HttpRequest request = Unirest.get(baseUri + "/live-streams/" + liveStreamId);

        HttpResponse<JsonNode> response = requestExecutor.executeJson(request);

        return getLiveResponse(response);
    }


    public LiveStream create(LiveStream liveStream) throws ResponseException {
        HttpRequest request = Unirest.post(baseUri + "/live-streams").body(serializer.serializeProperties(liveStream));

        HttpResponse<JsonNode> response = requestExecutor.executeJson(request);

        return getLiveResponse(response);

    }


    public LiveStream uploadThumbnail(String liveStreamId, String thumbnailSource) throws ResponseException, IllegalArgumentException {
        try {

            File            FileToUpload      = new File(thumbnailSource);
            FileInputStream inputStreamToFile = new FileInputStream(FileToUpload);
            HttpRequest request = Unirest.post(baseUri + "/live-streams/" + liveStreamId + "/thumbnail").field(
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

        HttpRequest request = Unirest.patch(baseUri + "/live-streams/" + liveStream.liveStreamId).body(serializer.serializeProperties(liveStream));

        HttpResponse<JsonNode> responseSubmit = requestExecutor.executeJson(request);

        return getLiveResponse(responseSubmit);

    }

    public int delete(String liveStreamId) throws ResponseException {


        HttpRequest request = Unirest.delete(baseUri + "/live-streams/" + liveStreamId);

        HttpResponse<JsonNode> response = requestExecutor.executeJson(request);

        return response.getStatus();

    }


    /////////////////////////Iterators//////////////////////////////

    public Iterator<LiveStream> list() throws ResponseException, IllegalArgumentException {


        QueryParams queryParams = new QueryParams();
        return new PageIterator<>(this, queryParams);
    }

    public Iterator<LiveStream> search(QueryParams queryParams) throws ResponseException, IllegalArgumentException {


        return new PageIterator<>(this, queryParams);

    }


    /////////////////////////Functions//////////////////////////////


    public String toString(LiveStream liveStream) {
        return serializer.serialize(liveStream).toString();
    }

    public JSONObject toJSONObject(LiveStream liveStream) {
        return serializer.serialize(liveStream);
    }

    private LiveStream getLiveResponse(HttpResponse<JsonNode> response) {

        return serializer.deserialize(response.getBody().getObject());
    }


    @Override
    public Page<LiveStream> load(QueryParams queryParams) throws ResponseException {

        String      url     = queryParams.create(baseUri + "/live-streams/");
        HttpRequest request = Unirest.get(url);

        HttpResponse<JsonNode> response = requestExecutor.executeJson(request);

        return new Page<>(
                serializer.deserialize(response.getBody().getObject().getJSONArray("data")),
                response.getBody().getObject().getJSONObject("pagination").getInt("pagesTotal"),
                response.getBody().getObject().getJSONObject("pagination").getInt("currentPage")
        );

    }
}

