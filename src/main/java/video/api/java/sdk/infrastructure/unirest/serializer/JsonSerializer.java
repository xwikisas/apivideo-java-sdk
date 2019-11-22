package video.api.java.sdk.infrastructure.unirest.serializer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import video.api.java.sdk.domain.analytics.PlayerSessionEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        return array.toList().stream().collect(Collectors.toMap(
                map -> ((Map) map).get("key").toString(),
                map -> ((Map) map).get("value").toString())
        );
    }
}
