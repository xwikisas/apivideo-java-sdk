package video.api.java.sdk;


import video.api.java.sdk.infrastructure.unirest.AuthRequestExecutor;
import video.api.java.sdk.infrastructure.unirest.analytic.event.SessionEventClient;
import video.api.java.sdk.infrastructure.unirest.analytic.event.SessionEventJsonSerializer;
import video.api.java.sdk.infrastructure.unirest.analytic.LiveStreamSessionClient;
import video.api.java.sdk.infrastructure.unirest.analytic.PlayerSessionJsonSerializer;
import video.api.java.sdk.infrastructure.unirest.analytic.VideoSessionClient;
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
        AuthRequestExecutor  authRequestExecutor = new AuthRequestExecutor(baseUri, apiKey);
        AssetsJsonSerializer assetsSerializer    = new AssetsJsonSerializer();

        return new Client(
                new CaptionClient(new CaptionJsonSerializer(), authRequestExecutor, baseUri),
                new LiveStreamClient(new LiveStreamJsonSerializer(assetsSerializer), authRequestExecutor, baseUri),
                new LiveStreamSessionClient(new PlayerSessionJsonSerializer(), authRequestExecutor, baseUri),
                new PlayerClient(new PlayerJsonSerializer(), authRequestExecutor, baseUri),
                new SessionEventClient(new SessionEventJsonSerializer(), authRequestExecutor, baseUri),
                new VideoClient(new VideoJsonSerializer(assetsSerializer), authRequestExecutor, baseUri),
                new VideoSessionClient(new PlayerSessionJsonSerializer(), authRequestExecutor, baseUri)
        );
    }

}
