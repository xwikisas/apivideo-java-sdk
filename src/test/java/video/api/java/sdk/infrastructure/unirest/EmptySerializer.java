package video.api.java.sdk.infrastructure.unirest;

import org.json.JSONException;
import org.json.JSONObject;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;

public class EmptySerializer<T> implements JsonSerializer<T> {
    @Override
    public JSONObject serialize(T object) throws JSONException {
        return new JSONObject();
    }
}
