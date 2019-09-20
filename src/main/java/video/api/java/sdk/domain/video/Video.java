package video.api.java.sdk.domain.video;

import video.api.java.sdk.domain.asset.Assets;
import video.api.java.sdk.domain.video.models.Source;
import video.api.java.sdk.domain.video.models.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Video {

    public String videoId;
    public String publishedAt;
    public Source source = new Source();
    public Assets assets = new Assets();

    public String              playerId;
    public String              title     = null;
    public String              description;
    public boolean             isPublic  = true;
    public boolean             panoramic = false;
    public List<String>        tags      = new ArrayList<>();
    public Map<String, String> metadata  = new HashMap<>();

    public Status status = new Status();

}
