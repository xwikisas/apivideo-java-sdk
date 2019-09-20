package video.api.java.sdk.infrastructure.unirest.caption;

import kong.unirest.HttpRequest;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.json.JSONArray;
import org.json.JSONObject;
import video.api.java.sdk.domain.RequestExecutor;
import video.api.java.sdk.domain.caption.Caption;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class CaptionClient implements video.api.java.sdk.domain.caption.CaptionClient {


    private final JsonSerializer<Caption> serializer;
    private final RequestExecutor         requestExecutor;
    private final String                  baseUri;

    public CaptionClient(JsonSerializer<Caption> serializer, RequestExecutor requestExecutor, String baseUri) {
        this.serializer      = serializer;
        this.requestExecutor = requestExecutor;
        this.baseUri         = baseUri;
    }


    public Caption get(String videoId, String lang) throws ResponseException {

        HttpRequest request = Unirest.get(baseUri + "/videos/" + videoId + "/captions/" + lang);

        HttpResponse<JsonNode> response = requestExecutor.executeJson(request);

        return getCaptionResponse(response);
    }

    public List<Caption> getAll(String VideoId) throws ResponseException {

        HttpRequest request = Unirest.get(baseUri + "/videos/" + VideoId + "/captions");

        HttpResponse<JsonNode> response = requestExecutor.executeJson(request);
        JSONArray              data     = response.getBody().getArray();
        return serializer.deserialize(data);
    }

    public Caption upload(String videoId, String captionSource, String lang) throws ResponseException, IllegalArgumentException {

        try {

            File            FileToUpload      = new File(captionSource);
            FileInputStream inputStreamToFile = new FileInputStream(FileToUpload);
            HttpRequest request = Unirest.post(baseUri + "/videos/" + videoId + "/captions/" + lang)
                    .field("file", inputStreamToFile,
                           kong.unirest.ContentType.APPLICATION_OCTET_STREAM, FileToUpload.getName());

            //Post thumbnail
            HttpResponse<JsonNode> responseSubmit = requestExecutor.executeJson(request);

            inputStreamToFile.close();
            return getCaptionResponse(responseSubmit);

        } catch (IOException e) {
            throw new IllegalArgumentException("upload caption  : " + e.getMessage());

        }

    }

    public Caption updateDefault(String videoId, String lang, boolean isDefault) throws ResponseException {

        HttpRequest request = Unirest.patch(baseUri + "/videos/" + videoId + "/captions/" + lang).body("{\"default\":" + isDefault + "}");

        HttpResponse<JsonNode> response = requestExecutor.executeJson(request);

        return getCaptionResponse(response);
    }

    public void delete(String videoId, String lang) throws ResponseException {
        HttpRequest request = Unirest.delete(baseUri + "/videos/" + videoId + "/captions/" + lang);

        requestExecutor.executeJson(request);
    }


    /////////////////////////Functions//////////////////////////////

    private Caption getCaptionResponse(HttpResponse<JsonNode> response) {

        return serializer.deserialize(response.getBody().getObject());
    }
}
