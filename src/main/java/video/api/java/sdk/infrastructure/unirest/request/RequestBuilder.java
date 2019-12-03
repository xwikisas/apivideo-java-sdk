package video.api.java.sdk.infrastructure.unirest.request;

import kong.unirest.HttpMethod;
import kong.unirest.HttpRequest;
import kong.unirest.HttpRequestWithBody;
import kong.unirest.Unirest;
import org.json.JSONObject;
import video.api.java.sdk.domain.video.UploadProgressListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class RequestBuilder {
    private final String              baseUri;
    private final HttpMethod          method;
    private final String              relativePath;
    private       BodyBuilder         bodyBuilder;
    public final  Map<String, String> headers     = new HashMap<>();
    public final  Map<String, Object> queryParams = new HashMap<>();

    public RequestBuilder(String baseUri, HttpMethod method, String relativePath) {
        this.baseUri      = baseUri;
        this.method       = method;
        this.relativePath = relativePath;
    }

    public RequestBuilder withJson(JSONObject data) {
        bodyBuilder = new JsonBodyBuilder(data);

        return this;
    }

    public RequestBuilder withFile(File file) {
        bodyBuilder = new FileBodyBuilder(file, null, null);

        return this;
    }

    public RequestBuilder withFile(File file, String link) {
        bodyBuilder = new FileBodyBuilder(file, link, null);

        return this;
    }

    public RequestBuilder withFile(File file, String link, UploadProgressListener progressListener) {
        bodyBuilder = new FileBodyBuilder(file, link, progressListener);

        return this;
    }

    public RequestBuilder withFile(File file, UploadProgressListener progressListener) throws IOException {
        bodyBuilder = new FileBodyBuilder(file, null, progressListener);

        return this;
    }

    public RequestBuilder withChunk(String filename, InputStream inputStream, int chunkCount, int chunkNum, UploadProgressListener progressListener) {
        bodyBuilder = new InputStreamBodyBuilder(filename, inputStream, chunkCount, chunkNum, progressListener);

        return this;
    }

    public RequestBuilder withHeader(String name, String value) {
        this.headers.put(name, value);

        return this;
    }

    public RequestBuilder withQueryParams(Map<String, Object> queryParams) {
        this.queryParams.putAll(queryParams);

        return this;
    }

    public HttpRequest build() {
        HttpRequestWithBody request = Unirest
                .request(method.toString(), baseUri + relativePath);

        if (!headers.isEmpty()) {
            request.headers(headers);
        }

        if (!queryParams.isEmpty()) {
            request.queryString(queryParams);
        }

        if (bodyBuilder != null) {
            return bodyBuilder.build(request);
        }

        return request;
    }
}
