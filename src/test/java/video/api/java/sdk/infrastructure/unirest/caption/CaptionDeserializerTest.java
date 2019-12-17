package video.api.java.sdk.infrastructure.unirest.caption;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import video.api.java.sdk.domain.caption.Caption;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class CaptionDeserializerTest {
    private CaptionDeserializer captionDeserializer;

    @BeforeEach
    void SetUp() {
        captionDeserializer = new CaptionDeserializer();

    }

    @Test
    void deserializeMax() {


        Caption caption = captionDeserializer.deserialize(new JSONObject("{\n" +
                                                                                   "    \"uri\": \"tata\",\n" +
                                                                                   "    \"src\": \"vtt\",\n" +
                                                                                   "    \"srclang\": \"en\",\n" +
                                                                                   "    \"default\": false\n" +
                                                                                   "}"));
        assertEquals("tata", caption.uri);
        assertEquals("vtt", caption.src);
        assertEquals("en", caption.language);
        assertFalse(caption.isDefault);

    }


    @Test
    void deserializeMin() {


        Caption caption = captionDeserializer.deserialize(new JSONObject("{\n" +
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
        assertEquals(captionDeserializer.deserialize(captions).get(0).language, "en");
        assertEquals(captionDeserializer.deserialize(captions).get(1).language, "fr");
        assertEquals(captionDeserializer.deserialize(captions).get(2).language, "esp");
    }

}
