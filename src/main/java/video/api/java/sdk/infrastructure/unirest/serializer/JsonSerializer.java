package video.api.java.sdk.infrastructure.unirest.serializer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public interface JsonSerializer<T> {
    JSONObject serialize(T t) throws JSONException;

    T deserialize(JSONObject data) throws JSONException;

    List<T> deserialize(JSONArray data) throws JSONException;
}
