package video.api.java.sdk.infrastructure.unirest.video;

import org.json.JSONException;
import org.json.JSONObject;
import video.api.java.sdk.domain.video.ReceivedBytes;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;

public class ReceivedBytesSerializer implements JsonSerializer<ReceivedBytes> {

    public ReceivedBytes deserialize(JSONObject data) throws JSONException {
        return new ReceivedBytes(
                data.getInt("to"),
                data.getInt("from"),
                data.getInt("total")
        );
    }

}
