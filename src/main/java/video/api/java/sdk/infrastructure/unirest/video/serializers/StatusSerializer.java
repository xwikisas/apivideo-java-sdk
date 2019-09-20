package video.api.java.sdk.infrastructure.unirest.video.serializers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import video.api.java.sdk.domain.video.models.Status;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;

import java.util.List;

public class StatusSerializer implements JsonSerializer<Status> {

    public Status deserialize(JSONObject data) throws JSONException {
        IngestSerializer   ingestSerializer   = new IngestSerializer();
        EncodingSerializer encodingSerializer = new EncodingSerializer();
        Status             status             = new Status();

        if (data.has("ingest")) {
            status.ingest = ingestSerializer.deserialize(data.getJSONObject("ingest"));
        }
        if (data.has("encoding")) {
            status.encoding = encodingSerializer.deserialize(data.getJSONObject("encoding"));
        }
        return status;
    }

    @Override
    public List<Status> deserialize(JSONArray data) throws JSONException {
        return null;
    }

    public JSONObject serialize(Status status) throws JSONException {
        JSONObject         data               = new JSONObject();
        IngestSerializer   ingestSerializer   = new IngestSerializer();
        EncodingSerializer encodingSerializer = new EncodingSerializer();
        data.put("ingest", ingestSerializer.serialize(status.ingest));
        data.put("encoding", encodingSerializer.serialize(status.encoding));
        return data;
    }

}
