package video.api.java.sdk.domain;

import org.apache.http.client.utils.URIBuilder;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.*;

@SuppressWarnings("WeakerAccess")
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

    public QueryParams() {

    }


    public QueryParams(QueryParams queryParams) {
        this.currentPage = queryParams.currentPage;
        this.pageSize    = queryParams.pageSize;
        this.period      = queryParams.period;
        this.title       = queryParams.title;
        this.description = queryParams.description;
        this.sortBy      = queryParams.sortBy;
        this.sortOrder   = queryParams.sortOrder;
        this.tags        = queryParams.tags;
        this.metadata    = queryParams.metadata;

    }


    public String create(String url) throws IllegalArgumentException {

        return queryBuilder(this.createJSONObject(), url);
    }

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
        for (Iterator<String> it = this.tags.iterator(); it.hasNext(); ) {
            param.put("tags[" + i + "]", it.next());
        }

        for (Map.Entry<String, String> e : this.metadata.entrySet()) {
            HashMap hashMap = new HashMap<>();
            hashMap.put("key", e.getKey());
            hashMap.put("value", e.getValue());
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
