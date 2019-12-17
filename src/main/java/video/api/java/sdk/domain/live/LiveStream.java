package video.api.java.sdk.domain.live;

import java.util.HashMap;
import java.util.Map;

public class LiveStream {
    public final String name;

    public       String              liveStreamId;
    public       String              streamKey;
    public       String              playerId;
    public       boolean             record;
    public       boolean             broadcasting;
    public final Map<String, String> assets = new HashMap<>();

    public LiveStream(String name) {
        this.name = name;
    }
}
