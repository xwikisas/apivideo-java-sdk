package video.api.java.sdk.infrastructure.unirest.video.serializers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import video.api.java.sdk.domain.video.models.ReceivedByte;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;

import java.util.List;

public class ReceivedByteSerializer implements JsonSerializer<ReceivedByte> {

    public ReceivedByte deserialize(JSONObject data) throws JSONException {

        ReceivedByte receivedByte = new ReceivedByte();
        receivedByte.to    = data.getInt("to");
        receivedByte.from  = data.getInt("from");
        receivedByte.total = data.getInt("total");
        return receivedByte;
    }

    @Override
    public List<ReceivedByte> deserialize(JSONArray data) throws JSONException {
        return null;
    }

    public JSONObject serialize(ReceivedByte object) throws JSONException {

        JSONObject data = new JSONObject();
        data.put("to", object.to);
        data.put("from", object.from);
        data.put("total", object.total);
        return data;

    }

    JSONArray serialize(List<ReceivedByte> receivedBytes) throws JSONException {
        JSONArray tabs = new JSONArray();
        for (ReceivedByte receivedByte : receivedBytes) {
            tabs.put(serialize(receivedByte));
        }
        return tabs;
    }


}
