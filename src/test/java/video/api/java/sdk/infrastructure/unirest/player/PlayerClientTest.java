package video.api.java.sdk.infrastructure.unirest.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.domain.player.Player;
import video.api.java.sdk.infrastructure.unirest.video.TestRequestExecutor;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("WeakerAccess")
class PlayerClientTest {
    PlayerClient playerClient;
    PlayerClient playerResponseException;

    @BeforeEach
    void setUp() {
        TestRequestExecutor testRequestExecutor = new TestRequestExecutor();
        testRequestExecutor.exception = new ResponseException(testRequestExecutor.ResponseFailure(), "");
        playerResponseException       = new PlayerClient(
                new PlayerJsonSerializer(),
                testRequestExecutor,
                ""
        );
        playerClient                  = new PlayerClient(
                new PlayerJsonSerializer(),
                new TestRequestExecutor(),
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

    }

    @Test
    void list() throws ResponseException {
        assertNotNull(playerClient.list());
    }

    @Test
    void search() throws ResponseException {
        assertNotNull(playerClient.search(new QueryParams()));
    }
}