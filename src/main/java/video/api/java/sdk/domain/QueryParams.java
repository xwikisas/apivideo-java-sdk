package video.api.java.sdk.domain;

import org.apache.http.client.utils.URIBuilder;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.*;

public class QueryParams {

    public int                 currentPage = 1;
    public int                 pageSize    = 25;
    public String              title;
    public String              description;
    public String              period;
    public String              sortBy;
    public String              sortOrder;
    public List<String>        tags        = new ArrayList<>();
    public Map<String, String> metadata    = new HashMap<>();

    public JSONObject createJSONObject() {
        JSONObject param = new JSONObject();
        param.put("currentPage", this.currentPage);
        param.put("pageSize", this.pageSize);
        if (this.period != null) {
            param.put("period", this.period);
        }
        if (this.title != null) {
            param.put("title", this.title);
        }
        if (this.description != null) {
            param.put("description", this.description);
        }
        if (this.sortBy != null && (this.sortBy.equals("title") || this.sortBy.equals("emitted_at"))) {
            param.put("sortBy", this.sortBy);
        }
        if (this.sortOrder != null && (this.sortBy.equals("asc") || this.sortBy.equals("desc"))) {
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


    public String queryBuilder(JSONObject parameters, String url) throws IllegalArgumentException {
        try {


            URIBuilder urlBuilder = new URIBuilder(url);
            for (Iterator iterator = parameters.keys(); iterator.hasNext(); ) {
                Object key = iterator.next();
                urlBuilder.addParameter(String.valueOf(key), parameters.get(String.valueOf(key)).toString());
            }
            return urlBuilder.build().toString();
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(" QueryBuilder --> URISyntaxException. \n Message : " + e.getMessage());
        }
    }


}
