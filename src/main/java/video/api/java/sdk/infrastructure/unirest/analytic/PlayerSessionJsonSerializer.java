package video.api.java.sdk.infrastructure.unirest.analytic;

import org.json.JSONException;
import org.json.JSONObject;
import video.api.java.sdk.domain.analytic.*;
import video.api.java.sdk.domain.analytic.PlayerSession;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;

public class PlayerSessionJsonSerializer implements JsonSerializer<PlayerSession> {

    @Override
    public PlayerSession deserialize(JSONObject data) throws JSONException {
        return new PlayerSession(
                deserializeInfo(data.getJSONObject("session")),
                deserializeLocation(data.getJSONObject("location")),
                deserializeReferrer(data.getJSONObject("referrer")),
                deserializeDevice(data.getJSONObject("device")),
                deserializeOperatingSystem(data.getJSONObject("os")),
                deserializeClient(data.getJSONObject("client"))
        );
    }

    private Info deserializeInfo(JSONObject object) {
        return new Info(
                object.getString("sessionId"),
                object.getString("endedAt"),
                object.getString("loadedAt"),
                object.has("metadata") ? convertKeyValueJsonArrayToMap(object.getJSONArray("metadata")) : null
        );
    }

    private Location deserializeLocation(JSONObject object) {
        return new Location(
                object.getString("country"),
                object.getString("city")
        );
    }

    private Referrer deserializeReferrer(JSONObject object) {
        return new Referrer(
                object.getString("medium"),
                object.getString("searchTerm"),
                object.getString("source"),
                object.getString("url")
        );
    }

    private Device deserializeDevice(JSONObject object) {
        return new Device(
                object.getString("model"),
                object.getString("type"),
                object.getString("vendor")
        );
    }

    private OperatingSystem deserializeOperatingSystem(JSONObject object) {
        return new OperatingSystem(
                object.getString("name"),
                object.getString("shortname"),
                object.getString("version")
        );
    }

    private Client deserializeClient(JSONObject object) {
        return new Client(
                object.getString("type"),
                object.getString("name"),
                object.getString("version")
        );
    }

    @Override
    public JSONObject serialize(PlayerSession object) throws JSONException {
        throw new UnsupportedOperationException("Not implemented");
    }
}
