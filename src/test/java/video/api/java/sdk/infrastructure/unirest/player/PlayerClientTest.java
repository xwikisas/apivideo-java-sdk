package video.api.java.sdk.infrastructure.unirest.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.domain.player.Player;
import video.api.java.sdk.infrastructure.unirest.video.RequestExecutor;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("WeakerAccess")
class PlayerClientTest {
    PlayerClient playerClient;
    PlayerClient playerResponseException;

    @BeforeEach
    void setUp() {
        RequestExecutor requestExecutor = new RequestExecutor();
        requestExecutor.exception = new ResponseException(requestExecutor.ResponseFailure(), "");
        playerResponseException   = new PlayerClient(
                new PlayerJsonSerializer(),
                requestExecutor,
                ""
        );
        playerClient              = new PlayerClient(
                new PlayerJsonSerializer(),
                new RequestExecutor(),
                ""
        );

    }

    @Test
    void get() throws ResponseException {
        assertNotNull(playerClient.get("plSuccess"));
    }

    @Test
    void create() throws ResponseException {
        assertNotNull(playerClient.create(new Player()));
    }

    @Test
    void update() throws ResponseException {
        Player player = new Player();
        player.playerId = "plSuccess";
        assertNotNull(playerClient.update(player));

    }

    @Test
    void uploadFailureLogo() {
        assertThrows(IllegalArgumentException.class, () -> playerClient.uploadLogo("plSuccess", "Failure Source", "test.fr"));
    }

    @Test
    void getPlayerFailure() {

        assertThrows(ResponseException.class, () -> playerResponseException.get("plFailure"));


    }

    @Test
    void delete() throws ResponseException {
        assertEquals(playerClient.delete("plSuccess"), 204);
    }

    @Test
    void list() throws ResponseException {
        assertNotNull(playerClient.list());
    }

    @Test
    void search() throws ResponseException {
        assertNotNull(playerClient.search(new QueryParams()));
    }

    @Test
    void load() throws ResponseException {
        assertNotNull(playerClient.load(new QueryParams()));
    }
}