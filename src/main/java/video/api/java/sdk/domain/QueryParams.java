package video.api.java.sdk.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryParams {

    public String sortBy;
    public String sortOrder;
    public String title;
    public String description;
    public String period;
    public String liveStreamId;
    public List<String> tags = new ArrayList<>();
    public Map<String, String> metadata = new HashMap<>();

    public Map<String, Object> toMap() {

        Map<String, Object> param = new HashMap<>();

        if (this.period != null) {
            param.put("period", this.period);
        }

        if (this.title != null) {
            param.put("title", this.title);
        }

        if (this.description != null) {
            param.put("description", this.description);
        }

        if (this.liveStreamId != null) {
            param.put("liveStreamId", this.liveStreamId);
        }

        if (this.sortBy != null) {
            param.put("sortBy", this.sortBy);
        }

        if ("asc".equals(this.sortOrder) || "desc".equals(this.sortOrder)) {
            param.put("sortOrder", this.sortOrder);
        }

        int i = 0;
        for (String tag : this.tags) {
            param.put("tags[" + i + "]", tag);
        }

        for (Map.Entry<String, String> e : this.metadata.entrySet()) {
            param.put("metadata[" + e.getKey() + "]", e.getValue());
        }

        return param;
    }

}
