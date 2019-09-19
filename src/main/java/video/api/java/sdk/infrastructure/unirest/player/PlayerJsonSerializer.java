package video.api.java.sdk.infrastructure.unirest.player;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import video.api.java.sdk.domain.player.Player;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@SuppressWarnings("WeakerAccess")
public class PlayerJsonSerializer implements JsonSerializer<Player> {
    @Override
    public Player deserialize(JSONObject data) throws JSONException {

        Player                     player                     = new Player();
        PlayerAssetsJsonSerializer playerAssetsJsonSerializer = new PlayerAssetsJsonSerializer();
        player.playerId = data.getString("playerId");

        if (data.has("assets"))
            player.assets = playerAssetsJsonSerializer.deserialize(data.getJSONObject("assets"));

        if (data.has("shapeMargin"))
            player.shapeMargin = data.getInt("shapeMargin");

        if (data.has("shapeRadius"))
            player.shapeRadius = data.getInt("shapeRadius");

        if (data.has("shapeAspect"))
            player.shapeAspect = data.getString("shapeAspect");

        if (data.has("shapeBackgroundTop"))
            player.shapeBackgroundTop = parseToColor(data.getString("shapeBackgroundTop"));

        if (data.has("shapeBackgroundBottom"))
            player.shapeBackgroundBottom = parseToColor(data.getString("shapeBackgroundBottom"));

        if (data.has("text"))
            player.text = parseToColor(data.getString("text"));

        if (data.has("link"))
            player.link = parseToColor(data.getString("link"));

        if (data.has("linkHover"))
            player.linkHover = parseToColor(data.getString("linkHover"));

        if (data.has("linkActive"))
            player.linkActive = parseToColor(data.getString("linkActive"));

        if (data.has("trackPlayed"))
            player.trackPlayed = parseToColor(data.getString("trackPlayed"));

        if (data.has("trackUnplayed"))
            player.trackUnplayed = parseToColor(data.getString("trackUnplayed"));

        if (data.has("trackBackground"))
            player.trackBackground = parseToColor(data.getString("trackBackground"));

        if (data.has("backgroundTop"))
            player.backgroundTop = parseToColor(data.getString("backgroundTop"));

        if (data.has("backgroundBottom"))
            player.backgroundBottom = parseToColor(data.getString("backgroundBottom"));

        if (data.has("backgroundText"))
            player.backgroundText = parseToColor(data.getString("backgroundText"));

        if (data.has("language"))
            player.language = data.getString("language");

        if (data.has("shapeMargin"))
            player.shapeMargin = data.getInt("shapeMargin");

        if (data.has("shapeRadius"))
            player.shapeRadius = data.getInt("shapeMargin");

        if (data.has("enableApi"))
            player.enableApi = (boolean) data.get("enableApi");

        if (data.has("enableControls"))
            player.enableControls = (boolean) data.get("enableControls");

        if (data.has("forceAutoplay"))
            player.forceAutoplay = (boolean) data.get("forceAutoplay");

        if (data.has("hideTitle"))
            player.hideTitle = (boolean) data.get("hideTitle");

        if (data.has("forceLoop"))
            player.forceLoop = (boolean) data.get("forceLoop");


        return player;

    }

    @Override
    public JSONObject serialize(Player player) throws JSONException {

        JSONObject                 data                       = new JSONObject();
        PlayerAssetsJsonSerializer playerAssetsJsonSerializer = new PlayerAssetsJsonSerializer();

        data.put("playerId", player.playerId);
        data.put("assets", playerAssetsJsonSerializer.serialize(player.assets));
        data.put("shapeMargin", player.shapeMargin);
        data.put("shapeRadius", player.shapeRadius);
        data.put("shapeAspect", player.shapeAspect);
        data.put("shapeBackgroundTop", colorToString(player.shapeBackgroundTop));
        data.put("shapeBackgroundBottom", colorToString(player.shapeBackgroundBottom));
        data.put("text", colorToString(player.text));
        data.put("link", colorToString(player.link));
        data.put("linkHover", colorToString(player.linkHover));
        data.put("linkActive", colorToString(player.linkActive));
        data.put("trackPlayed", colorToString(player.trackPlayed));
        data.put("trackUnplayed", colorToString(player.trackUnplayed));
        data.put("trackBackground", colorToString(player.trackBackground));
        data.put("backgroundTop", colorToString(player.backgroundTop));
        data.put("backgroundBottom", colorToString(player.backgroundBottom));
        data.put("backgroundText", colorToString(player.backgroundText));
        data.put("language", player.language);
        data.put("shapeMargin", player.shapeMargin);
        data.put("shapeRadius", player.shapeMargin);
        data.put("enableApi", player.enableApi);
        data.put("enableControls", player.enableControls);
        data.put("forceAutoplay", player.forceAutoplay);
        data.put("hideTitle", player.hideTitle);
        data.put("forceLoop", player.forceLoop);


        return data;
    }


    @Override
    public List<Player> deserialize(JSONArray data) throws JSONException {


        List<Player> players = new ArrayList<>();
        for (Object item : data) {
            players.add(deserialize((JSONObject) item));
        }
        return players;
    }

