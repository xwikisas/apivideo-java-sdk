package video.api.java.sdk.infrastructure.pagination;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import video.api.java.sdk.domain.pagination.Page;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;

import java.util.List;

public class PageSerializer<T> implements JsonSerializer<Page<T>> {
    private final JsonSerializer<T> inner;

    public PageSerializer(JsonSerializer<T> inner) {
        this.inner = inner;
    }

    @Override
    public JSONObject serialize(Page<T> tPage) throws JSONException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Page<T> deserialize(JSONObject body) throws JSONException {
        JSONObject pagination = body.getJSONObject("pagination");
        
        return new Page<>(
                inner.deserialize(body.getJSONArray("data")),
                pagination.getInt("pagesTotal"),
                pagination.getInt("currentPage")
        );
    }

    @Override
    public List<Page<T>> deserialize(JSONArray data) throws JSONException {
        throw new UnsupportedOperationException();
    }
}
