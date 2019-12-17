package video.api.java.sdk.domain.video;

import video.api.java.sdk.domain.asset.Assets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Video {
    public static class Source {
        public final String type;
        public final String uri;

        public Source(String type, String uri) {
            this.type = type;
            this.uri  = uri;
        }
    }

    public String              videoId;
    public String              publishedAt;
    public String              updatedAt;
    public Source              source;
    public Assets              assets    = new Assets();
    public String              playerId;
    public String              title;
    public String              description;
    public boolean             isPublic  = true;
    public boolean             panoramic = false;
    public List<String>        tags      = new ArrayList<>();
    public Map<String, String> metadata  = new HashMap<>();

    public Video() {
        this.source = new Source(null, null);
    }

    public Video(Source source) {
        this.source = source;
    }
}
