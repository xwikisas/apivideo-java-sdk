package video.api.java.sdk.infrastructure.unirest.caption;

import kong.unirest.HttpRequest;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import org.json.JSONArray;
import video.api.java.sdk.domain.RequestExecutor;
import video.api.java.sdk.domain.caption.Caption;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.infrastructure.unirest.RequestFactory;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static kong.unirest.HttpMethod.*;

public class CaptionClient implements video.api.java.sdk.domain.caption.CaptionClient {
    private final RequestFactory          requestFactory;
    private final JsonSerializer<Caption> serializer;
    private final RequestExecutor         requestExecutor;

    public CaptionClient(RequestFactory requestFactory, JsonSerializer<Caption> serializer, RequestExecutor requestExecutor) {
        this.requestFactory  = requestFactory;
        this.serializer      = serializer;
        this.requestExecutor = requestExecutor;
    }

    public Caption get(String videoId, String lang) throws ResponseException {

        HttpRequest request = requestFactory.create(GET, "/videos/" + videoId + "/captions/" + lang);

        HttpResponse<JsonNode> response = requestExecutor.executeJson(request);

        return serializer.deserialize(response.getBody().getObject());
    }

    public List<Caption> getAll(String VideoId) throws ResponseException {

        HttpRequest request = requestFactory.create(GET, "/videos/" + VideoId + "/captions");

        HttpResponse<JsonNode> response = requestExecutor.executeJson(request);
        JSONArray              data     = response.getBody().getArray();
        return serializer.deserialize(data);
    }

    public Caption upload(String videoId, String captionSource, String lang) throws ResponseException, IllegalArgumentException {

        try {

            File            FileToUpload      = new File(captionSource);
            FileInputStream inputStreamToFile = new FileInputStream(FileToUpload);
            HttpRequest request = requestFactory.create(POST, "/videos/" + videoId + "/captions/" + lang)
                    .field("file", inputStreamToFile,
                           kong.unirest.ContentType.APPLICATION_OCTET_STREAM, FileToUpload.getName());

            //Post thumbnail
            HttpResponse<JsonNode> responseSubmit = requestExecutor.executeJson(request);

            inputStreamToFile.close();
            return serializer.deserialize(responseSubmit.getBody().getObject());

        } catch (IOException e) {
            throw new IllegalArgumentException("upload caption  : " + e.getMessage());

        }

    }

    public Caption updateDefault(String videoId, String lang, boolean isDefault) throws ResponseException {

        HttpRequest request = requestFactory.create(PATCH, "/videos/" + videoId + "/captions/" + lang).body("{\"default\":" + isDefault + "}");

        HttpResponse<JsonNode> response = requestExecutor.executeJson(request);

        return serializer.deserialize(response.getBody().getObject());
    }

    public void delete(String videoId, String lang) throws ResponseException {
        HttpRequest request = requestFactory.create(DELETE, "/videos/" + videoId + "/captions/" + lang);

        requestExecutor.executeJson(request);
    }

}
