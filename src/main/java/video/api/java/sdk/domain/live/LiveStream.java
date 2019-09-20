package video.api.java.sdk.domain.live;

import video.api.java.sdk.domain.asset.Assets;

public class LiveStream {
    public final String name;

    public String  liveStreamId;
    public String  streamKey;
    public String  playerId;
    public boolean record       = false;
    public boolean broadcasting = false;
    public Assets  assets       = new Assets();

    public LiveStream(String name) {
        this.name = name;
    }
}
