package video.api.java.sdk.infrastructure.unirest.caption;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.infrastructure.unirest.video.TestRequestExecutor;

import static org.junit.jupiter.api.Assertions.*;

class CaptionClientTest {
    private CaptionClient captionResponseException;
    private CaptionClient captionClient;

    @BeforeEach
    void setUp() {
        captionClient = new CaptionClient(
                new CaptionJsonSerializer(),
                new TestRequestExecutor(),
                ""
        );
        TestRequestExecutor testRequestExecutor = new TestRequestExecutor();
        testRequestExecutor.exception = new ResponseException(testRequestExecutor.ResponseFailure(), "");
        captionResponseException      = new CaptionClient(
                new CaptionJsonSerializer(),
                testRequestExecutor,
                ""
        );
    }

    @Test
    void get() throws ResponseException {
        assertNotNull(captionClient.get("viSuccess", "en"));
    }

    @Test
    void getAll() throws ResponseException {
        assertNotNull(captionClient.getAll("viSuccess"));
    }

    @Test
    void updateDefault() throws ResponseException {
        assertNotNull(captionClient.updateDefault("viSuccess", "en", true));
    }

    @Test
    void delete() throws ResponseException {

    }

    @Test
    void getUploadException() {
        assertThrows(IllegalArgumentException.class, () -> captionClient.upload("viSuccess", "error", "en"));
    }

    @Test
    void getCaptionFailure() throws ResponseException {
        // assertNotNull(captionResponseException.get("viFailure","en"));
        assertThrows(ResponseException.class, () -> captionResponseException.get("viFailure", "en"));
    }


}