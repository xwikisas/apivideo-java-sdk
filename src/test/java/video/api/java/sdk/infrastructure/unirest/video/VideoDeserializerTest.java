package video.api.java.sdk.infrastructure.unirest.video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import video.api.java.sdk.domain.video.Video;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VideoDeserializerTest {
    private VideoDeserializer deserializer = new VideoDeserializer();

    ///////////////Failure//////////////////////////

    @Test
    void deserializeFailure() {
        assertThrows(JSONException.class, () -> deserializer.deserialize(new JSONObject()));
    }

    @Test
    void deserializeAllFailure() {
        JSONArray array = new JSONArray();
        array.put(new JSONObject("{\"Fail\":\"ok\"}"));
        assertThrows(JSONException.class, () -> deserializer.deserialize(array));
    }

    ///////////////////////Success/////////////////////////////
    @Test
    void deserializeMinimalSuccess() {

        Video video = deserializer.deserialize(new JSONObject() {
            {
                put("videoId", "toto");
                put("publishedAt", "2019-08-28T16:25:51+02:00");
                put("updatedAt", "2019-08-28T16:25:51+02:00");
            }
        });

        assertEquals("toto", video.videoId);
    }

    @Test
    void deserializeMaximalSuccess() {
        JSONObject jsonVideo = new JSONObject()
                .put("videoId", "toto")
                .put("title", "toto")
                .put("description", "desc")
                .put("isPublic", true)
                .put("publishedAt", "2019-08-28T16:25:51+02:00")
                .put("updatedAt", "2019-08-28T16:25:51+02:00")
                .put("panoramic", true)
                .put("tags", new JSONArray()
                        .put("foo")
                        .put("bar"))
                .put("metadata", new JSONArray()
                        .put(new JSONObject()
                                     .put("key", "age")
                                     .put("value", "__age__")
                        )
                        .put(new JSONObject()
                                     .put("key", "actor")
                                     .put("value", "Nader")
                        ))
                .put("assets", new JSONObject()
                        .put("iframe", "<iframe src=\"https://embed.api.video/vod/virMgDJYvjHzoFZZYgMCkvC\" width=\"100%\" height=\"100%\" frameborder=\"0\" scrolling=\"no\" allowfullscreen=\"\"></iframe>")
                        .put("player", "https://embed.api.video/vod/virMgDJYvjHzoFZZYgMCkvC")
                        .put("hls", "https://cdn.api.video/vod/virMgDJYvjHzoFZZYgMCkvC/hls/manifest.m3u8")
                        .put("thumbnail", "https://cdn.api.video/vod/virMgDJYvjHzoFZZYgMCkvC/thumbnail.jpg")
                );

        Video video = deserializer.deserialize(jsonVideo);

        assertEquals("toto", video.videoId);
        assertEquals("toto", video.title);
        assertEquals("desc", video.description);
        assertTrue(video.isPublic);
        assertTrue(video.panoramic);
        assertEquals("foo", video.tags.get(0));
        assertEquals("bar", video.tags.get(1));
        assertEquals("__age__", video.metadata.get("age"));
        assertEquals("Nader", video.metadata.get("actor"));
        assertEquals(4, video.assets.size());
    }

    @Test
    void deserializeMediumSuccess() {

        JSONObject jsonVideo = new JSONObject() {
            {
                put("videoId", "vi1");
                put("title", "toto");
                put("description", "desc");
                put("isPublic", true);
                put("panoramic", true);
                put("publishedAt", "2019-08-28T16:25:51+02:00");
                put("updatedAt", "2019-08-28T16:25:51+02:00");
            }

        };

        Video video = deserializer.deserialize(jsonVideo);

        assertEquals("vi1", video.videoId);
        assertEquals("toto", video.title);
        assertEquals("desc", video.description);
        assertTrue(video.isPublic);
        assertTrue(video.panoramic);
    }

    @Test
    void deserialize() {
        JSONObject jsonVideo = new JSONObject("  {\n" +
                                                      "            \"videoId\": \"vi1\",\n" +
                                                      "            \"title\": \"Test Title\",\n" +
                                                      "            \"description\": \"Test create description\",\n" +
                                                      "            \"public\": true,\n" +
                                                      "            \"panoramic\": false,\n" +
                                                      "            \"publishedAt\": \"2019-08-28T16:25:51+02:00\",\n" +
                                                      "            \"updatedAt\": \"2019-08-28T16:25:51+02:00\",\n" +
                                                      "            \"tags\": [],\n" +
                                                      "            \"metadata\": [],\n" +
                                                      "            \"source\": {\n" +
                                                      "                \"type\": \"upload\",\n" +
                                                      "                \"uri\": \"/videos/vi5iemB77Z5VmZSoNz94dr2O/source\"\n" +
                                                      "            },\n" +
                                                      "            \"assets\": {\n" +
                                                      "                \"iframe\": \"<iframe src=\\\"https://embed.api.video/vod/vi5iemB77Z5VmZSoNz94dr2O\\\" width=\\\"100%\\\" height=\\\"100%\\\" frameborder=\\\"0\\\" scrolling=\\\"no\\\" allowfullscreen=\\\"\\\"></iframe>\",\n" +
                                                      "                \"player\": \"https://embed.api.video/vod/vi5iemB77Z5VmZSoNz94dr2O\",\n" +
                                                      "                \"hls\": \"https://cdn.api.video/vod/vi5iemB77Z5VmZSoNz94dr2O/hls/manifest.m3u8\",\n" +
                                                      "                \"thumbnail\": \"https://cdn.api.video/vod/vi5iemB77Z5VmZSoNz94dr2O/thumbnail.jpg\"\n" +
                                                      "            }\n" +
                                                      "        }");


        JSONObject jsonVideo1 = new JSONObject("  {\n" +
                                                       "            \"videoId\": \"vi2\",\n" +
                                                       "            \"title\": \"Test Title\",\n" +
                                                       "            \"description\": \"Test create description\",\n" +
                                                       "            \"public\": true,\n" +
                                                       "            \"panoramic\": false,\n" +
                                                       "            \"publishedAt\": \"2019-08-29T08:54:13+02:00\",\n" +
                                                       "            \"updatedAt\": \"2019-08-29T08:54:13+02:00\",\n" +
                                                       "            \"tags\": [],\n" +
                                                       "            \"metadata\": [],\n" +
                                                       "            \"source\": {\n" +
                                                       "                \"type\": \"upload\",\n" +
                                                       "                \"uri\": \"/videos/vi2SRWJ6ipruaD9K73CJbP0V/source\"\n" +
                                                       "            },\n" +
                                                       "            \"assets\": {\n" +
                                                       "                \"iframe\": \"<iframe src=\\\"https://embed.api.video/vod/vi2SRWJ6ipruaD9K73CJbP0V\\\" width=\\\"100%\\\" height=\\\"100%\\\" frameborder=\\\"0\\\" scrolling=\\\"no\\\" allowfullscreen=\\\"\\\"></iframe>\",\n" +
                                                       "                \"player\": \"https://embed.api.video/vod/vi2SRWJ6ipruaD9K73CJbP0V\",\n" +
                                                       "                \"hls\": \"https://cdn.api.video/vod/vi2SRWJ6ipruaD9K73CJbP0V/hls/manifest.m3u8\",\n" +
                                                       "                \"thumbnail\": \"https://cdn.api.video/vod/vi2SRWJ6ipruaD9K73CJbP0V/thumbnail.jpg\"\n" +
                                                       "            }\n" +
                                                       "        }");
        List<Video> videoList = deserializer.deserialize(new JSONArray() {{
            put(jsonVideo);
            put(jsonVideo1);
            put(jsonVideo);
        }});
        assertEquals("vi1", videoList.get(0).videoId);
        assertEquals("vi2", videoList.get(1).videoId);
        assertEquals("vi1", videoList.get(2).videoId);
    }
}