    public JSONObject serializeProperties(Player player) throws JSONException {

        JSONObject data = new JSONObject();
        data.put("shapeMargin", player.shapeMargin);
        data.put("shapeRadius", player.shapeRadius);
        data.put("shapeAspect", player.shapeAspect);
        data.put("shapeBackgroundTop", colorToString(player.shapeBackgroundTop));
        data.put("shapeBackgroundBottom", colorToString(player.shapeBackgroundBottom));
        data.put("text", colorToString(player.text));
        data.put("link", colorToString(player.link));
        data.put("linkHover", colorToString(player.linkHover));
        data.put("linkActive", colorToString(player.linkActive));
        data.put("trackPlayed", colorToString(player.trackPlayed));
        data.put("trackUnplayed", colorToString(player.trackUnplayed));
        data.put("trackBackground", colorToString(player.trackBackground));
        data.put("backgroundTop", colorToString(player.backgroundTop));
        data.put("backgroundBottom", colorToString(player.backgroundBottom));
        data.put("backgroundText", colorToString(player.backgroundText));
        data.put("language", player.language);
        data.put("shapeMargin", player.shapeMargin);
        data.put("shapeRadius", player.shapeMargin);
        data.put("enableApi", player.enableApi);
        data.put("enableControls", player.enableControls);
        data.put("forceAutoplay", player.forceAutoplay);
        data.put("hideTitle", player.hideTitle);
        data.put("forceLoop", player.forceLoop);


        return data;
    }

    public Player deserializeProperties(JSONObject data) throws JSONException {

        Player player = new Player();

        if (data.has("shapeMargin"))
            player.shapeMargin = data.getInt("shapeMargin");

        if (data.has("shapeRadius"))
            player.shapeRadius = data.getInt("shapeRadius");

        if (data.has("shapeAspect"))
            player.shapeAspect = data.getString("shapeAspect");

        if (data.has("shapeBackgroundTop"))
            player.shapeBackgroundTop = parseToColor(data.getString("shapeBackgroundTop"));

        if (data.has("shapeBackgroundBottom"))
            player.shapeBackgroundBottom = parseToColor(data.getString("shapeBackgroundBottom"));

        if (data.has("text"))
            player.text = parseToColor(data.getString("text"));

        if (data.has("link"))
            player.link = parseToColor(data.getString("link"));

        if (data.has("linkHover"))
            player.linkHover = parseToColor(data.getString("linkHover"));

        if (data.has("linkActive"))
            player.linkActive = parseToColor(data.getString("linkActive"));

        if (data.has("trackPlayed"))
            player.trackPlayed = parseToColor(data.getString("trackPlayed"));

        if (data.has("trackUnplayed"))
            player.trackUnplayed = parseToColor(data.getString("trackUnplayed"));

        if (data.has("trackBackground"))
            player.trackBackground = parseToColor(data.getString("trackBackground"));

        if (data.has("backgroundTop"))
            player.backgroundTop = parseToColor(data.getString("backgroundTop"));

        if (data.has("backgroundBottom"))
            player.backgroundBottom = parseToColor(data.getString("backgroundBottom"));

        if (data.has("backgroundText"))
            player.backgroundText = parseToColor(data.getString("backgroundText"));

        if (data.has("language"))
            player.language = data.getString("language");

        if (data.has("shapeMargin"))
            player.shapeMargin = data.getInt("shapeMargin");

        if (data.has("shapeRadius"))
            player.shapeRadius = data.getInt("shapeMargin");

        if (data.has("enableApi"))
            player.enableApi = (boolean) data.get("enableApi");

        if (data.has("enableControls"))
            player.enableControls = (boolean) data.get("enableControls");

        if (data.has("forceAutoplay"))
            player.forceAutoplay = (boolean) data.get("forceAutoplay");

        if (data.has("hideTitle"))
            player.hideTitle = (boolean) data.get("hideTitle");

        if (data.has("forceLoop"))
            player.forceLoop = (boolean) data.get("forceLoop");

        return player;
    }

    public Color createColor(int red, int green, int blue, float alpha) {

        return new Color(red, green, blue, (int) (alpha * 256));
    }

    public String colorToString(Color color) {
        float alpha = (float) ((int) (1 + ((((float) color.getAlpha()) / 256) * 100))) / 100;
        if (color.getAlpha() == 192) {
            alpha = (float) 0.75;
        }
        if (color.getAlpha() == 128) {
            alpha = (float) 0.50;
        }
        if (color.getAlpha() == 64) {
            alpha = (float) 0.25;
        }
        int alphaInt = (int) (alpha * 100);

        return "rgba(" + color.getRed() + ", " + color.getGreen() + ", " + color.getBlue() + ", ." + alphaInt + ")";
    }

    public Color parseToColor(String input) {
        Pattern c = Pattern.compile("rgba *\\( *([0-9]+), *([0-9]+), *([0-9]+), ([+-]?([0-9]*[.])?[0-9]+) *\\)");
        Matcher m = c.matcher(input);

        if (m.matches()) {
            return new Color(Integer.parseInt(m.group(1)),  // r
                             Integer.parseInt(m.group(2)),  // g
                             Integer.parseInt(m.group(3)),  //b
                             (int) (Float.parseFloat((m.group(4))) * 256)); // alpha
        }

        return null;
    }

}
