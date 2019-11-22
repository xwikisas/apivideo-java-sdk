package video.api.java.sdk.infrastructure.unirest.player;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import video.api.java.sdk.domain.player.models.Assets;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;

import java.util.List;

public class PlayerAssetsJsonSerializer implements JsonSerializer<Assets> {

    public Assets deserialize(JSONObject data) throws JSONException {

        Assets asset = new Assets();
        asset.link = data.getString("link");
        asset.logo = data.getString("logo");
        return asset;

    }

    @Override
    public List<Assets> deserialize(JSONArray data) throws JSONException {
        return null;
    }

    public JSONObject serialize(Assets object) throws JSONException {

        JSONObject data = new JSONObject();
        data.put("link", object.link);
        data.put("logo", object.logo);
        return data;

    }

}
