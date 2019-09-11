package video.api.java.sdk.domain.analytic.models;

import java.util.HashMap;
import java.util.Map;

public class AnalyticSession {

    public String              sessionId;
    public String              loadedAt;
    public String              endedAt;
    public Map<String, String> metadatas = new HashMap<>();


    AnalyticSession() {

        this.sessionId = null;
        this.loadedAt  = null;
        this.endedAt   = null;
    }
}
