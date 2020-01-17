package video.api.java.sdk.infrastructure.unirest.chapter;

import kong.unirest.HttpRequest;
import org.junit.jupiter.api.Test;
import video.api.java.sdk.domain.chapter.ChapterClient;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.infrastructure.unirest.NullDeserializer;
import video.api.java.sdk.infrastructure.unirest.RequestBuilderInspector;
import video.api.java.sdk.infrastructure.unirest.request.RequestBuilderFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static kong.unirest.HttpMethod.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UnirestChapterClientTest {
    @Test
    public void get() throws ResponseException {
        RequestBuilderInspector inspector = new RequestBuilderInspector();
        ChapterClient client = createClient(inspector);

        client.get("fr");

        HttpRequest request = inspector.buildRequest();

        assertEquals(GET, request.getHttpMethod());
        assertEquals("/videos/viXXX/chapters/fr", request.getUrl());
    }

    @Test
    public void list() throws ResponseException {
        RequestBuilderInspector inspector = new RequestBuilderInspector();
        ChapterClient client = createClient(inspector);

        client.list();

        HttpRequest request = inspector.buildRequest();

        assertEquals(GET, request.getHttpMethod());
        assertTrue(request.getUrl().startsWith("/videos/viXXX/chapters"));
    }

    @Test
    public void upload() throws ResponseException, IOException {
        RequestBuilderInspector inspector = new RequestBuilderInspector();
        ChapterClient client = createClient(inspector);

        URL url = Thread.currentThread().getContextClassLoader().getResource("test.vtt");

        client.upload("fr", new File(url.getPath()));

        HttpRequest request = inspector.buildRequest();

        assertEquals(POST, request.getHttpMethod());
        assertEquals("/videos/viXXX/chapters/fr", request.getUrl());
    }

    @Test
    public void delete() throws ResponseException {
        RequestBuilderInspector inspector = new RequestBuilderInspector();
        ChapterClient client = createClient(inspector);

        client.delete("fr");

        HttpRequest request = inspector.buildRequest();

        assertEquals(DELETE, request.getHttpMethod());
        assertEquals("/videos/viXXX/chapters/fr", request.getUrl());
    }

    private UnirestChapterClient createClient(RequestBuilderInspector inspector) {
        return new UnirestChapterClient(
                new RequestBuilderFactory(""),
                new NullDeserializer<>(),
                inspector,
                "viXXX"
        );
    }
}
