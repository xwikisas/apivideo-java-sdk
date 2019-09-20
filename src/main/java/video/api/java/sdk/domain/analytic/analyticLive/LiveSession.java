package video.api.java.sdk.domain.analytic.analyticLive;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import video.api.java.sdk.domain.analytic.models.AnalyticData;

public class LiveSession {

    public String         liveStreamId;
    public String         liveName;
    public String         period;
    public AnalyticData[] data;

    public LiveSession() {
        this.liveStreamId = null;
        this.liveName     = null;
        this.period       = null;
        this.data         = new AnalyticData[]{};

    }


    public LiveSession(JSONObject data) {
        try {
            this.liveStreamId = data.getJSONObject("live").getString("live_stream_id");
            this.liveName     = data.getJSONObject("live").getString("name");
            this.period       = data.getString("period");
            JSONArray dataArray = data.getJSONArray("data");
            this.data = new AnalyticData[dataArray.length()];

            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject   playerSession = dataArray.getJSONObject(i);
                AnalyticData analyticData  = new AnalyticData();

                // Build Analytic Session
                analyticData.session.sessionId = playerSession.getJSONObject("session").getString("session_id");
                analyticData.session.endedAt   = playerSession.getJSONObject("session").getString("ended_at");
                analyticData.session.loadedAt  = playerSession.getJSONObject("session").getString("loaded_at");

                // Build Analytic Location
                analyticData.location.country = playerSession.getJSONObject("location").getString("country");
                analyticData.location.city    = playerSession.getJSONObject("location").getString("city");

                // Build Analytic Referer
                analyticData.referer.medium      = playerSession.getJSONObject("referrer").getString("medium");
                analyticData.referer.search_term = playerSession.getJSONObject("referrer").getString("search_term");
                analyticData.referer.source      = playerSession.getJSONObject("referrer").getString("source");
                analyticData.referer.url         = playerSession.getJSONObject("referrer").getString("url");

                // Build Analytic Device
                analyticData.device.model  = playerSession.getJSONObject("device").getString("model");
                analyticData.device.type   = playerSession.getJSONObject("device").getString("type");
                analyticData.device.vendor = playerSession.getJSONObject("device").getString("vendor");

                // Build Analytic Os
                analyticData.os.name      = playerSession.getJSONObject("os").getString("name");
                analyticData.os.shortname = playerSession.getJSONObject("os").getString("shortname");
                analyticData.os.version   = playerSession.getJSONObject("os").getString("version");

                // Build Analytic Client
                analyticData.client.type    = playerSession.getJSONObject("client").getString("type");
                analyticData.client.name    = playerSession.getJSONObject("client").getString("name");
                analyticData.client.version = playerSession.getJSONObject("client").getString("version");

                this.data[i] = analyticData;

            }
        } catch (JSONException | NullPointerException n) {
            this.liveStreamId = null;
            this.liveName     = null;
            this.period       = null;
            this.data         = new AnalyticData[]{};
        }
    }
}
