package video.api.java.sdk.infrastructure.unirest.request;

import kong.unirest.HttpRequestWithBody;

import java.io.File;

public class FileBodyBuilder implements BodyBuilder {
    private final File   file;
    private       String link;

    public FileBodyBuilder(File file, String link) {
        this.file = file;
        this.link = link;
    }

    @Override
    public HttpRequestWithBody build(HttpRequestWithBody request) {
        request.field("file", file);

        if (link != null) {
            request.field("link", link);
        }

        return request;
    }
}
