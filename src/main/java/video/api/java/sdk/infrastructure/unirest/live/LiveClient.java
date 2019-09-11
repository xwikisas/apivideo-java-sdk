package video.api.java.sdk.infrastructure.unirest.live;

import kong.unirest.HttpRequest;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.json.JSONObject;
import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.RequestExecutor;
import video.api.java.sdk.domain.live.Live;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.infrastructure.pagination.Page;
import video.api.java.sdk.infrastructure.pagination.PageIterator;
import video.api.java.sdk.infrastructure.pagination.PageLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class LiveClient implements video.api.java.sdk.domain.live.LiveClient, PageLoader<Live> {

    private final LiveJsonSerializer serializer;
    private final RequestExecutor    requestExecutor;
    private final String             baseUri;

    public LiveClient(LiveJsonSerializer serializer, RequestExecutor requestExecutor, String baseUri) {

        this.serializer      = serializer;
        this.requestExecutor = requestExecutor;
        this.baseUri         = baseUri;

    }

    public Live get(String liveStreamId) throws ResponseException {

        HttpRequest request = Unirest.get(baseUri + "/live-streams/" + liveStreamId);

        HttpResponse<JsonNode> response = requestExecutor.executeJson(request);

        return getLiveResponse(response);
    }


    public Live create(Live live) throws ResponseException {
        HttpRequest request = Unirest.post(baseUri + "/live-streams").body(serializer.serializeProperties(live));

        HttpResponse<JsonNode> response = requestExecutor.executeJson(request);

        return getLiveResponse(response);

    }


    public Live uploadThumbnail(String liveStreamId, String thumbnailSource) throws ResponseException, IllegalArgumentException {
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

    public Live update(Live live) throws ResponseException {

        HttpRequest request = Unirest.patch(baseUri + "/live-streams/" + live.liveStreamId).body(serializer.serializeProperties(live));

        HttpResponse<JsonNode> responseSubmit = requestExecutor.executeJson(request);

        return getLiveResponse(responseSubmit);

    }

    public int delete(String liveStreamId) throws ResponseException {


        HttpRequest request = Unirest.delete(baseUri + "/live-streams/" + liveStreamId);

        HttpResponse<JsonNode> response = requestExecutor.executeJson(request);

        return response.getStatus();

    }


    /////////////////////////Iterators//////////////////////////////

    public PageIterator<Live> list() throws ResponseException, IllegalArgumentException {


        QueryParams queryParams = new QueryParams();
        return new PageIterator<>(this, queryParams);
    }

    public PageIterator<Live> search(QueryParams queryParams) throws ResponseException, IllegalArgumentException {


        return new PageIterator<>(this, queryParams);

    }


    /////////////////////////Functions//////////////////////////////


    public String toString(Live live) {
        return serializer.serialize(live).toString();
    }

    public JSONObject toJSONObject(Live live) {
        return serializer.serialize(live);
    }

    private Live getLiveResponse(HttpResponse<JsonNode> response) {

        return serializer.deserialize(response.getBody().getObject());
    }


    @Override
    public Page<Live> load(QueryParams queryParams) throws ResponseException {

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

