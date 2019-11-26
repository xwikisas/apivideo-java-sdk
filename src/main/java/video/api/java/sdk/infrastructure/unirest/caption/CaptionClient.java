package video.api.java.sdk.infrastructure.unirest.caption;

import kong.unirest.HttpRequest;
import kong.unirest.JsonNode;
import org.json.JSONObject;
import video.api.java.sdk.domain.caption.Caption;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.infrastructure.unirest.RequestBuilder;
import video.api.java.sdk.infrastructure.unirest.RequestExecutor;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class CaptionClient implements video.api.java.sdk.domain.caption.CaptionClient {
    private final RequestBuilder          requestBuilder;
    private final JsonSerializer<Caption> serializer;
    private final RequestExecutor         requestExecutor;

    public CaptionClient(RequestBuilder requestBuilder, JsonSerializer<Caption> serializer, RequestExecutor requestExecutor) {
        this.requestBuilder  = requestBuilder;
        this.serializer      = serializer;
        this.requestExecutor = requestExecutor;
    }

    public Caption get(String videoId, String lang) throws ResponseException {
        HttpRequest request = requestBuilder
                .get("/videos/" + videoId + "/captions/" + lang);

        JsonNode responseBody = requestExecutor.executeJson(request);

        return serializer.deserialize(responseBody.getObject());
    }

    public List<Caption> getAll(String videoId) throws ResponseException {
        HttpRequest request = requestBuilder
                .get("/videos/" + videoId + "/captions");

        JsonNode responseBody = requestExecutor.executeJson(request);

        return serializer.deserialize(responseBody.getArray());
    }

    public Caption upload(String videoId, File file, String lang) throws ResponseException, IOException {
        try (FileInputStream inputStreamToFile = new FileInputStream(file)){

            HttpRequest request = requestBuilder
                    .post("/videos/" + videoId + "/captions/" + lang)
                    .field("file", inputStreamToFile, file.getName());

            JsonNode responseBody = requestExecutor.executeJson(request);

            return serializer.deserialize(responseBody.getObject());
        }
    }

    public Caption updateDefault(String videoId, String language, boolean isDefault) throws ResponseException {
        HttpRequest request = requestBuilder
                .patch("/videos/" + videoId + "/captions/" + language)
                .body(new JSONObject().put("default", isDefault));

        JsonNode responseBody = requestExecutor.executeJson(request);

        return serializer.deserialize(responseBody.getObject());
    }

    public void delete(String videoId, String lang) throws ResponseException {
        HttpRequest request = requestBuilder
                .delete("/videos/" + videoId + "/captions/" + lang);

        requestExecutor.executeJson(request);
    }
}
