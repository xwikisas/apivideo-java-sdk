package video.api.java.sdk.infrastructure.unirest.live;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import video.api.java.sdk.domain.live.LiveStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LiveStreamJsonSerializerTest {

    private LiveStreamJsonSerializer liveStreamJsonSerializer;

    @BeforeEach
    void setUp() {
        liveStreamJsonSerializer = new LiveStreamJsonSerializer();
    }


    @Test
    void deserializeMax() {
        JSONObject jsonLive = new JSONObject("{\n" +
                                                     "    \"liveStreamId\": \"liSuccess\",\n" +
                                                     "    \"streamKey\": \"a2135a64-dce6-4329-a987-0ea046f42fff\",\n" +
                                                     "    \"name\": \" update test name\",\n" +
                                                     "    \"record\": false,\n" +
                                                     "    \"broadcasting\": false,\n" +
                                                     "    \"assets\": {\n" +
                                                     "        \"iframe\": \"<iframe src=\\\"https://embed-staging.api.video/live/li7KG7XdDROdtuAj00rD4J8B\\\" width=\\\"100%\\\" height=\\\"100%\\\" frameborder=\\\"0\\\" scrolling=\\\"no\\\" allowfullscreen=\\\"\\\"></iframe>\",\n" +
                                                     "        \"player\": \"https://embed-staging.api.video/live/li7KG7XdDROdtuAj00rD4J8B\",\n" +
                                                     "        \"hls\": \"https://live.api.video/li7KG7XdDROdtuAj00rD4J8B.m3u8\",\n" +
                                                     "        \"thumbnail\": \"https://cdn-staging.api.video/live/li7KG7XdDROdtuAj00rD4J8B/thumbnail.jpg\"\n" +
                                                     "    }\n" +
                                                     "}");
        LiveStream liveStream = liveStreamJsonSerializer.deserialize(jsonLive);
        assertEquals("liSuccess", liveStream.liveStreamId);
    }

    @Test
    void deserializeMin() {
        JSONObject jsonLive = new JSONObject("{\n" +
                                                     "    \"liveStreamId\": \"liSuccess\",\n" +
                                                     "}");
        LiveStream liveStream = liveStreamJsonSerializer.deserialize(jsonLive);
        assertEquals("liSuccess", liveStream.liveStreamId);

    }

    @Test
    void serialize() {
        LiveStream liveStream = new LiveStream();
        liveStream.liveStreamId = "toto";
        assertEquals("toto", liveStreamJsonSerializer.serialize(liveStream).getString("liveStreamId"));
    }

    @Test
    void serializeProperties() {

    }

    @Test
    void deserializeProperties() {
        JSONObject jsonLive = new JSONObject("{\n" +
                                                     "    \"liveStreamId\": \"liSuccess\",\n" +
                                                     "    \"streamKey\": \"a2135a64-dce6-4329-a987-0ea046f42fff\",\n" +
                                                     "    \"name\": \"Success\",\n" +
                                                     "    \"record\": false,\n" +
                                                     "    \"broadcasting\": false,\n" +
                                                     "    \"assets\": {\n" +
                                                     "        \"iframe\": \"<iframe src=\\\"https://embed-staging.api.video/live/li7KG7XdDROdtuAj00rD4J8B\\\" width=\\\"100%\\\" height=\\\"100%\\\" frameborder=\\\"0\\\" scrolling=\\\"no\\\" allowfullscreen=\\\"\\\"></iframe>\",\n" +
                                                     "        \"player\": \"https://embed-staging.api.video/live/li7KG7XdDROdtuAj00rD4J8B\",\n" +
                                                     "        \"hls\": \"https://live.api.video/li7KG7XdDROdtuAj00rD4J8B.m3u8\",\n" +
                                                     "        \"thumbnail\": \"https://cdn-staging.api.video/live/li7KG7XdDROdtuAj00rD4J8B/thumbnail.jpg\"\n" +
                                                     "    }\n" +
                                                     "}");
        LiveStream liveStream = liveStreamJsonSerializer.deserializeProperties(jsonLive);
        assertEquals("Success", liveStream.name);
    }
}