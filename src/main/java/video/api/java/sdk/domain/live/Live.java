package video.api.java.sdk.domain.live;

import video.api.java.sdk.domain.video.models.Assets;

public class Live {


    public String  liveStreamId;
    public String  streamKey;
    public String  name;
    public String  playerId;
    public boolean record       = false;
    public boolean broadcasting = false;
    public Assets  assets       = new Assets();


}
