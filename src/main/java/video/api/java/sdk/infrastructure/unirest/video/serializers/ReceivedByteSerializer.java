package video.api.java.sdk.infrastructure.unirest.video.serializers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import video.api.java.sdk.domain.video.models.ReceivedByte;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;

import java.util.Iterator;
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

    public JSONObject serialize(ReceivedByte receivedByte) throws JSONException {

        JSONObject data = new JSONObject();
        data.put("to", receivedByte.to);
        data.put("from", receivedByte.from);
        data.put("total", receivedByte.total);
        return data;

    }

    @Override
    public JSONObject serializeProperties(ReceivedByte receivedByte) throws JSONException {
        return null;
    }


    JSONArray serialize(List<ReceivedByte> receivedBytes) throws JSONException {
        JSONArray tabs = new JSONArray();
        for (Iterator<ReceivedByte> it = receivedBytes.iterator(); it.hasNext(); ) {
            tabs.put(serialize(it.next()));
        }
        return tabs;
    }


}
