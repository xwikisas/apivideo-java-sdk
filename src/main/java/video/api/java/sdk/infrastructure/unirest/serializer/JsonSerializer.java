package video.api.java.sdk.infrastructure.unirest.serializer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface JsonSerializer<T> {
    default JSONObject serialize(T object) throws JSONException {
        throw new UnsupportedOperationException("Not implemented");
    }

    T deserialize(JSONObject data) throws JSONException;

    default List<T> deserialize(JSONArray data) throws JSONException {
        List<T> list = new ArrayList<>();

        for (Object item : data) {
            list.add(deserialize((JSONObject) item));
        }

        return list;
    }

    default Map<String, String> convertKeyValueJsonArrayToMap(JSONArray array) {
        Map<String, String> map = new HashMap<>();

        for(Object object: array) {
            JSONObject jsonObject = (JSONObject) object;

            String value;
            try {
                value = jsonObject.getString("value");
            }
            catch (JSONException e) {
                value = null;
            }

            map.put(jsonObject.getString("key"), value);
        }

        return map;
    }
}
