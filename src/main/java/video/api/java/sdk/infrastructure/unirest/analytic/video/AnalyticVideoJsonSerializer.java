package video.api.java.sdk.infrastructure.unirest.analytic.video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import video.api.java.sdk.domain.analytic.analyticVideo.AnalyticVideo;
import video.api.java.sdk.domain.analytic.models.AnalyticData;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalyticVideoJsonSerializer implements JsonSerializer<AnalyticVideo> {

    @Override
    public AnalyticVideo deserialize(JSONObject data) throws JSONException {
        AnalyticVideo analyticVideo = new AnalyticVideo();
        analyticVideo.videoId = data.getJSONObject("video").getString("videoId");
        if (data.getJSONObject("video").has("title"))
            analyticVideo.videoTitle = data.getJSONObject("video").getString("title");
        if (data.getJSONObject("video").has("period"))
            analyticVideo.period = data.getString("period");
        JSONArray dataArray = data.getJSONArray("data");
        if (data.getJSONObject("video").has("tags")) {
            for (int i = 0; i < data.getJSONObject("video").getJSONArray("tags").length(); i++) {
                analyticVideo.tags.add(data.getJSONObject("video").getJSONArray("tags").getString(i));
            }
        }

        if (data.getJSONObject("video").has("metadata")) {
            JSONArray           jsonArray = data.getJSONObject("video").getJSONArray("metadata");
            Map<String, String> metadata  = new HashMap<>();
            for (int j = 0; j < jsonArray.length(); j++) {
                JSONObject jsonObject = jsonArray.getJSONObject(j);
                String     key        = jsonObject.getString("key");
                String     value      = jsonObject.getString("value");
                metadata.put(key, value);
            }
            analyticVideo.metadata = metadata;
        }
        analyticVideo.data = new AnalyticData[dataArray.length()];

        for (int i = 0; i < dataArray.length(); i++) {
            JSONObject   playerSession = dataArray.getJSONObject(i);
            AnalyticData analyticData  = new AnalyticData();

            // Build Analytic Session
            if (playerSession.has("session")) {
                analyticData.session.sessionId = playerSession.getJSONObject("session").getString("sessionId");
                analyticData.session.endedAt   = playerSession.getJSONObject("session").getString("endedAt");
                analyticData.session.loadedAt  = playerSession.getJSONObject("session").getString("loadedAt");
            }
            if (playerSession.getJSONObject("session").has("metadatas")) {
                JSONArray           jsonArray = playerSession.getJSONObject("session").getJSONArray("metadatas");
                Map<String, String> metadatas = new HashMap<>();
                for (int j = 0; j < jsonArray.length(); j++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(j);
                    String     key        = jsonObject.getString("key");
                    try {
                        String value = jsonObject.getString("value");
                        metadatas.put(key, value);

                    } catch (org.json.JSONException e) {
                        metadatas.put(key, null);

                    }
                }
                analyticData.session.metadatas = metadatas;
            }
            // Build Analytic Location
            if (playerSession.has("location")) {
                analyticData.location.country = playerSession.getJSONObject("location").getString("country");
                analyticData.location.city    = playerSession.getJSONObject("location").getString("city");
            }
            // Build Analytic Referer
            if (playerSession.has("referrer")) {
                analyticData.referer.medium      = playerSession.getJSONObject("referrer").getString("medium");
                analyticData.referer.search_term = playerSession.getJSONObject("referrer").getString("searchTerm");
                analyticData.referer.source      = playerSession.getJSONObject("referrer").getString("source");
                analyticData.referer.url         = playerSession.getJSONObject("referrer").getString("url");
            }
            // Build Analytic Device
            if (playerSession.has("device")) {
                analyticData.device.model  = playerSession.getJSONObject("device").getString("model");
                analyticData.device.type   = playerSession.getJSONObject("device").getString("type");
                analyticData.device.vendor = playerSession.getJSONObject("device").getString("vendor");
            }
            // Build Analytic Os
            if (playerSession.has("os")) {
                analyticData.os.name      = playerSession.getJSONObject("os").getString("name");
                analyticData.os.shortname = playerSession.getJSONObject("os").getString("shortname");
                analyticData.os.version   = playerSession.getJSONObject("os").getString("version");
            }
            // Build Analytic Client
            if (playerSession.has("client")) {
                analyticData.client.type    = playerSession.getJSONObject("client").getString("type");
                analyticData.client.name    = playerSession.getJSONObject("client").getString("name");
                analyticData.client.version = playerSession.getJSONObject("client").getString("version");
            }
            analyticVideo.data[i] = analyticData;

        }


        return analyticVideo;
    }

    @Override
    public JSONObject serialize(AnalyticVideo analyticVideo) throws JSONException {
        JSONObject data  = new JSONObject();
        JSONObject video = new JSONObject();

        video.put("videoId", analyticVideo.videoId);
        video.put("title", analyticVideo.videoTitle);
        data.put("video", video);
        data.put("period", analyticVideo.period);
        JSONArray tags = new JSONArray();

        for (String tag : analyticVideo.tags) {
            tags.put(tag);
        }
        data.put("tags", tags);

        JSONArray metadataArray = mapToArray(analyticVideo.metadata);
        data.put("metadata", metadataArray);
        JSONArray dataArray = new JSONArray();
        //JSONArray dataArray = data.getJSONArray("data");
        //   analyticVideo.data = new AnalyticData[dataArray.length()];

        for (int i = 0; i < analyticVideo.data.length; i++) {
            AnalyticData analyticData  = analyticVideo.data[i];
            JSONObject   playerSession = new JSONObject();


            // Build Analytic Session
            JSONObject session = new JSONObject();
            session.put("sessionId", analyticData.session.sessionId);
            session.put("endedAt", analyticData.session.endedAt);
            session.put("loadedAt", analyticData.session.loadedAt);
            JSONArray metadataArrays = mapToArray(analyticData.session.metadatas);
            session.put("metadatas", metadataArrays);
            playerSession.put("session", session);

            // Build Analytic Location
            JSONObject location = new JSONObject();
            location.put("country", analyticData.location.country);
            location.put("city", analyticData.location.city);
            playerSession.put("location", location);

            // Build Analytic Referer
            JSONObject referer = new JSONObject();
            referer.put("medium", analyticData.referer.medium);
            referer.put("searchTerm", analyticData.referer.search_term);
            referer.put("source", analyticData.referer.source);
            referer.put("url", analyticData.referer.url);
            playerSession.put("referer", referer);

            // Build Analytic Device
            JSONObject device = new JSONObject();
            device.put("model", analyticData.device.model);
            device.put("type", analyticData.device.type);
            device.put("vendor", analyticData.device.vendor);
            playerSession.put("device", device);

            // Build Analytic Os
            JSONObject os = new JSONObject();
            os.put("name", analyticData.os.name);
            os.put("shortname", analyticData.os.shortname);
            os.put("version", analyticData.os.version);
            playerSession.put("os", os);

            // Build Analytic Client
            JSONObject client = new JSONObject();
            client.put("type", analyticData.client.type);
            client.put("name", analyticData.client.name);
            client.put("version", analyticData.client.version);
            playerSession.put("client", client);

            dataArray.put(playerSession);

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
    public JSONObject serializeProperties(AnalyticVideo analyticVideo) throws JSONException {
        return null;
    }


    @Override
    public List<AnalyticVideo> deserialize(JSONArray data) throws JSONException {


        List<AnalyticVideo> analyticsVideo = new ArrayList<>();
        for (Object item : data) {
            analyticsVideo.add(deserialize((JSONObject) item));
        }
        return analyticsVideo;
    }


}
