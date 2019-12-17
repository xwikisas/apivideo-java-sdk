package video.api.java.sdk.infrastructure.unirest.video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.exception.ClientException;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.domain.exception.ServerException;
import video.api.java.sdk.domain.video.Video;
import video.api.java.sdk.domain.video.VideoInput;
import video.api.java.sdk.infrastructure.unirest.request.RequestBuilderFactory;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonDeserializer;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VideoClientTest {
    private VideoClient videoClient;
    private VideoClient videoClientResponseException;
    private VideoClient videoClientSerializerException;

    @BeforeEach
    void setUp() {
        TestRequestExecutor testRequestExecutor = new TestRequestExecutor();
        testRequestExecutor.exception  = new ResponseException("foo", testRequestExecutor.responseFailure(), 400);
        videoClientResponseException   = new VideoClient(
                new RequestBuilderFactory(""),
                new VideoInputSerializer(),
                new VideoDeserializer(),
                testRequestExecutor
        );
        videoClient                    = new VideoClient(
                new RequestBuilderFactory(""),
                new VideoInputSerializer(),
                new VideoDeserializer(),
                new TestRequestExecutor()
        );
        videoClientSerializerException = new VideoClient(
                new RequestBuilderFactory(""),
                object -> null,
                new JsonDeserializer<Video>() {
                    @Override
                    public Video deserialize(JSONObject data) throws JSONException {
                        throw new JSONException("json error");
                    }

                    @Override
                    public List<Video> deserialize(JSONArray data) throws JSONException {
                        return null;
                    }
                },
                new TestRequestExecutor()
        );
    }


    @Test
    void getResponseFailure() {

        assertThrows(ResponseException.class, () -> videoClientResponseException.get("viFailure"));
    }

    @Test
    void getJsonResponseFailure() {

        assertThrows(JSONException.class, () -> videoClientSerializerException.get("viSuccess"));
    }


    @Test
    void getClientException() {
        assertThrows(ClientException.class, () -> videoClient.get("viClientException"));

    }

    @Test
    void getResponseException() {
        assertThrows(ServerException.class, () -> videoClient.get("viServerException"));

    }


    @Test
    void getSuccess() throws ResponseException {

        assertNotNull(videoClient.get("viSuccess"));
    }

    @Test
    void getStatusSuccess() throws ResponseException {

        assertNotNull(videoClient.getStatus("viSuccess"));
    }


    @Test
    void createSuccess() throws ResponseException {
        assertNotNull(videoClient.create(new VideoInput()));
    }

    @Test
    void uploadThumbnailFailure() {
        assertThrows(IOException.class, () -> videoClient.uploadThumbnail("viSuccess", new File("foo")));
    }

    @Test
    void updateThumbnailWithTimeCode() throws ResponseException {
        assertNotNull(videoClient.updateThumbnail("viSuccess", ""));
    }

    @Test
    void update() throws ResponseException {
        assertNotNull(videoClient.update(new Video("viSuccess", null, null, null, null)));
    }

    @Test
    void delete() {

    }

    @Test
    void listWithParams() throws ResponseException, IllegalArgumentException {
        assertNotNull(videoClient.list(new QueryParams()));
    }

    @Test
    void list() throws ResponseException, IllegalArgumentException {
        assertNotNull(videoClient.list());
    }

    @Test
    void getStatus() throws ResponseException {
        assertNotNull(videoClient.getStatus("viSuccess"));

    }

}