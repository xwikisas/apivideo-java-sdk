package video.api.java.sdk.infrastructure.unirest.live;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import video.api.java.sdk.domain.live.LiveStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LiveStreamDeserializerTest {

    private LiveStreamDeserializer deserializer = new LiveStreamDeserializer();

    @Test
    void deserializeMin() {
        JSONObject json = new JSONObject()
                .put("liveStreamId", "liSuccess")
                .put("name", "test");

        LiveStream liveStream = deserializer.deserialize(json);

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

        LiveStream liveStream = deserializer.deserialize(json);
        assertEquals(json.getString("streamKey"), liveStream.streamKey);
        assertEquals(json.getBoolean("record"), liveStream.record);
        assertEquals(json.getBoolean("broadcasting"), liveStream.broadcasting);
        assertEquals(assets.getString("iframe"), liveStream.assets.get("iframe"));
        assertEquals(assets.getString("player"), liveStream.assets.get("player"));
        assertEquals(assets.getString("hls"), liveStream.assets.get("hls"));
        assertEquals(assets.getString("thumbnail"), liveStream.assets.get("thumbnail"));
    }
}