package video.api.java.sdk.infrastructure.unirest.live;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import video.api.java.sdk.domain.live.LiveStream;
import video.api.java.sdk.infrastructure.unirest.asset.AssetsJsonSerializer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class LiveStreamJsonSerializerTest {

    private LiveStreamJsonSerializer serializer;

    @BeforeEach
    void setUp() {
        serializer = new LiveStreamJsonSerializer(new AssetsJsonSerializer());
    }

    @Test
    void deserializeMin() {
        JSONObject json = new JSONObject()
                .put("liveStreamId", "liSuccess")
                .put("name", "test");

        LiveStream liveStream = serializer.deserialize(json);

        assertEquals(json.getString("liveStreamId"), liveStream.liveStreamId);
        assertEquals(json.getString("name"), liveStream.name);
    }

    @Test
    void deserializeMax() {
        JSONObject assets = new JSONObject()
                .put("iframe", "<iframe src=\"...\" width=\"100%\" height=\"100%\" frameborder=\"0\" scrolling=\"no\" allowfullscreen=\"\"></iframe>")
                .put("player", "...")
                .put("hls", "...")
                .put("thumbnail", "...");

        JSONObject json = new JSONObject()
                .put("liveStreamId", "liSuccess")
                .put("name", "test")
                .put("streamKey", "xxx")
                .put("record", true)
                .put("broadcasting", true)
                .put("assets", assets);

        LiveStream liveStream = serializer.deserialize(json);
        assertEquals(json.getString("streamKey"), liveStream.streamKey);
        assertEquals(json.getBoolean("record"), liveStream.record);
        assertEquals(json.getBoolean("broadcasting"), liveStream.broadcasting);
        assertEquals(assets.getString("iframe"), liveStream.assets.iframe);
        assertEquals(assets.getString("player"), liveStream.assets.player);
        assertEquals(assets.getString("hls"), liveStream.assets.hls);
        assertEquals(assets.getString("thumbnail"), liveStream.assets.thumbnail);
    }

    @Test
    void serializeMin() {
        LiveStream liveStream = new LiveStream("test");

        JSONObject serialized = serializer.serialize(liveStream);

        assertEquals(liveStream.name, serialized.getString("name"));
        assertEquals(liveStream.record, serialized.getBoolean("record"));
        assertFalse(serialized.has("playerId"));
    }

    @Test
    void serializeMax() {
        LiveStream liveStream = new LiveStream("test");
        liveStream.record   = true;
        liveStream.playerId = "xxx";

        JSONObject serialized = serializer.serialize(liveStream);

        assertEquals(liveStream.record, serialized.getBoolean("record"));
        assertEquals(liveStream.playerId, serialized.getString("playerId"));
    }
}