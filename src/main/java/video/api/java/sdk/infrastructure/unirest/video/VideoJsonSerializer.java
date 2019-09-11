package video.api.java.sdk.infrastructure.unirest.video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import video.api.java.sdk.domain.video.Video;
import video.api.java.sdk.domain.video.models.Assets;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;
import video.api.java.sdk.infrastructure.unirest.video.serializers.AssetsSerializer;
import video.api.java.sdk.infrastructure.unirest.video.serializers.SourceSerializer;
import video.api.java.sdk.infrastructure.unirest.video.serializers.StatusSerializer;

import java.util.*;

public class VideoJsonSerializer implements JsonSerializer<Video> {
    @Override
    public Video deserialize(JSONObject data) throws JSONException {
        Video            video            = new Video();
        SourceSerializer sourceSerializer = new SourceSerializer();
        AssetsSerializer assetsSerializer = new AssetsSerializer();
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


        List<Video> videos = new ArrayList<Video>();
        for (Object item : data) {
            videos.add(deserialize((JSONObject) item));
        }
        return videos;
    }

    @Override
    public JSONObject serialize(Video video) throws JSONException {

        SourceSerializer sourceSerializer = new SourceSerializer();
        AssetsSerializer assetsSerializer = new AssetsSerializer();
        StatusSerializer statusSerializer = new StatusSerializer();
        JSONObject       data             = new JSONObject();
        data.put("videoId", video.videoId);
        data.put("title", video.title);
        data.put("playerId", video.playerId);
        data.put("isPublic", video.isPublic);
        data.put("panoramic", video.panoramic);
        data.put("description", video.description);
        JSONArray tags = new JSONArray();

        for (Iterator<String> it = video.tags.iterator(); it.hasNext(); ) {
            tags.put(it.next());
        }
        data.put("tags", tags);

        Map<String, String> videoMetadata = video.metadata;
        JSONArray           metadataArray = new JSONArray();
        for (Map.Entry<String, String> e : videoMetadata.entrySet()) {
            HashMap hashMap = new HashMap<>();
            hashMap.put("key", e.getKey());
            hashMap.put("value", e.getValue());
            metadataArray.put(new JSONObject(hashMap));
        }
        data.put("metadata", metadataArray);

        data.put("source", sourceSerializer.serialize(video.source));
        data.put("assets", assetsSerializer.serialize(video.assets));
        data.put("publishedAt", video.publishedAt);

        if (video.playerId != null) {
            data.put("playerId", video.playerId);
        }

        data.put("status", statusSerializer.serialize(video.status));
        return data;

    }

    public JSONObject serializeProperties(Video video) throws JSONException {

        JSONObject data = new JSONObject();
        data.put("title", video.title);
        data.put("description", video.description);
        data.put("playerId", video.playerId);
        data.put("isPublic", video.isPublic);
        data.put("description", video.description);
        data.put("tags", video.tags.toArray());
        data.put("metadata", video.metadata);
        JSONArray tags = new JSONArray();

        for (Iterator<String> it = video.tags.iterator(); it.hasNext(); ) {
            tags.put(it.next());
        }
        data.put("tags", tags);
        if (video.metadata != null) {
            Map<String, String> videoMetadata = video.metadata;
            JSONArray           metadataArray = new JSONArray();
            for (Map.Entry<String, String> e : videoMetadata.entrySet()) {
                HashMap hashMap = new HashMap<>();
                hashMap.put("key", e.getKey());
                hashMap.put("value", e.getValue());
                metadataArray.put(new JSONObject(hashMap));
            }
            data.put("metadata", metadataArray);
        }

        return data;
    }

    public Video deserializeProperties(JSONObject data) throws JSONException {

        Video video = new Video();
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

        if (data.has("playerId")) {
            video.playerId = data.getString("playerId");
        }

        return video;
    }


}
