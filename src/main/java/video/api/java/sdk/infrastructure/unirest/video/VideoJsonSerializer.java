package video.api.java.sdk.infrastructure.unirest.video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import video.api.java.sdk.domain.asset.Assets;
import video.api.java.sdk.domain.video.Video;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;

import javax.xml.bind.DatatypeConverter;
import java.util.HashMap;
import java.util.Map;

public class VideoJsonSerializer implements JsonSerializer<Video> {
    private final JsonSerializer<Assets> assetsSerializer;

    public VideoJsonSerializer(JsonSerializer<Assets> assetsSerializer) {
        this.assetsSerializer = assetsSerializer;
    }

    @Override
    public Video deserialize(JSONObject data) throws JSONException {
        Video video = new Video();

        video.videoId = data.getString("videoId");
        if (data.has("title")) {
            video.title = data.getString("title");
        }
        if (data.has("description")) {
            video.description = data.getString("description");
        }
        if (data.has("public")) {
            video.isPublic = (boolean) data.get("public");
        }
        if (data.has("panoramic")) {
            video.panoramic = (boolean) data.get("panoramic");
        }

        if (data.has("tags")) {
            for (int i = 0; i < data.getJSONArray("tags").length(); i++) {
                video.tags.add(data.getJSONArray("tags").getString(i));
            }
        }

        if (data.has("metadata")) {
            JSONArray           jsonArray = data.getJSONArray("metadata");
            Map<String, String> metadata  = new HashMap<>();
            for (int j = 0; j < jsonArray.length(); j++) {
                JSONObject jsonObject = jsonArray.getJSONObject(j);
                String     key        = jsonObject.getString("key");
                String     value      = jsonObject.getString("value");
                metadata.put(key, value);
            }
            video.metadata = metadata;
        }
        if (data.has("source")) {
            video.source = deserializeSource(data.getJSONObject("source"));
        }

        video.publishedAt = DatatypeConverter.parseDateTime(data.getString("publishedAt"));
        video.updatedAt   = DatatypeConverter.parseDateTime(data.getString("updatedAt"));

        try {
            video.assets = assetsSerializer.deserialize(data.getJSONObject("assets"));
        } catch (org.json.JSONException e) {
            video.assets = null;
        }

        if (data.has("playerId")) {
            video.playerId = data.getString("playerId");
        }

        return video;
    }

    @Override
    public JSONObject serialize(Video object) throws JSONException {
        JSONObject data = new JSONObject();
        data.put("description", object.description);
        data.put("isPublic", object.isPublic);
        if (object.metadata != null) {
            data.put("metadata", mapToArray(object.metadata));
        }
        data.put("playerId", object.playerId);
        data.put("tags", new JSONArray(object.tags));
        data.put("title", object.title);

        return data;
    }

    private JSONArray mapToArray(Map<String, String> videoMetadata) {
        JSONArray metadataArray = new JSONArray();
        for (Map.Entry<String, String> e : videoMetadata.entrySet()) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("key", e.getKey());
            hashMap.put("value", e.getValue());
            metadataArray.put(new JSONObject(hashMap));
        }
        return metadataArray;
    }

    private Video.Source deserializeSource(JSONObject data) {
        return new Video.Source(
                data.getString("type"),
                data.getString("uri")
        );
    }
}
