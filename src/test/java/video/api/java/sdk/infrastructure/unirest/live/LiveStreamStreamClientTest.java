package video.api.java.sdk.infrastructure.unirest.live;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.domain.live.LiveStream;
import video.api.java.sdk.domain.live.LiveStreamInput;
import video.api.java.sdk.infrastructure.unirest.request.RequestBuilderFactory;
import video.api.java.sdk.infrastructure.unirest.TestRequestExecutor;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class LiveStreamStreamClientTest {
    private LiveStreamClient liveStreamClient;

    @BeforeEach
    void setUp() {
        liveStreamClient = new LiveStreamClient(
                new RequestBuilderFactory(""),
                new LiveStreamInputSerializer(),
                new LiveStreamDeserializer(),
                new TestRequestExecutor()
        );
    }

    @Test
    void get() throws ResponseException {
        assertNotNull(liveStreamClient.get("liSuccess"));
    }

    @Test
    void create() throws ResponseException {
        LiveStreamInput liveStream = new LiveStreamInput("test");

        assertNotNull(liveStreamClient.create(liveStream));
    }

    @Test
    void update() throws ResponseException {
        LiveStream liveStream = new LiveStream("Success", "liSuccess", "foo", false);

        assertNotNull(liveStreamClient.update(liveStream));
    }

    @Test
    void delete() {

    }

    @Test
    void list() throws ResponseException {
        assertNotNull(liveStreamClient.list());

    }

}