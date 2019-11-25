package video.api.java.sdk.infrastructure.unirest.video;

import org.json.JSONException;
import org.json.JSONObject;
import video.api.java.sdk.domain.video.Status;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;

public class StatusSerializer implements JsonSerializer<Status> {
    private ReceivedBytesSerializer receivedBytesSerializer = new ReceivedBytesSerializer();

    public Status deserialize(JSONObject data) throws JSONException {
        return new Status(
                data.has("ingest") ? deserializeIngest(data.getJSONObject("ingest")) : null,
                data.has("encoding") ? deserializeEncoding(data.getJSONObject("encoding")) : null
        );
    }

    private Status.Ingest deserializeIngest(JSONObject data) {
        return new Status.Ingest(
                data.getString("status"),
                data.getInt("filesize"),
                receivedBytesSerializer.deserialize(data.getJSONArray("receivedBytes"))
        );
    }

    private Status.Encoding deserializeEncoding(JSONObject data) {
        return new Status.Encoding(
                data.getBoolean("playable"),
                data.getJSONArray("qualities").toList(),
                data.getJSONObject("metadata").toMap()
        );
    }
}
