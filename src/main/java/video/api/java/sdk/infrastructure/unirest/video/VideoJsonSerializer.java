package video.api.java.sdk.infrastructure.unirest.video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import video.api.java.sdk.domain.asset.Assets;
import video.api.java.sdk.domain.video.Video;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;
import video.api.java.sdk.infrastructure.unirest.video.serializers.SourceSerializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VideoJsonSerializer implements JsonSerializer<Video> {
    private final JsonSerializer<Assets> assetsSerializer;

    public VideoJsonSerializer(JsonSerializer<Assets> assetsSerializer) {
        this.assetsSerializer = assetsSerializer;
    }

    @Override
    public Video deserialize(JSONObject data) throws JSONException {
        Video            video            = new Video();
        SourceSerializer sourceSerializer = new SourceSerializer();

        video.videoId = data.getString("videoId");
        if (data.has("title"))
            video.title = data.getString("title");
        if (data.has("description"))
            video.description = data.getString("description");
        if (data.has("public"))
            video.isPublic = (boolean) data.get("public");
        if (data.has("panoramic"))
            video.panoramic = (boolean) data.get("panoramic");

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
        if (data.has("source"))
            video.source = sourceSerializer.deserialize(data.getJSONObject("source"));

        try {
            video.assets      = assetsSerializer.deserialize(data.getJSONObject("assets"));
            video.publishedAt = data.getString("publishedAt");
        } catch (org.json.JSONException e) {
            video.assets      = new Assets();
            video.publishedAt = "unkown";

        }

        if (data.has("playerId")) {
            video.playerId = data.getString("playerId");
        }

        return video;
    }

    @Override
    public List<Video> deserialize(JSONArray data) throws JSONException {


        List<Video> videos = new ArrayList<>();
        for (Object item : data) {
            videos.add(deserialize((JSONObject) item));
        }
        return videos;
    }

    @Override
    public JSONObject serialize(Video video) throws JSONException {
        JSONObject data = new JSONObject();
        data.put("description", video.description);
        data.put("isPublic", video.isPublic);
        if (video.metadata != null) {
            data.put("metadata", mapToArray(video.metadata));
        }
        data.put("playerId", video.playerId);
        data.put("tags", new JSONArray(video.tags));
        data.put("title", video.title);

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

}
