package video.api.java.sdk.infrastructure.unirest.analytics;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import video.api.java.sdk.domain.analytics.PlayerSession;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerSessionJsonSerializerTest {
    private PlayerSessionJsonSerializer playerSessionJsonSerializer;

    @BeforeEach
    void SetUp() {
        playerSessionJsonSerializer = new PlayerSessionJsonSerializer();
    }


    @Test
    void deserializeMax() {

        JSONObject data = new JSONObject(
                "        {\n" +
                        "            \"session\": {\n" +
                        "                \"sessionId\": \"ps3uBmLoqYV2O4k9BOeSQ6Sm\",\n" +
                        "                \"loadedAt\": \"2019-09-09 06:41:06.683+00\",\n" +
                        "                \"endedAt\": \"2019-09-09 06:41:27.606+00\",\n" +
                        "            },\n" +
                        "            \"location\": {\n" +
                        "                \"country\": \"France\",\n" +
                        "                \"city\": \"Vaulx-en-Velin\"\n" +
                        "            },\n" +
                        "            \"referrer\": {\n" +
                        "                \"url\": \"unknown\",\n" +
                        "                \"medium\": \"unknown\",\n" +
                        "                \"source\": \"unknown\",\n" +
                        "                \"searchTerm\": \"unknown\"\n" +
                        "            },\n" +
                        "            \"device\": {\n" +
                        "                \"type\": \"smartphone\",\n" +
                        "                \"vendor\": \"Huawei\",\n" +
                        "                \"model\": \"P8 Lite (2017)\"\n" +
                        "            },\n" +
                        "            \"os\": {\n" +
                        "                \"name\": \"unknown\",\n" +
                        "                \"shortname\": \"unknown\",\n" +
                        "                \"version\": \"unknown\"\n" +
                        "            },\n" +
                        "            \"client\": {\n" +
                        "                \"type\": \"browser\",\n" +
                        "                \"name\": \"Chrome Mobile\",\n" +
                        "                \"version\": \"62.0\"\n" +
                        "            }\n" +
                        "        }"
        );

        data.getJSONObject("session").put("metadata", new JSONArray(new ArrayList<JSONObject>() {{
            add(new JSONObject().put("key", "age").put("value", (String) null));
            add(new JSONObject().put("key", "name").put("value", "Foo"));
        }}));

        PlayerSession playerSession = playerSessionJsonSerializer.deserialize(data);
        assertEquals(null, playerSession.info.metadata.get("age"));
        assertEquals("Foo", playerSession.info.metadata.get("name"));
        //assertEquals("viSuccess", playerSession.);
    }

    @Test
    void deserializeMin() {

        JSONObject analytic = new JSONObject("{\n" +
                                                     "    \"video\": {\n" +
                                                     "        \"videoId\": \"viSuccess\",\n" +
                                                     "        \"title\": \"updated video\",\n" +
                                                     "    },\n" +
                                                     "    \"period\": \"2019-09-09\",\n" +
                                                     "    \"data\": [\n" +
                                                     "        {\n" +
                                                     "            \"session\": {\n" +
                                                     "                \"sessionId\": \"ps3uBmLoqYV2O4k9BOeSQ6Sm\",\n" +
                                                     "                \"loadedAt\": \"2019-09-09 06:41:06.683+00\",\n" +
                                                     "                \"endedAt\": \"2019-09-09 06:41:27.606+00\",\n" +
                                                     "                \"metadata\": [\n" +
                                                     "                    {\n" +
                                                     "                        \"key\": \"age\",\n" +
                                                     "                        \"value\": null\n" +
                                                     "                    }\n" +
                                                     "                ]\n" +
                                                     "            },\n" +
                                                     "            \"device\": {\n" +
                                                     "                \"type\": \"smartphone\",\n" +
                                                     "                \"vendor\": \"Huawei\",\n" +
                                                     "                \"model\": \"P8 Lite (2017)\"\n" +
                                                     "            },\n" +
                                                     "        }\n" +
                                                     "    ]\n" +
                                                     "}");

        PlayerSession playerSession = playerSessionJsonSerializer.deserialize(analytic);
        //assertEquals("viSuccess", playerSession.videoId);


    }


    @Test
    void deserializeAll() {
        JSONArray collection = new JSONArray();

        JSONObject item = new JSONObject(
                "{\n" +
                        "            \"session\": {\n" +
                        "                \"sessionId\": \"ps3uBmLoqYV2O4k9BOeSQ6Sm\",\n" +
                        "                \"loadedAt\": \"2019-09-09 06:41:06.683+00\",\n" +
                        "                \"endedAt\": \"2019-09-09 06:41:27.606+00\",\n" +
                        "                \"metadata\": [\n" +
                        "                    {\n" +
                        "                        \"key\": \"age\",\n" +
                        "                        \"value\": null\n" +
                        "                    }\n" +
                        "                ]\n" +
                        "            },\n" +
                        "            \"device\": {\n" +
                        "                \"type\": \"smartphone\",\n" +
                        "                \"vendor\": \"Huawei\",\n" +
                        "                \"model\": \"P8 Lite (2017)\"\n" +
                        "            },\n" +
                        "        }\n"
        );

        collection.put(item);
        collection.put(item);
        collection.put(item);
        collection.put(item);
        assertEquals(4, playerSessionJsonSerializer.deserialize(collection).size());


    }
}