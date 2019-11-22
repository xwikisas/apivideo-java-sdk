package video.api.java.sdk.domain.analytic;

import java.util.Map;

public class Info {
    public final String              sessionId;
    public final String              loadedAt;
    public final String              endedAt;
    public final Map<String, String> metadata;

    public Info(String sessionId, String loadedAt, String endedAt, Map<String, String> metadata) {
        this.sessionId = sessionId;
        this.loadedAt  = loadedAt;
        this.endedAt   = endedAt;
        this.metadata  = metadata;
    }
}
