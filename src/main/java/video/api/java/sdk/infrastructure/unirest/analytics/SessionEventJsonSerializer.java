package video.api.java.sdk.infrastructure.unirest.analytics;

import org.json.JSONException;
import org.json.JSONObject;
import video.api.java.sdk.domain.analytics.PlayerSessionEvent;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;

public class SessionEventJsonSerializer implements JsonSerializer<PlayerSessionEvent> {
    public PlayerSessionEvent deserialize(JSONObject data) throws JSONException {
        return new PlayerSessionEvent(
                data.getString("type"),
                data.getString("emittedAt"),
                data.has("at") ? data.getInt("at") : null,
                data.has("from") ? data.getInt("from") : null,
                data.has("to") ? data.getInt("to") : null
        );
    }
}
