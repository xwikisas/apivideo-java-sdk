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
import video.api.java.sdk.infrastructure.unirest.RequestBuilder;
import video.api.java.sdk.infrastructure.unirest.asset.AssetsJsonSerializer;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VideoClientTest {
    private VideoClient videoClient;
    private VideoClient videoClientResponseException;
    private VideoClient videoClientSerializerException;
    private Video       testVideo = new Video();

    @BeforeEach
    void setUp() {
        TestRequestExecutor testRequestExecutor = new TestRequestExecutor();
        testRequestExecutor.exception  = new ResponseException(testRequestExecutor.ResponseFailure(), "");
        videoClientResponseException   = new VideoClient(
                new RequestBuilder(""),
                new VideoJsonSerializer(new AssetsJsonSerializer()),
                testRequestExecutor
        );
        videoClient                    = new VideoClient(
                new RequestBuilder(""),
                new VideoJsonSerializer(new AssetsJsonSerializer()),
                new TestRequestExecutor()
        );
        videoClientSerializerException = new VideoClient(
                new RequestBuilder(""),
                new JsonSerializer<Video>() {
                    @Override
                    public Video deserialize(JSONObject data) throws JSONException {
                        throw new JSONException("json error");
                    }

                    @Override
                    public List<Video> deserialize(JSONArray data) throws JSONException {
                        return null;
                    }

                    @Override
                    public JSONObject serialize(Video object) throws JSONException {
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
        testVideo.videoId = "viSuccess";
        assertNotNull(videoClient.create(testVideo));
    }

    @Test
    void uploadThumbnailFailure() throws IllegalArgumentException {
        testVideo.videoId = "viSuccess";
        assertThrows(java.lang.IllegalArgumentException.class, () -> videoClient.uploadThumbnail(testVideo, "ezdzedze"));
    }

    @Test
    void updateThumbnailWithTimeCode() throws ResponseException {
        testVideo.videoId = "viSuccess";
        assertNotNull(videoClient.updateThumbnailWithTimeCode(testVideo, ""));
    }

    @Test
    void update() throws ResponseException {
        testVideo.videoId = "viSuccess";
        assertNotNull(videoClient.update(testVideo));
    }


    @Test
    void delete() {

    }

    @Test
    void search() throws ResponseException, IllegalArgumentException {
        assertNotNull(videoClient.search(new QueryParams()));
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