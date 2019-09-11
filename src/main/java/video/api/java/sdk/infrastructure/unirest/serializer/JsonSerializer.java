package video.api.java.sdk.infrastructure.unirest.serializer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public interface JsonSerializer<T> {


    T deserialize(JSONObject data) throws JSONException;

    List<T> deserialize(JSONArray data) throws JSONException;

    JSONObject serialize(T t) throws JSONException;

    JSONObject serializeProperties(T t) throws JSONException;
}
