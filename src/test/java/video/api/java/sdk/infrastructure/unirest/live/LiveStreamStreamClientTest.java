package video.api.java.sdk.infrastructure.unirest.live;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.domain.live.LiveStream;
import video.api.java.sdk.infrastructure.unirest.request.RequestBuilder;
import video.api.java.sdk.infrastructure.unirest.asset.AssetsJsonSerializer;
import video.api.java.sdk.infrastructure.unirest.video.TestRequestExecutor;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class LiveStreamStreamClientTest {
    private LiveStreamClient liveStreamClient;

    @BeforeEach
    void setUp() {
        liveStreamClient = new LiveStreamClient(
                new RequestBuilder(""),
                new LiveStreamJsonSerializer(new AssetsJsonSerializer()),
                new TestRequestExecutor()
        );
    }

    @Test
    void get() throws ResponseException {
        assertNotNull(liveStreamClient.get("liSuccess"));
    }

    @Test
    void create() throws ResponseException {
        LiveStream liveStream = new LiveStream("test");
        liveStream.liveStreamId = "liSuccess";
        assertNotNull(liveStreamClient.create(liveStream));
    }

    @Test
    void update() throws ResponseException {
        LiveStream liveStream = new LiveStream("Success");
        liveStream.liveStreamId = "liSuccess";
        assertNotNull(liveStreamClient.update(liveStream));
    }

    @Test
    void delete() throws ResponseException {

    }

    @Test
    void list() throws ResponseException {
        assertNotNull(liveStreamClient.list());

    }

}