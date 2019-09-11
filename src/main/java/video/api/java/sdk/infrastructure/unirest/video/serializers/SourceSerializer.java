package video.api.java.sdk.infrastructure.unirest.video.serializers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import video.api.java.sdk.domain.video.models.Source;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;

import java.util.List;

public class SourceSerializer implements JsonSerializer<Source> {


    @Override
    public Source deserialize(JSONObject data) {
        Source source = new Source();
        source.type = data.getString("type");
        source.uri  = data.getString("uri");

        return source;
    }

    @Override
    public List<Source> deserialize(JSONArray data) throws JSONException {
        return null;
    }


    @Override
    public JSONObject serialize(Source source) throws JSONException {
        JSONObject data = new JSONObject();
        data.put("type", source.type);
        data.put("uri", source.uri);
        return data;
    }

    @Override
    public JSONObject serializeProperties(Source source) throws JSONException {
        return null;
    }


}
