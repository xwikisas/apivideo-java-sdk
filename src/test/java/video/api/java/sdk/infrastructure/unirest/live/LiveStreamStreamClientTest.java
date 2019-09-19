package video.api.java.sdk.infrastructure.unirest.live;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.domain.live.LiveStream;
import video.api.java.sdk.infrastructure.unirest.video.RequestExecutor;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class LiveStreamStreamClientTest {
    LiveStream testLiveStream;
    private LiveStreamClient liveStreamClient;

    @BeforeEach
    void setUp() {
        liveStreamClient = new LiveStreamClient(
                new LiveStreamJsonSerializer(),
                new RequestExecutor(),
                ""
        );
    }

    @Test
    void get() throws ResponseException {
        assertNotNull(liveStreamClient.get("liSuccess"));
    }

    @Test
    void create() throws ResponseException {
        LiveStream liveStream = new LiveStream();
        liveStream.liveStreamId = "liSuccess";
        assertNotNull(liveStreamClient.create(liveStream));
    }

    @Test
    void update() throws ResponseException {
        LiveStream liveStream = new LiveStream();
        liveStream.liveStreamId = "liSuccess";
        liveStream.name         = "Success";
        assertNotNull(liveStreamClient.update(liveStream));
    }

    @Test
    void delete() throws ResponseException {
        assertNotNull(liveStreamClient.delete("liSuccess"));
    }

    @Test
    void list() throws ResponseException {
        assertNotNull(liveStreamClient.list());

    }

    @Test
    void search() throws ResponseException {
        assertNotNull(liveStreamClient.search(new QueryParams()));

    }

    @Test
    void load() throws ResponseException {
        QueryParams queryParams = new QueryParams();
        assertNotNull(liveStreamClient.load(queryParams));
    }
}