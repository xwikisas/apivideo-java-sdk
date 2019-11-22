package video.api.java.sdk.infrastructure.unirest.video.serializers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import video.api.java.sdk.domain.video.models.Metadata;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;

import java.util.List;

public class MetadataSerializer implements JsonSerializer<Metadata> {


    public Metadata deserialize(JSONObject data) throws JSONException {
        try {
            Metadata metadata = new Metadata();
            metadata.width        = data.getInt("width");
            metadata.height       = data.getInt("height");
            metadata.bitrate      = data.getInt("bitrate");
            metadata.duration     = data.getInt("duration");
            metadata.framerate    = data.getInt("framerate");
            metadata.samplerate   = data.getInt("samplerate");
            metadata.video_codec  = data.getString("videoCodec");
            metadata.audio_codec  = data.getString("audioCodec");
            metadata.aspect_ratio = data.getString("aspectRatio");
            return metadata;
        } catch (JSONException e) {
            return new Metadata();
        }
    }

    @Override
    public List<Metadata> deserialize(JSONArray data) throws JSONException {
        return null;
    }

    public JSONObject serialize(Metadata object) throws JSONException {
        JSONObject data = new JSONObject();
        data.put("width", object.width);
        data.put("height", object.height);
        data.put("bitrate", object.bitrate);
        data.put("duration", object.duration);
        data.put("framerate", object.framerate);
        data.put("samplerate", object.samplerate);
        data.put("videoCodec", object.video_codec);
        data.put("audioCodec", object.audio_codec);
        data.put("aspectRatio", object.aspect_ratio);
        return data;
    }

}
