package video.api.java.sdk.infrastructure.unirest.live;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import video.api.java.sdk.domain.live.LiveStream;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;
import video.api.java.sdk.infrastructure.unirest.video.serializers.AssetsSerializer;

import java.util.ArrayList;
import java.util.List;

public class LiveStreamJsonSerializer implements JsonSerializer<LiveStream> {

    @Override
    public LiveStream deserialize(JSONObject data) throws JSONException {
        LiveStream       liveStream       = new LiveStream();
        AssetsSerializer assetsSerializer = new AssetsSerializer();
        liveStream.liveStreamId = data.getString("liveStreamId");
        if (data.has("streamKey"))
            liveStream.streamKey = data.getString("streamKey");
        if (data.has("name"))
            liveStream.name = data.getString("name");
        if (data.has("record"))
            liveStream.record = data.getBoolean("record");
        if (data.has("broadcasting"))
            liveStream.broadcasting = data.getBoolean("broadcasting");
        if (data.has("assets"))
            liveStream.assets = assetsSerializer.deserialize(data.getJSONObject("assets"));
        if (data.has("playerId")) {
            liveStream.playerId = data.getString("playerId");
        }

        return liveStream;
    }

    @Override
    public List<LiveStream> deserialize(JSONArray data) throws JSONException {

        List<LiveStream> lives = new ArrayList<LiveStream>();
        for (Object item : data) {
            lives.add(deserialize((JSONObject) item));
        }
        return lives;
    }

    @Override
    public JSONObject serialize(LiveStream liveStream) throws JSONException {
        AssetsSerializer assetsSerializer = new AssetsSerializer();
        JSONObject       data             = new JSONObject();
        data.put("liveStreamId", liveStream.liveStreamId);
        data.put("streamKey", liveStream.streamKey);
        data.put("name", liveStream.name);
        data.put("record", liveStream.record);
        data.put("broadcasting", liveStream.broadcasting);
        data.put("assets", assetsSerializer.serialize(liveStream.assets));
        if (liveStream.playerId != null) {
            data.put("playerId", liveStream.playerId);
        }
        return data;
    }


    public JSONObject serializeProperties(LiveStream liveStream) throws JSONException {

        JSONObject data = new JSONObject();
        if (liveStream.name != null) {
            data.put("name", liveStream.name);
        }
        data.put("record", liveStream.record);

        if (liveStream.playerId != null) {
            data.put("playerId", liveStream.playerId);
        }
        return data;

    }

    public LiveStream deserializeProperties(JSONObject data) throws JSONException {

        LiveStream liveStream = new LiveStream();
        if (data.has("name")) {
            liveStream.name = data.getString("name");
        }
        if (data.has("record")) {

            liveStream.record = data.getBoolean("record");
        }
        if (data.has("playerId")) {
            liveStream.playerId = data.getString("playerId");
        }
        return liveStream;

    }
}
