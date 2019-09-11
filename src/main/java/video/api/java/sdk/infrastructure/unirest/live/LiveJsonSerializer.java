package video.api.java.sdk.infrastructure.unirest.live;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import video.api.java.sdk.domain.live.Live;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;
import video.api.java.sdk.infrastructure.unirest.video.serializers.AssetsSerializer;

import java.util.ArrayList;
import java.util.List;

public class LiveJsonSerializer implements JsonSerializer<Live> {

    @Override
    public Live deserialize(JSONObject data) throws JSONException {
        Live             live             = new Live();
        AssetsSerializer assetsSerializer = new AssetsSerializer();
        live.liveStreamId = data.getString("liveStreamId");
        if (data.has("streamKey"))
            live.streamKey = data.getString("streamKey");
        if (data.has("name"))
            live.name = data.getString("name");
        if (data.has("record"))
            live.record = data.getBoolean("record");
        if (data.has("broadcasting"))
            live.broadcasting = data.getBoolean("broadcasting");
        if (data.has("assets"))
            live.assets = assetsSerializer.deserialize(data.getJSONObject("assets"));
        if (data.has("playerId")) {
            live.playerId = data.getString("playerId");
        }

        return live;
    }

    @Override
    public List<Live> deserialize(JSONArray data) throws JSONException {

        List<Live> lives = new ArrayList<Live>();
        for (Object item : data) {
            lives.add(deserialize((JSONObject) item));
        }
        return lives;
    }

    @Override
    public JSONObject serialize(Live live) throws JSONException {
        AssetsSerializer assetsSerializer = new AssetsSerializer();
        JSONObject       data             = new JSONObject();
        data.put("liveStreamId", live.liveStreamId);
        data.put("streamKey", live.streamKey);
        data.put("name", live.name);
        data.put("record", live.record);
        data.put("broadcasting", live.broadcasting);
        data.put("assets", assetsSerializer.serialize(live.assets));
        if (live.playerId != null) {
            data.put("playerId", live.playerId);
        }
        return data;
    }


    public JSONObject serializeProperties(Live live) throws JSONException {

        JSONObject data = new JSONObject();
        if (live.name != null) {
            data.put("name", live.name);
        }
        data.put("record", live.record);

        if (live.playerId != null) {
            data.put("playerId", live.playerId);
        }
        return data;

    }

    public Live deserializeProperties(JSONObject data) throws JSONException {

        Live live = new Live();
        if (data.has("name")) {
            live.name = data.getString("name");
        }
        if (data.has("record")) {

            live.record = data.getBoolean("record");
        }
        if (data.has("playerId")) {
            live.playerId = data.getString("playerId");
        }
        return live;

    }
}
