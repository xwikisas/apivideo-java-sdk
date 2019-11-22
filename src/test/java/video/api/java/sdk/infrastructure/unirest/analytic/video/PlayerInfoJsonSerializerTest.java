package video.api.java.sdk.infrastructure.unirest.analytic.video;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import video.api.java.sdk.domain.analytic.PlayerSession;
import video.api.java.sdk.infrastructure.unirest.analytic.PlayerSessionJsonSerializer;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerInfoJsonSerializerTest {
    private PlayerSessionJsonSerializer playerSessionJsonSerializer;

    @BeforeEach
    void SetUp() {
        playerSessionJsonSerializer = new PlayerSessionJsonSerializer();
    }


    @Test
    void deserializeMax() {

        JSONObject analytic = new JSONObject("{\n" +
                                                     "    \"data\": [\n" +
                                                     "        {\n" +
                                                     "            \"session\": {\n" +
                                                     "                \"sessionId\": \"ps3uBmLoqYV2O4k9BOeSQ6Sm\",\n" +
                                                     "                \"loadedAt\": \"2019-09-09 06:41:06.683+00\",\n" +
                                                     "                \"endedAt\": \"2019-09-09 06:41:27.606+00\",\n" +
                                                     "                \"metadatas\": [\n" +
                                                     "                    {\n" +
                                                     "                        \"key\": \"age\",\n" +
                                                     "                        \"value\": null\n" +
                                                     "                    }\n" +
                                                     "                ]\n" +
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
                                                     "        }\n" +
                                                     "    ]\n" +
                                                     "}");

        PlayerSession playerSession = playerSessionJsonSerializer.deserialize(analytic);
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
                                                     "                \"metadatas\": [\n" +
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
        JSONArray analytics = new JSONArray();

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
                                                     "                \"metadatas\": [\n" +
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

        analytics.put(analytic);
        analytics.put(analytic);
        analytics.put(analytic);
        analytics.put(analytic);
        assertEquals(4, playerSessionJsonSerializer.deserialize(analytics).size());


    }
}