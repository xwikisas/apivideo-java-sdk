# API.video java-sdk

This SDK provides a Java client for [api.video](https://api.video/) service.
This is an early version, feel free to report any issue.

# Install

## With Gradle

1. Download the [latest release](https://github.com/apivideo/java-sdk/releases).
2. Add the following dependencies in your `build.gradle` file:

```gradle
// build.gradle
dependencies {
    implementation 'com.konghq:unirest-java:2.3.17'
    implementation files('libs/java-sdk-0.1.0.jar')
}
``` 

# Quick start

```java
import video.api.java.sdk.Client;
import video.api.java.sdk.ClientFactory;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.domain.video.Video;

public class Main {
    public static void main(String[] args) throws ResponseException {
        Client client = new ClientFactory().createSandbox("YourApiKey");
    
        // Upload a new video
        Video video = client.videos.upload("/path/to/file.mp4");

        // Get its embed code 
        String embedCode = video.assets.iframe; // <iframe src="..."></iframe>

        // Iterate over videos (paging is transparent)
        for (PageIterator<Video> it = client.videos.list(); it.hasNext(); ) {
            Video v = it.next();
        
            String videoTitle = v.title;
        }
    }
}
```

# More examples
```java
// Upload a video with properties
Video video = new Video();
video.title = "My title";
video.tags.add("my tag");
video = client.videos.upload("/path/to/file.mp4", video);

// Iterate over videos (paging is transparent)
for (PageIterator<Video> it = client.videos.list(); it.hasNext(); ) {
    Video video = it.next();

    String videoTitle = video.title;
}
```

# API coverage
Most of _api.video_ features are implemented and autocomplete friendly within your favorite IDE:

```java
client.videos; // https://docs.api.video/5.1/videos
client.players; // https://docs.api.video/5.1/players
client.captions; // https://docs.api.video/5.1/captions
client.liveStreams; // https://docs.api.video/5.1/live

// https://docs.api.video/5.1/analytics
client.liveStreamAnalytics; 
client.sessionEventAnalytics; 
client.videoAnalytics;
```
