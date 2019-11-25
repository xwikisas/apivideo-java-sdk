package video.api.java.sdk.infrastructure.unirest;

import kong.unirest.HttpMethod;
import kong.unirest.HttpRequestWithBody;
import kong.unirest.Unirest;

public class RequestFactory {
    private String baseUri;

    public RequestFactory(String baseUri) {
        this.baseUri = baseUri;
    }

    public HttpRequestWithBody create(HttpMethod method, String relativePath) {
        return Unirest.request(method.toString(), baseUri + relativePath);
    }
}
