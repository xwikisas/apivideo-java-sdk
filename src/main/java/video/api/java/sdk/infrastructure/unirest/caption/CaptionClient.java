package video.api.java.sdk.infrastructure.unirest.caption;

import kong.unirest.JsonNode;
import org.json.JSONObject;
import video.api.java.sdk.domain.caption.Caption;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.infrastructure.unirest.RequestExecutor;
import video.api.java.sdk.infrastructure.unirest.request.RequestBuilder;
import video.api.java.sdk.infrastructure.unirest.request.RequestBuilderFactory;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static kong.unirest.HttpMethod.*;

public class CaptionClient implements video.api.java.sdk.domain.caption.CaptionClient {
    private final RequestBuilderFactory   requestBuilderFactory;
    private final JsonSerializer<Caption> serializer;
    private final RequestExecutor         requestExecutor;

    public CaptionClient(RequestBuilderFactory requestBuilderFactory, JsonSerializer<Caption> serializer, RequestExecutor requestExecutor) {
        this.requestBuilderFactory = requestBuilderFactory;
        this.serializer            = serializer;
        this.requestExecutor       = requestExecutor;
    }

    public Caption get(String videoId, String lang) throws ResponseException {
        RequestBuilder request = requestBuilderFactory
                .create(GET, "/videos/" + videoId + "/captions/" + lang);

        JsonNode responseBody = requestExecutor.executeJson(request);

        return serializer.deserialize(responseBody.getObject());
    }

    public List<Caption> getAll(String videoId) throws ResponseException {
        RequestBuilder request = requestBuilderFactory
                .create(GET, "/videos/" + videoId + "/captions");

        JsonNode responseBody = requestExecutor.executeJson(request);

        return serializer.deserialize(responseBody.getArray());
    }

    public Caption upload(String videoId, File file, String lang) throws ResponseException, IOException {
        RequestBuilder request = requestBuilderFactory
                .create(POST, "/videos/" + videoId + "/captions/" + lang)
                .withFile(file);

        JsonNode responseBody = requestExecutor.executeJson(request);

        return serializer.deserialize(responseBody.getObject());
    }

    public Caption updateDefault(String videoId, String language, boolean isDefault) throws ResponseException {
        RequestBuilder request = requestBuilderFactory
                .create(PATCH, "/videos/" + videoId + "/captions/" + language)
                .withJson(new JSONObject().put("default", isDefault));

        JsonNode responseBody = requestExecutor.executeJson(request);

        return serializer.deserialize(responseBody.getObject());
    }

    public void delete(String videoId, String lang) throws ResponseException {
        RequestBuilder request = requestBuilderFactory
                .create(DELETE, "/videos/" + videoId + "/captions/" + lang);

        requestExecutor.executeJson(request);
    }
}
