package video.api.java.sdk.domain.analytic.analyticEvent;

import org.json.JSONException;
import org.json.JSONObject;

public class SessionEvent {
    public String type;
    public String emittedAt;
    public int    at;
    public int    from;
    public int    to;

    public SessionEvent() {
        this.type      = null;
        this.emittedAt = null;
    }

    public SessionEvent(JSONObject data) {


        try {
            this.type      = data.getString("type");
            this.emittedAt = data.getString("emitted_at");
            if (data.has("at")) {
                this.at = data.getInt("at");
            }
            if (data.has("from") && data.has("to")) {
                this.from = data.getInt("from");
                this.to   = data.getInt("to");
            }
        } catch (JSONException | NullPointerException n) {
            n.getCause();
            this.type      = null;
            this.emittedAt = null;
        }
    }
}
