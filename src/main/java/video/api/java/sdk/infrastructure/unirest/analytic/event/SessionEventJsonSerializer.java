package video.api.java.sdk.infrastructure.unirest.analytic.event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import video.api.java.sdk.domain.analytics.SessionEvent;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;

import java.util.ArrayList;
import java.util.List;

public class SessionEventJsonSerializer implements JsonSerializer<SessionEvent> {

    public SessionEvent deserialize(JSONObject data) throws JSONException {
        SessionEvent sessionEvent = new SessionEvent();
        if (data.has("type"))
            sessionEvent.type = data.getString("type");
        if (data.has("emittedAt"))
            sessionEvent.emittedAt = data.getString("emittedAt");
        if (data.has("at")) {
            sessionEvent.at = data.getInt("at");
        }
        if (data.has("from") && data.has("to")) {
            sessionEvent.from = data.getInt("from");
            sessionEvent.to   = data.getInt("to");
        }
        return sessionEvent;
    }

    public JSONObject serialize(SessionEvent sessionEvent) throws JSONException {
        JSONObject data = new JSONObject();
        data.put("type", sessionEvent.type);
        data.put("emittedAt", sessionEvent.emittedAt);

        if (data.has("at")) {
            data.put("at", sessionEvent.at);
        }

        if (data.has("from") && data.has("to")) {

            data.put("from", sessionEvent.from);
            data.put("to", sessionEvent.to);
        }
        return data;
    }

    public List<SessionEvent> deserialize(JSONArray data) throws JSONException {


        List<SessionEvent> sessionEvent = new ArrayList<>();
        for (Object item : data) {
            sessionEvent.add(deserialize((JSONObject) item));
        }
        return sessionEvent;
    }

}
