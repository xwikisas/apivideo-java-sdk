package video.api.java.sdk.infrastructure.unirest.video;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import video.api.java.sdk.domain.video.VideoInput;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VideoInputSerializerTest {
    private VideoInputSerializer serializer = new VideoInputSerializer();

    @Test
    void serialize() {
        VideoInput video = new VideoInput();
        video.title = "foo";
        JSONObject jsonVideo = serializer.serialize(video);
        assertEquals("foo", jsonVideo.getString("title"));
    }
}
