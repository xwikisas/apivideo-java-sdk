package video.api.java.sdk;


import video.api.java.sdk.infrastructure.unirest.AuthRequestExecutor;
import video.api.java.sdk.infrastructure.unirest.RequestFactory;
import video.api.java.sdk.infrastructure.unirest.analytics.*;
import video.api.java.sdk.infrastructure.unirest.asset.AssetsJsonSerializer;
import video.api.java.sdk.infrastructure.unirest.caption.CaptionClient;
import video.api.java.sdk.infrastructure.unirest.caption.CaptionJsonSerializer;
import video.api.java.sdk.infrastructure.unirest.live.LiveStreamClient;
import video.api.java.sdk.infrastructure.unirest.live.LiveStreamJsonSerializer;
import video.api.java.sdk.infrastructure.unirest.player.PlayerClient;
import video.api.java.sdk.infrastructure.unirest.player.PlayerJsonSerializer;
import video.api.java.sdk.infrastructure.unirest.video.VideoClient;
import video.api.java.sdk.infrastructure.unirest.video.VideoJsonSerializer;

public class ClientFactory {
    public Client create(String apiKey) {
        return create(apiKey, "https://ws.api.video");
    }

    public Client createSandbox(String apiKey) {
        return create(apiKey, "https://sandbox.api.video");
    }

    private Client create(String apiKey, String baseUri) {
        RequestFactory       requestFactory      = new RequestFactory(baseUri);
        AuthRequestExecutor  authRequestExecutor = new AuthRequestExecutor(requestFactory, apiKey);
        AssetsJsonSerializer assetsSerializer    = new AssetsJsonSerializer();

        return new Client(
                new CaptionClient(requestFactory, new CaptionJsonSerializer(), authRequestExecutor),
                new LiveStreamClient(requestFactory, new LiveStreamJsonSerializer(assetsSerializer), authRequestExecutor),
                new LiveStreamSessionClient(requestFactory, new PlayerSessionJsonSerializer(), authRequestExecutor),
                new PlayerClient(requestFactory, new PlayerJsonSerializer(), authRequestExecutor),
                new PlayerSessionEventClient(requestFactory, new SessionEventJsonSerializer(), authRequestExecutor),
                new VideoClient(requestFactory, new VideoJsonSerializer(assetsSerializer), authRequestExecutor),
                new VideoSessionClient(requestFactory, new PlayerSessionJsonSerializer(), authRequestExecutor)
        );
    }

}
