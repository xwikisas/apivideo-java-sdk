package video.api.java.sdk.infrastructure.unirest.live;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import video.api.java.sdk.domain.asset.Assets;
import video.api.java.sdk.domain.live.LiveStream;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LiveStreamJsonSerializer implements JsonSerializer<LiveStream> {
    @Override
    public LiveStream deserialize(JSONObject data) throws JSONException {
        LiveStream liveStream = new LiveStream(data.getString("name"));

        liveStream.liveStreamId = data.getString("liveStreamId");

        if (data.has("streamKey"))
            liveStream.streamKey = data.getString("streamKey");
        if (data.has("record"))
            liveStream.record = data.getBoolean("record");
        if (data.has("broadcasting"))
            liveStream.broadcasting = data.getBoolean("broadcasting");
        if (data.has("assets")) {
            liveStream.assets.putAll(convertAssets(data.getJSONObject("assets")));
        }
        if (data.has("playerId")) {
            liveStream.playerId = data.getString("playerId");
        }

        return liveStream;
    }

    private Map<String, String> convertAssets(JSONObject assets) {
        return assets.toMap().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> (String) e.getValue()));
    }

    @Override
    public List<LiveStream> deserialize(JSONArray data) throws JSONException {
        List<LiveStream> lives = new ArrayList<>();
        for (Object item : data) {
            lives.add(deserialize((JSONObject) item));
        }
        return lives;
    }

    @Override
    public JSONObject serialize(LiveStream object) throws JSONException {
        JSONObject data = new JSONObject();
        if (object.name != null) {
            data.put("name", object.name);
        }
        data.put("record", object.record);

        if (object.playerId != null) {
            data.put("playerId", object.playerId);
        }
        return data;
    }

}
