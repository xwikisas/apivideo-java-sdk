package video.api.java.sdk.infrastructure.unirest.caption;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.infrastructure.unirest.RequestBuilder;
import video.api.java.sdk.infrastructure.unirest.video.TestRequestExecutor;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class CaptionClientTest {
    private CaptionClient captionResponseException;
    private CaptionClient captionClient;

    @BeforeEach
    void setUp() {
        captionClient = new CaptionClient(
                new RequestBuilder(""),
                new CaptionJsonSerializer(),
                new TestRequestExecutor()
        );
        TestRequestExecutor testRequestExecutor = new TestRequestExecutor();
        testRequestExecutor.exception = new ResponseException("foo", testRequestExecutor.responseFailure(), 400);
        captionResponseException      = new CaptionClient(
                new RequestBuilder(""),
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
    void getUploadException() {
        assertThrows(FileNotFoundException.class, () -> captionClient.upload("viSuccess", new File("error"), "en"));
    }

    @Test
    void getCaptionFailure() throws ResponseException {
        // assertNotNull(captionResponseException.get("viFailure","en"));
        assertThrows(ResponseException.class, () -> captionResponseException.get("viFailure", "en"));
    }


}