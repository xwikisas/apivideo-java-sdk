package video.api.java.sdk.infrastructure.unirest.player;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import video.api.java.sdk.domain.player.Player;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerJsonSerializerTest {
    private PlayerJsonSerializer playerJsonSerializer;
    private JSONObject           play;

    @BeforeEach
    void SetUp() {
        playerJsonSerializer = new PlayerJsonSerializer();
        play                 = new JSONObject("  {\n" +
                                                      "            \"playerId\": \"pl7icQgMd5uE3gajeWOy4oPQ\",\n" +
                                                      "            \"assets\": {\n" +
                                                      "                \"logo\": \"https://cdn-staging.api.video/player/pl7icQgMd5uE3gajeWOy4oPQ/logo.png\",\n" +
                                                      "                \"link\": \"https://api.video\"\n" +
                                                      "            },\n" +
                                                      "            \"shapeMargin\": 10,\n" +
                                                      "            \"shapeRadius\": 10,\n" +
                                                      "            \"shapeAspect\": \"flat\",\n" +
                                                      "            \"shapeBackgroundTop\": \"rgba(150, 150, 150, .7)\",\n" +
                                                      "            \"shapeBackgroundBottom\": \"rgba(150, 150, 150, .8)\",\n" +
                                                      "            \"text\": \"rgba(155, 155, 155, .95)\",\n" +
                                                      "            \"link\": \"rgba(155, 0, 0, .95)\",\n" +
                                                      "            \"linkHover\": \"rgba(155, 155, 155, .25)\",\n" +
                                                      "            \"linkActive\": \"rgba(25, 0, 0, .75)\",\n" +
                                                      "            \"trackPlayed\": \"rgba(25, 25, 25, .5)\",\n" +
                                                      "            \"trackUnplayed\": \"rgba(25, 25, 55, .1)\",\n" +
                                                      "            \"trackBackground\": \"rgba(10, 10, 10, .10)\",\n" +
                                                      "            \"backgroundTop\": \"rgba(172, 14, 145, .1)\",\n" +
                                                      "            \"backgroundBottom\": \"rgba(194, 195, 189, .11)\",\n" +
                                                      "            \"backgroundText\": \"rgba(25, 25, 25, .95)\",\n" +
                                                      "            \"language\": \"en\",\n" +
                                                      "            \"enableApi\": true,\n" +
                                                      "            \"enableControls\": true,\n" +
                                                      "            \"forceAutoplay\": false,\n" +
                                                      "            \"hideTitle\": true,\n" +
                                                      "            \"forceLoop\": false\n" +
                                                      "        }");

    }

    @Test
    void deserializeMax() {

        Player player = playerJsonSerializer.deserialize(play);


        assertEquals("rgba(194, 195, 189, .11)", playerJsonSerializer.colorToString(playerJsonSerializer.deserialize(play).backgroundBottom));
        assertEquals(player.playerId, "pl7icQgMd5uE3gajeWOy4oPQ");
        assertEquals(player.shapeMargin, 10);
        assertEquals(player.shapeRadius, 10);
        assertEquals(player.shapeAspect, "flat");
        assertEquals("rgba(150, 150, 150, .70)", playerJsonSerializer.colorToString(player.shapeBackgroundTop));
        assertEquals("rgba(150, 150, 150, .80)", playerJsonSerializer.colorToString(player.shapeBackgroundBottom));
        assertEquals("rgba(155, 155, 155, .95)", playerJsonSerializer.colorToString(player.text));
        assertEquals("rgba(155, 0, 0, .95)", playerJsonSerializer.colorToString(player.link));
        assertEquals("rgba(155, 155, 155, .25)", playerJsonSerializer.colorToString(player.linkHover));
        assertEquals("rgba(25, 0, 0, .75)", playerJsonSerializer.colorToString(player.linkActive));
        assertEquals("rgba(25, 25, 25, .50)", playerJsonSerializer.colorToString(player.trackPlayed));
        assertEquals("rgba(25, 25, 55, .10)", playerJsonSerializer.colorToString(player.trackUnplayed));
        assertEquals("rgba(10, 10, 10, .10)", playerJsonSerializer.colorToString(player.trackBackground));
        assertEquals("rgba(172, 14, 145, .10)", playerJsonSerializer.colorToString(player.backgroundTop));
        assertEquals("rgba(194, 195, 189, .11)", playerJsonSerializer.colorToString(player.backgroundBottom));
        assertEquals("rgba(25, 25, 25, .95)", playerJsonSerializer.colorToString(player.backgroundText));
        assertEquals("en", player.language);
        assertTrue(player.enableApi);
        assertTrue(player.enableControls);
        assertFalse(player.forceAutoplay);
        assertTrue(player.hideTitle);
        assertFalse(player.forceLoop);
        assertEquals("https://cdn-staging.api.video/player/pl7icQgMd5uE3gajeWOy4oPQ/logo.png", player.assets.logo);
        assertEquals("https://api.video", player.assets.link);


    }

    @Test
    void deserializeMin() {
        JSONObject play = new JSONObject("  {\n" +
                                                 "            \"playerId\": \"pl7icQgMd5uE3gajeWOy4oPQ\",\n" +
                                                 "            \"backgroundTop\": \"rgba(172, 14, 145, .1)\",\n" +
                                                 "            \"backgroundBottom\": \"rgba(194, 195, 189, 0.11)\",\n" +
                                                 "            \"backgroundText\": \"rgba(25, 25, 25, .95)\",\n" +
                                                 "            \"language\": \"en\",\n" +
                                                 "            \"forceLoop\": false\n" +
                                                 "        }");

        Player player = playerJsonSerializer.deserialize(play);


        assertEquals(player.playerId, "pl7icQgMd5uE3gajeWOy4oPQ");
        assertEquals(playerJsonSerializer.colorToString(player.backgroundTop), "rgba(172, 14, 145, .10)");
        assertEquals(playerJsonSerializer.colorToString(player.backgroundBottom), "rgba(194, 195, 189, .11)");
        assertEquals(playerJsonSerializer.colorToString(player.backgroundText), "rgba(25, 25, 25, .95)");
        assertEquals(player.language, "en");
        assertFalse(player.forceLoop);


    }

    @Test
    void serialize() {
        JSONArray players = new JSONArray();
        players.put(play);
        players.put(play);
        players.put(play);
        List<Player> pls = playerJsonSerializer.deserialize(players);
        assertEquals(pls.get(0).playerId, "pl7icQgMd5uE3gajeWOy4oPQ");
        assertEquals(pls.get(1).playerId, "pl7icQgMd5uE3gajeWOy4oPQ");
        assertEquals(pls.get(2).playerId, "pl7icQgMd5uE3gajeWOy4oPQ");
    }

    @Test
    void deserialize1() {


    }
}