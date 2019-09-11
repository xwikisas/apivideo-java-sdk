package video.api.java.sdk.domain.analytic.models;

import org.json.JSONArray;

public class AnalyticData {
    public final AnalyticSession  session;
    public final AnalyticLocation location;
    public final AnalyticReferer  referer;
    public final AnalyticDevice   device;
    public final AnalyticOs       os;
    public final AnalyticClient   client;
    public final JSONArray        events;

    public AnalyticData() {
        this.session  = new AnalyticSession();
        this.location = new AnalyticLocation();
        this.referer  = new AnalyticReferer();
        this.device   = new AnalyticDevice();
        this.os       = new AnalyticOs();
        this.client   = new AnalyticClient();
        this.events   = new JSONArray();
    }
}
