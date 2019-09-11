package video.api.java.sdk.infrastructure.unirest.live;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.live.Live;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.infrastructure.unirest.video.RequestExecutor;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class LiveClientTest {
    Live testLive;
    private LiveClient liveClient;

    @BeforeEach
    void setUp() {
        liveClient = new LiveClient(
                new LiveJsonSerializer(),
                new RequestExecutor(),
                ""
        );
    }

    @Test
    void get() throws ResponseException {
        assertNotNull(liveClient.get("liSuccess"));
    }

    @Test
    void create() throws ResponseException {
        Live live = new Live();
        live.liveStreamId = "liSuccess";
        assertNotNull(liveClient.create(live));
    }

    @Test
    void update() throws ResponseException {
        Live live = new Live();
        live.liveStreamId = "liSuccess";
        live.name         = "Success";
        assertNotNull(liveClient.update(live));
    }

    @Test
    void delete() throws ResponseException {
        assertNotNull(liveClient.delete("liSuccess"));
    }

    @Test
    void list() throws ResponseException {
        assertNotNull(liveClient.list());

    }

    @Test
    void search() throws ResponseException {
        assertNotNull(liveClient.search(new QueryParams()));

    }

    @Test
    void load() throws ResponseException {
        QueryParams queryParams = new QueryParams();
        assertNotNull(liveClient.load(queryParams));
    }
}