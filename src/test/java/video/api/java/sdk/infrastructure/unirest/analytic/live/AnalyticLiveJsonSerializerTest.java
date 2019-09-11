package video.api.java.sdk.infrastructure.unirest.analytic.live;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import video.api.java.sdk.domain.analytic.analyticLive.AnalyticLive;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AnalyticLiveJsonSerializerTest {
    AnalyticLiveJsonSerializer analyticLiveJsonSerializer;

    @BeforeEach
    void SetUp() {
        analyticLiveJsonSerializer = new AnalyticLiveJsonSerializer();
    }

    @Test
    void deserialize() {
        JSONObject analytic = new JSONObject("        {\n" +
                                                     "            \"live\": {\n" +
                                                     "                \"liveStreamId\": \"liSuccess\",\n" +
                                                     "                \"name\": \"updated video\",\n" +
                                                     "    },\n" +
                                                     "            \"period\": \"2019-09-09\",\n" +
                                                     "            \"data\": [\n" +
                                                     "                {\n" +
                                                     "                    \"session\": {\n" +
                                                     "                        \"sessionId\": \"ps3uBmLoqYV2O4k9BOeSQ6Sm\",\n" +
                                                     "                        \"loadedAt\": \"2019-09-09 06:41:06.683+00\",\n" +
                                                     "                        \"endedAt\": \"2019-09-09 06:41:27.606+00\",\n" +
                                                     "                    },\n" +
                                                     "                    \"location\": {\n" +
                                                     "                        \"country\": \"France\",\n" +
                                                     "                        \"city\": \"Vaulx-en-Velin\"\n" +
                                                     "                    },\n" +
                                                     "                    \"referrer\": {\n" +
                                                     "                        \"url\": \"unknown\",\n" +
                                                     "                        \"medium\": \"unknown\",\n" +
                                                     "                        \"source\": \"unknown\",\n" +
                                                     "                        \"searchTerm\": \"unknown\"\n" +
                                                     "                    },\n" +
                                                     "                    \"device\": {\n" +
                                                     "                        \"type\": \"smartphone\",\n" +
                                                     "                        \"vendor\": \"Huawei\",\n" +
                                                     "                        \"model\": \"P8 Lite (2017)\"\n" +
                                                     "                    },\n" +
                                                     "                    \"os\": {\n" +
                                                     "                        \"name\": \"unknown\",\n" +
                                                     "                        \"shortname\": \"unknown\",\n" +
                                                     "                        \"version\": \"unknown\"\n" +
                                                     "                    },\n" +
                                                     "                    \"client\": {\n" +
                                                     "                        \"type\": \"browser\",\n" +
                                                     "                        \"name\": \"Chrome Mobile\",\n" +
                                                     "                        \"version\": \"62.0\"\n" +
                                                     "                    }\n" +
                                                     "                }\n" +
                                                     "            ]\n" +
                                                     "        }\n");
        AnalyticLive analyticLive = analyticLiveJsonSerializer.deserialize(analytic);
        assertEquals("liSuccess", analyticLive.liveStreamId);

    }

    @Test
    void serialize() {
    }

    @Test
    void deserializeMin() {
        JSONObject analytic = new JSONObject("        {\n" +
                                                     "            \"live\": {\n" +
                                                     "                \"liveStreamId\": \"liSuccess\",\n" +
                                                     "                \"name\": \"updated video\",\n" +
                                                     "    },\n" +
                                                     "            \"period\": \"2019-09-09\",\n" +
                                                     "            \"data\": [\n" +
                                                     "                {\n" +
                                                     "                    \"session\": {\n" +
                                                     "                        \"sessionId\": \"ps3uBmLoqYV2O4k9BOeSQ6Sm\",\n" +
                                                     "                        \"loadedAt\": \"2019-09-09 06:41:06.683+00\",\n" +
                                                     "                        \"endedAt\": \"2019-09-09 06:41:27.606+00\",\n" +
                                                     "                    },\n" +
                                                     "                    \"client\": {\n" +
                                                     "                        \"type\": \"browser\",\n" +
                                                     "                        \"name\": \"Chrome Mobile\",\n" +
                                                     "                        \"version\": \"62.0\"\n" +
                                                     "                    }\n" +
                                                     "                }\n" +
                                                     "            ]\n" +
                                                     "        }\n");
        AnalyticLive analyticLive = analyticLiveJsonSerializer.deserialize(analytic);
        assertEquals("liSuccess", analyticLive.liveStreamId);

    }
}