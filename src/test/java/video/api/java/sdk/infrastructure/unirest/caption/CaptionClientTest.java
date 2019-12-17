package video.api.java.sdk.infrastructure.unirest.caption;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.infrastructure.unirest.request.RequestBuilderFactory;
import video.api.java.sdk.infrastructure.unirest.video.TestRequestExecutor;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CaptionClientTest {
    private CaptionClient captionResponseException;
    private CaptionClient captionClient;

    @BeforeEach
    void setUp() {
        captionClient = new CaptionClient(
                new RequestBuilderFactory(""),
                new CaptionJsonSerializer(),
                new TestRequestExecutor()
        );
        TestRequestExecutor testRequestExecutor = new TestRequestExecutor();
        testRequestExecutor.exception = new ResponseException("foo", testRequestExecutor.responseFailure(), 400);
        captionResponseException      = new CaptionClient(
                new RequestBuilderFactory(""),
                new CaptionJsonSerializer(),
                testRequestExecutor
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
    void getCaptionFailure() throws ResponseException {
        // assertNotNull(captionResponseException.get("viFailure","en"));
        assertThrows(ResponseException.class, () -> captionResponseException.get("viFailure", "en"));
    }


}