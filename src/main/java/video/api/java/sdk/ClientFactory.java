package video.api.java.sdk;


import video.api.java.sdk.infrastructure.unirest.AuthRequestExecutor;
import video.api.java.sdk.infrastructure.unirest.analytics.*;
import video.api.java.sdk.infrastructure.unirest.caption.CaptionClient;
import video.api.java.sdk.infrastructure.unirest.caption.CaptionDeserializer;
import video.api.java.sdk.infrastructure.unirest.caption.CaptionInputSerializer;
import video.api.java.sdk.infrastructure.unirest.live.LiveStreamClient;
import video.api.java.sdk.infrastructure.unirest.live.LiveStreamDeserializer;
import video.api.java.sdk.infrastructure.unirest.live.LiveStreamInputSerializer;
import video.api.java.sdk.infrastructure.unirest.player.PlayerClient;
import video.api.java.sdk.infrastructure.unirest.player.PlayerDeserializer;
import video.api.java.sdk.infrastructure.unirest.player.PlayerInputSerializer;
import video.api.java.sdk.infrastructure.unirest.request.RequestBuilderFactory;
import video.api.java.sdk.infrastructure.unirest.video.VideoClient;
import video.api.java.sdk.infrastructure.unirest.video.VideoDeserializer;
import video.api.java.sdk.infrastructure.unirest.video.VideoInputSerializer;

public class ClientFactory {
    public Client create(String apiKey) {
        return create(apiKey, "https://ws.api.video");
    }

    public Client createSandbox(String apiKey) {
        return create(apiKey, "https://sandbox.api.video");
    }

    private Client create(String apiKey, String baseUri) {
        RequestBuilderFactory requestBuilderFactory = new RequestBuilderFactory(baseUri);
        AuthRequestExecutor   authRequestExecutor   = new AuthRequestExecutor(requestBuilderFactory, apiKey);

        return new Client(
                new CaptionClient(requestBuilderFactory, new CaptionInputSerializer(), new CaptionDeserializer(), authRequestExecutor),
                new LiveStreamClient(requestBuilderFactory, new LiveStreamInputSerializer(), new LiveStreamDeserializer(), authRequestExecutor),
                new LiveStreamSessionClient(requestBuilderFactory, new PlayerSessionDeserializer(), authRequestExecutor),
                new PlayerClient(requestBuilderFactory, new PlayerInputSerializer(), new PlayerDeserializer(), authRequestExecutor),
                new PlayerSessionEventClient(requestBuilderFactory, new SessionEventJsonSerializer(), authRequestExecutor),
                new VideoClient(requestBuilderFactory, new VideoInputSerializer(), new VideoDeserializer(), authRequestExecutor),
                new VideoSessionClient(requestBuilderFactory, new PlayerSessionDeserializer(), authRequestExecutor)
        );
    }

}
