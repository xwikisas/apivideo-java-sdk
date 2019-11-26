package video.api.java.sdk.infrastructure.unirest;

import kong.unirest.GetRequest;
import kong.unirest.HttpMethod;
import kong.unirest.HttpRequestWithBody;
import kong.unirest.Unirest;

public class RequestBuilder {
    private String baseUri;

    public RequestBuilder(String baseUri) {
        this.baseUri = baseUri;
    }

    public GetRequest get(String relativePath) {
        return Unirest.get(baseUri + relativePath);
    }

    public HttpRequestWithBody post(String relativePath) {
        return Unirest.post(baseUri + relativePath);
    }

    public HttpRequestWithBody patch(String relativePath) {
        return Unirest.patch(baseUri + relativePath);
    }

    public HttpRequestWithBody delete(String relativePath) {
        return Unirest.delete(baseUri + relativePath);
    }
}
