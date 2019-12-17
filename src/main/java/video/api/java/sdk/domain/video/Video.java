package video.api.java.sdk.domain.video;

import java.util.*;

public class Video {
    public static class SourceInfo {
        public final String type;
        public final String uri;

        public SourceInfo(String type, String uri) {
            this.type = type;
            this.uri  = uri;
        }
    }

    public       String              videoId;
    public       Calendar            publishedAt;
    public       Calendar            updatedAt;
    public       SourceInfo          sourceInfo;
    public final Map<String, String> assets    = new HashMap<>();
    public       String              playerId;
    public       String              title;
    public       String              description;
    public       boolean             isPublic  = true;
    public       boolean             panoramic = false;
    public       List<String>        tags      = new ArrayList<>();
    public       Map<String, String> metadata  = new HashMap<>();

    public Video() {
        this.sourceInfo = new SourceInfo(null, null);
    }

    public Video(SourceInfo sourceInfo) {
        this.sourceInfo = sourceInfo;
    }
}
