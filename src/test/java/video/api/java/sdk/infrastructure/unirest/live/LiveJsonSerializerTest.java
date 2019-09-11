package video.api.java.sdk.infrastructure.unirest.live;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import video.api.java.sdk.domain.live.Live;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LiveJsonSerializerTest {

    private LiveJsonSerializer liveJsonSerializer;

    @BeforeEach
    void setUp() {
        liveJsonSerializer = new LiveJsonSerializer();
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
        Live live = liveJsonSerializer.deserialize(jsonLive);
        assertEquals("liSuccess", live.liveStreamId);
    }

    @Test
    void deserializeMin() {
        JSONObject jsonLive = new JSONObject("{\n" +
                                                     "    \"liveStreamId\": \"liSuccess\",\n" +
                                                     "}");
        Live live = liveJsonSerializer.deserialize(jsonLive);
        assertEquals("liSuccess", live.liveStreamId);

    }

    @Test
    void serialize() {
        Live live = new Live();
        live.liveStreamId = "toto";
        assertEquals("toto", liveJsonSerializer.serialize(live).getString("liveStreamId"));
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
        Live live = liveJsonSerializer.deserializeProperties(jsonLive);
        assertEquals("Success", live.name);
    }
}