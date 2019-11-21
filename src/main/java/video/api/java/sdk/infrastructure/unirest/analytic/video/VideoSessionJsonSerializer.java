package video.api.java.sdk.infrastructure.unirest.analytic.video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import video.api.java.sdk.domain.analytic.analyticVideo.VideoSession;
import video.api.java.sdk.domain.analytic.models.AnalyticData;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VideoSessionJsonSerializer implements JsonSerializer<VideoSession> {

    @Override
    public VideoSession deserialize(JSONObject data) throws JSONException {
        System.out.println(data);

        VideoSession videoSession = new VideoSession();

        AnalyticData analyticData = new AnalyticData();

        // Build Analytic Session
        JSONObject session = data.getJSONObject("session");
        analyticData.session.sessionId = session.getString("sessionId");
        analyticData.session.endedAt   = session.getString("endedAt");
        analyticData.session.loadedAt  = session.getString("loadedAt");

        if (session.has("metadata")) {
            JSONArray           jsonArray = session.getJSONArray("metadata");

            Map<String, String> metadata = new HashMap<>();

            for (int j = 0; j < jsonArray.length(); j++) {
                JSONObject jsonObject = jsonArray.getJSONObject(j);
                String     key        = jsonObject.getString("key");
                try {
                    String value = jsonObject.getString("value");
                    metadata.put(key, value);

                } catch (org.json.JSONException e) {
                    metadata.put(key, null);

                }
            }
            analyticData.session.metadata = metadata;
        }
        // Build Analytic Location
        if (data.has("location")) {
            JSONObject location = data.getJSONObject("location");
            analyticData.location.country = location.getString("country");
            analyticData.location.city    = location.getString("city");
        }
        // Build Analytic Referer
        if (data.has("referrer")) {
            JSONObject referrer = data.getJSONObject("referrer");
            analyticData.referer.medium      = referrer.getString("medium");
            analyticData.referer.search_term = referrer.getString("searchTerm");
            analyticData.referer.source      = referrer.getString("source");
            analyticData.referer.url         = referrer.getString("url");
        }
        // Build Analytic Device
        if (data.has("device")) {
            JSONObject device = data.getJSONObject("device");
            analyticData.device.model  = device.getString("model");
            analyticData.device.type   = device.getString("type");
            analyticData.device.vendor = device.getString("vendor");
        }
        // Build Analytic Os
        if (data.has("os")) {
            JSONObject os = data.getJSONObject("os");
            analyticData.os.name      = os.getString("name");
            analyticData.os.shortname = os.getString("shortname");
            analyticData.os.version   = os.getString("version");
        }
        // Build Analytic Client
        if (data.has("client")) {
            JSONObject client = data.getJSONObject("client");
            analyticData.client.type    = client.getString("type");
            analyticData.client.name    = client.getString("name");
            analyticData.client.version = client.getString("version");
        }

        return videoSession;
    }

    @Override
    public JSONObject serialize(VideoSession videoSession) throws JSONException {
        JSONObject data  = new JSONObject();
        JSONObject video = new JSONObject();

        video.put("videoId", videoSession.videoId);
        video.put("title", videoSession.videoTitle);
        data.put("video", video);
        data.put("period", videoSession.period);
        JSONArray tags = new JSONArray();

        for (String tag : videoSession.tags) {
            tags.put(tag);
        }
        data.put("tags", tags);

        JSONArray metadataArray = mapToArray(videoSession.metadata);
        data.put("metadata", metadataArray);
        JSONArray dataArray = new JSONArray();
        //JSONArray dataArray = data.getJSONArray("data");
        //   analyticVideo.data = new AnalyticData[dataArray.length()];

        for (int i = 0; i < videoSession.data.length; i++) {
            AnalyticData analyticData = videoSession.data[i];
            JSONObject   data         = new JSONObject();


            // Build Analytic Session
            JSONObject session = new JSONObject();
            session.put("sessionId", analyticData.session.sessionId);
            session.put("endedAt", analyticData.session.endedAt);
            session.put("loadedAt", analyticData.session.loadedAt);
            JSONArray metadataArrays = mapToArray(analyticData.session.metadata);
            session.put("metadatas", metadataArrays);
            data.put("session", session);

            // Build Analytic Location
            JSONObject location = new JSONObject();
            location.put("country", analyticData.location.country);
            location.put("city", analyticData.location.city);
            data.put("location", location);

            // Build Analytic Referer
            JSONObject referer = new JSONObject();
            referer.put("medium", analyticData.referer.medium);
            referer.put("searchTerm", analyticData.referer.search_term);
            referer.put("source", analyticData.referer.source);
            referer.put("url", analyticData.referer.url);
            data.put("referer", referer);

            // Build Analytic Device
            JSONObject device = new JSONObject();
            device.put("model", analyticData.device.model);
            device.put("type", analyticData.device.type);
            device.put("vendor", analyticData.device.vendor);
            data.put("device", device);

            // Build Analytic Os
            JSONObject os = new JSONObject();
            os.put("name", analyticData.os.name);
            os.put("shortname", analyticData.os.shortname);
            os.put("version", analyticData.os.version);
            data.put("os", os);

            // Build Analytic Client
            JSONObject client = new JSONObject();
            client.put("type", analyticData.client.type);
            client.put("name", analyticData.client.name);
            client.put("version", analyticData.client.version);
            data.put("client", client);

            dataArray.put(data);

        }
        data.put("data", dataArray);


        return data;
    }

    private static JSONArray mapToArray(Map<String, String> map) {
        JSONArray array = new JSONArray();
        for (Map.Entry<String, String> e : map.entrySet()) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("key", e.getKey());
            hashMap.put("value", e.getValue());
            array.put(new JSONObject(hashMap));
        }
        return array;
    }

    @Override
    public List<VideoSession> deserialize(JSONArray data) throws JSONException {


        List<VideoSession> analyticsVideo = new ArrayList<>();
        for (Object item : data) {
            analyticsVideo.add(deserialize((JSONObject) item));
        }
        return analyticsVideo;
    }


}
