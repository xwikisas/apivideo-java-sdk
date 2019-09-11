package video.api.java.sdk.infrastructure.unirest.video.serializers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import video.api.java.sdk.domain.video.models.Quality;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;

import java.util.Iterator;
import java.util.List;

public class QualitySerializer implements JsonSerializer<Quality> {

    @Override
    public Quality deserialize(JSONObject data) throws JSONException {
        Quality quality = new Quality();
        quality.quality = data.getString("quality");
        quality.status  = data.getString("status");
        return quality;
    }

    @Override
    public List<Quality> deserialize(JSONArray data) throws JSONException {
        return null;
    }


    @Override
    public JSONObject serialize(Quality quality) throws JSONException {

        JSONObject data = new JSONObject();
        data.put("quality", quality.quality);
        data.put("status", quality.status);
        return data;

    }

    @Override
    public JSONObject serializeProperties(Quality quality) throws JSONException {
        return null;
    }


    public JSONArray serialize(List<Quality> quality) throws JSONException {
        JSONArray tabs = new JSONArray();
        for (Iterator<Quality> it = quality.iterator(); it.hasNext(); ) {
            tabs.put(serialize(it.next()));
        }
        return tabs;
    }
}
