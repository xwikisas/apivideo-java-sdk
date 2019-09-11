package video.api.java.sdk.infrastructure.unirest.analytic.event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import video.api.java.sdk.domain.analytic.analyticEvent.AnalyticEvent;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;

import java.util.ArrayList;
import java.util.List;

public class AnalyticEventJsonSerializer implements JsonSerializer<AnalyticEvent> {

    public AnalyticEvent deserialize(JSONObject data) throws JSONException {
        AnalyticEvent analyticEvent = new AnalyticEvent();
        if (data.has("type"))
            analyticEvent.type = data.getString("type");
        if (data.has("emittedAt"))
            analyticEvent.emittedAt = data.getString("emittedAt");
        if (data.has("at")) {
            analyticEvent.at = data.getInt("at");
        }
        if (data.has("from") && data.has("to")) {
            analyticEvent.from = data.getInt("from");
            analyticEvent.to   = data.getInt("to");
        }
        return analyticEvent;
    }

    public JSONObject serialize(AnalyticEvent analyticEvent) throws JSONException {
        JSONObject data = new JSONObject();
        data.put("type", analyticEvent.type);
        data.put("emittedAt", analyticEvent.emittedAt);

        if (data.has("at")) {
            data.put("at", analyticEvent.at);
        }

        if (data.has("from") && data.has("to")) {

            data.put("from", analyticEvent.from);
            data.put("to", analyticEvent.to);
        }
        return data;
    }

    @Override
    public JSONObject serializeProperties(AnalyticEvent analyticEvent) throws JSONException {
        return null;
    }


    public List<AnalyticEvent> deserialize(JSONArray data) throws JSONException {


        List<AnalyticEvent> analyticEvent = new ArrayList<AnalyticEvent>();
        for (Object item : data) {
            analyticEvent.add(deserialize((JSONObject) item));
        }
        return analyticEvent;
    }

}
