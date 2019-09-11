package video.api.java.sdk.infrastructure.unirest.caption;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import video.api.java.sdk.domain.caption.Caption;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class CaptionJsonSerializerTest {
    private CaptionJsonSerializer captionJsonSerializer;

    @BeforeEach
    void SetUp() {
        captionJsonSerializer = new CaptionJsonSerializer();

    }

    @Test
    void deserializeMax() {


        Caption caption = captionJsonSerializer.deserialize(new JSONObject("{\n" +
                                                                                   "    \"uri\": \"tata\",\n" +
                                                                                   "    \"src\": \"vtt\",\n" +
                                                                                   "    \"srclang\": \"en\",\n" +
                                                                                   "    \"default\": false\n" +
                                                                                   "}"));
        assertEquals("tata", caption.uri);
        assertEquals("vtt", caption.src);
        assertEquals("en", caption.srclang);
        assertFalse(caption.isDefault);

    }


    @Test
    void deserializeMin() {


        Caption caption = captionJsonSerializer.deserialize(new JSONObject("{\n" +
                                                                                   "    \"uri\": \"tata\",\n" +
                                                                                   "}"));
        assertEquals("tata", caption.uri);

    }


    @Test
    void deserializeAll() {
        JSONArray captions = new JSONArray();
        JSONObject caption = new JSONObject("{\n" +
                                                    "    \"uri\": \"tata\",\n" +
                                                    "    \"src\": \"vtt\",\n" +
                                                    "    \"srclang\": \"en\",\n" +
                                                    "    \"default\": false\n" +
                                                    "}");
        JSONObject caption1 = new JSONObject("{\n" +
                                                     "    \"uri\": \"tata\",\n" +
                                                     "    \"src\": \"vtt\",\n" +
                                                     "    \"srclang\": \"fr\",\n" +
                                                     "    \"default\": false\n" +
                                                     "}");
        JSONObject caption2 = new JSONObject("{\n" +
                                                     "    \"uri\": \"tata\",\n" +
                                                     "    \"src\": \"vtt\",\n" +
                                                     "    \"srclang\": \"esp\",\n" +
                                                     "    \"default\": false\n" +
                                                     "}");

        captions.put(caption);
        captions.put(caption1);
        captions.put(caption2);
        assertEquals(captionJsonSerializer.deserialize(captions).get(0).srclang, "en");
        assertEquals(captionJsonSerializer.deserialize(captions).get(1).srclang, "fr");
        assertEquals(captionJsonSerializer.deserialize(captions).get(2).srclang, "esp");

    }

    @Test
    void serialize() {
        JSONObject caption = new JSONObject("{\n" +
                                                    "    \"uri\": \"tata\",\n" +
                                                    "    \"src\": \"vtt\",\n" +
                                                    "    \"srclang\": \"en\",\n" +
                                                    "    \"default\": false\n" +
                                                    "}");

        assertEquals(caption.toString(), captionJsonSerializer.serialize(captionJsonSerializer.deserialize(caption)).toString());


    }

    @Test
    void serializeProperties() {
        JSONObject caption = new JSONObject("{\n" +
                                                    "    \"srclang\": \"en\",\n" +
                                                    "    \"default\": false\n" +
                                                    "}");

        assertEquals(caption.toString(), captionJsonSerializer.serializeProperties(captionJsonSerializer.deserialize(caption)).toString());


    }

}
