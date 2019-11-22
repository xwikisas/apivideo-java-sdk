package video.api.java.sdk.infrastructure.unirest.caption;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import video.api.java.sdk.domain.caption.Caption;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;

import java.util.ArrayList;
import java.util.List;

public class CaptionJsonSerializer implements JsonSerializer<Caption> {

    @Override
    public Caption deserialize(JSONObject data) throws JSONException {
        Caption caption = new Caption();

        if (data.has("uri"))
            caption.uri = data.getString("uri");
        if (data.has("src"))
            caption.src = data.getString("src");
        if (data.has("srclang"))
            caption.srclang = data.getString("srclang");
        if (data.has("default"))
            caption.defaultCaption = data.getBoolean("default");


        return caption;
    }

    @Override
    public List<Caption> deserialize(JSONArray data) throws JSONException {
        List<Caption> captions = new ArrayList<>();
        for (Object item : data) {
            captions.add(deserialize((JSONObject) item));
        }
        return captions;
    }

    public JSONObject serialize(Caption object) throws JSONException {
        JSONObject data = new JSONObject();

        data.put("default", object.defaultCaption);

        return data;
    }

}
