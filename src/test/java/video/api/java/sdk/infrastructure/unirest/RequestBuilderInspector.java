package video.api.java.sdk.infrastructure.unirest;

import kong.unirest.HttpRequest;
import kong.unirest.JsonNode;
import video.api.java.sdk.infrastructure.unirest.request.RequestBuilder;

public class RequestBuilderInspector implements RequestExecutor {
    private RequestBuilder requestBuilder;

    @Override
    public JsonNode executeJson(RequestBuilder requestBuilder) {
        this.requestBuilder = requestBuilder;
        return new JsonNode(null);
    }

    public HttpRequest buildRequest()
    {
        return requestBuilder.build();
    }
}
