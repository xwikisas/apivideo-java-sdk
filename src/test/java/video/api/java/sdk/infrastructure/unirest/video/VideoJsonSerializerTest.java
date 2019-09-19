package video.api.java.sdk.infrastructure.unirest.video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import video.api.java.sdk.domain.video.Video;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VideoJsonSerializerTest {
    private VideoJsonSerializer videoJsonSerializer;

    @BeforeEach
    void setUp() {
        videoJsonSerializer = new VideoJsonSerializer();
    }

    ///////////////Failure//////////////////////////

    @Test
    void deserializeFailure() {
        assertThrows(JSONException.class, () -> videoJsonSerializer.deserialize(new JSONObject()));
    }

    @Test
    void deserializeAllFailure() {
        JSONArray array = new JSONArray();
        array.put(new JSONObject("{\"Fail\":\"ok\"}"));
        assertThrows(JSONException.class, () -> videoJsonSerializer.deserialize(array));
    }

    ///////////////////////Success/////////////////////////////
    @Test
    void deserializeMinimalSuccess() {

        Video video = videoJsonSerializer.deserialize(new JSONObject() {
            {
                put("videoId", "toto");
            }
        });

        assertEquals("toto", video.videoId);
    }

    @Test
    void deserializeMaximalSuccess() {
        JSONArray tags = new JSONArray() {{
            put("tata");
            put("titi");
        }};

        JSONArray metadata = new JSONArray() {
        };


        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("key", "age");
        hashMap.put("value", "__age__");
        HashMap<String, String> hashMap2 = new HashMap<>();
        hashMap2.put("key", "actor");
        hashMap2.put("value", "Nader");
        metadata.put(new JSONObject(hashMap));
        metadata.put(new JSONObject(hashMap2));

        JSONObject jsonVideo = new JSONObject() {
            {
                put("videoId", "toto");
                put("title", "toto");
                put("description", "desc");
                put("isPublic", true);
                put("panoramic", true);
                put("tags", tags);
                put("metadata", metadata);

                // TODO
            }

        };

        Video video = videoJsonSerializer.deserialize(jsonVideo);

        assertEquals("toto", video.videoId);
        assertEquals("toto", video.title);
        assertEquals("desc", video.description);
        assertTrue(video.isPublic);
        assertTrue(video.panoramic);
        assertEquals("tata", video.tags.get(0));
        assertEquals("titi", video.tags.get(1));
        assertEquals("__age__", video.metadata.get("age"));
        assertEquals("Nader", video.metadata.get("actor"));
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
                // TODO
            }

        };

        Video video = videoJsonSerializer.deserialize(jsonVideo);

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
                                                      "            \"tags\": [],\n" +
                                                      "            \"metadata\": [],\n" +
                                                      "            \"source\": {\n" +
                                                      "                \"type\": \"upload\",\n" +
                                                      "                \"uri\": \"/videos/vi5iemB77Z5VmZSoNz94dr2O/source\"\n" +
                                                      "            },\n" +
                                                      "            \"assets\": {\n" +
                                                      "                \"iframe\": \"<iframe src=\\\"https://embed-staging.api.video/vod/vi5iemB77Z5VmZSoNz94dr2O\\\" width=\\\"100%\\\" height=\\\"100%\\\" frameborder=\\\"0\\\" scrolling=\\\"no\\\" allowfullscreen=\\\"\\\"></iframe>\",\n" +
                                                      "                \"player\": \"https://embed-staging.api.video/vod/vi5iemB77Z5VmZSoNz94dr2O\",\n" +
                                                      "                \"hls\": \"https://cdn-staging.api.video/vod/vi5iemB77Z5VmZSoNz94dr2O/hls/manifest.m3u8\",\n" +
                                                      "                \"thumbnail\": \"https://cdn-staging.api.video/vod/vi5iemB77Z5VmZSoNz94dr2O/thumbnail.jpg\"\n" +
                                                      "            }\n" +
                                                      "        }");


        JSONObject jsonVideo1 = new JSONObject("  {\n" +
                                                       "            \"videoId\": \"vi2\",\n" +
                                                       "            \"title\": \"Test Title\",\n" +
                                                       "            \"description\": \"Test create description\",\n" +
                                                       "            \"public\": true,\n" +
                                                       "            \"panoramic\": false,\n" +
                                                       "            \"publishedAt\": \"2019-08-29T08:54:13+02:00\",\n" +
                                                       "            \"tags\": [],\n" +
                                                       "            \"metadata\": [],\n" +
                                                       "            \"source\": {\n" +
                                                       "                \"type\": \"upload\",\n" +
                                                       "                \"uri\": \"/videos/vi2SRWJ6ipruaD9K73CJbP0V/source\"\n" +
                                                       "            },\n" +
                                                       "            \"assets\": {\n" +
                                                       "                \"iframe\": \"<iframe src=\\\"https://embed-staging.api.video/vod/vi2SRWJ6ipruaD9K73CJbP0V\\\" width=\\\"100%\\\" height=\\\"100%\\\" frameborder=\\\"0\\\" scrolling=\\\"no\\\" allowfullscreen=\\\"\\\"></iframe>\",\n" +
                                                       "                \"player\": \"https://embed-staging.api.video/vod/vi2SRWJ6ipruaD9K73CJbP0V\",\n" +
                                                       "                \"hls\": \"https://cdn-staging.api.video/vod/vi2SRWJ6ipruaD9K73CJbP0V/hls/manifest.m3u8\",\n" +
                                                       "                \"thumbnail\": \"https://cdn-staging.api.video/vod/vi2SRWJ6ipruaD9K73CJbP0V/thumbnail.jpg\"\n" +
                                                       "            }\n" +
                                                       "        }");
        List<Video> videoList = videoJsonSerializer.deserialize(new JSONArray() {{
            put(jsonVideo);
            put(jsonVideo1);
            put(jsonVideo);
        }});
        assertEquals("vi1", videoList.get(0).videoId);
        assertEquals("vi2", videoList.get(1).videoId);
        assertEquals("vi1", videoList.get(2).videoId);
    }

    @Test
    void serialize() {
        Video video = new Video();
        video.videoId = "vi";
        JSONObject jsonVideo = videoJsonSerializer.serialize(video);
        assertEquals("vi", jsonVideo.getString("videoId"));
    }

    @Test
    void serializeProperties() {
        Video video = new Video();
        video.title = "viTitle";
        JSONObject jsonVideo = videoJsonSerializer.serialize(video);
        assertEquals("viTitle", jsonVideo.getString("title"));

    }
}