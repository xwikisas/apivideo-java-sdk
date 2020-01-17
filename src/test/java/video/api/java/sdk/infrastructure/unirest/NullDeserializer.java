package video.api.java.sdk.infrastructure.unirest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonDeserializer;

import java.util.List;

public class NullDeserializer<T> implements JsonDeserializer<T> {
    @Override
    public T deserialize(JSONObject data) throws JSONException {
        return null;
    }

    @Override
    public List<T> deserialize(JSONArray data) throws JSONException {
        return null;
    }
}
