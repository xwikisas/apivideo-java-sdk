package video.api.java.sdk.infrastructure.unirest.caption;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import video.api.java.sdk.domain.caption.CaptionInput;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.infrastructure.unirest.request.RequestBuilderFactory;
import video.api.java.sdk.infrastructure.unirest.TestRequestExecutor;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CaptionClientTest {
    private CaptionClient captionResponseException;
    private CaptionClient captionClient;

    @BeforeEach
    void setUp() {
        captionClient = new CaptionClient(
                new RequestBuilderFactory(""),
                new CaptionInputSerializer(),
                new CaptionDeserializer(),
                new TestRequestExecutor()
        );
        TestRequestExecutor testRequestExecutor = new TestRequestExecutor();
        testRequestExecutor.exception = new ResponseException("foo", testRequestExecutor.responseFailure(), 400);
        captionResponseException      = new CaptionClient(
                new RequestBuilderFactory(""),
                new CaptionInputSerializer(),
                new CaptionDeserializer(),
                testRequestExecutor
        );
    }

    @Test
    void get() throws ResponseException {
        assertNotNull(captionClient.get("viSuccess", "en"));
    }

    @Test
    void getAll() throws ResponseException {
        assertNotNull(captionClient.list("viSuccess"));
    }

    @Test
    void updateDefault() throws ResponseException {
        CaptionInput captionInput = new CaptionInput("en");
        captionInput.isDefault = true;

        assertNotNull(captionClient.update("viSuccess", captionInput));
    }

    @Test
    void delete() {

    }

    @Test
    void getCaptionFailure() {
        // assertNotNull(captionResponseException.get("viFailure","en"));
        assertThrows(ResponseException.class, () -> captionResponseException.get("viFailure", "en"));
    }


}