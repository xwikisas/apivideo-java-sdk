package video.api.java.sdk;


import video.api.java.sdk.infrastructure.unirest.AuthRequestExecutor;
import video.api.java.sdk.infrastructure.unirest.RequestBuilder;
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
        RequestBuilder       requestBuilder      = new RequestBuilder(baseUri);
        AuthRequestExecutor  authRequestExecutor = new AuthRequestExecutor(requestBuilder, apiKey);
        AssetsJsonSerializer assetsSerializer    = new AssetsJsonSerializer();

        return new Client(
                new CaptionClient(requestBuilder, new CaptionJsonSerializer(), authRequestExecutor),
                new LiveStreamClient(requestBuilder, new LiveStreamJsonSerializer(assetsSerializer), authRequestExecutor),
                new LiveStreamSessionClient(requestBuilder, new PlayerSessionJsonSerializer(), authRequestExecutor),
                new PlayerClient(requestBuilder, new PlayerJsonSerializer(), authRequestExecutor),
                new PlayerSessionEventClient(requestBuilder, new SessionEventJsonSerializer(), authRequestExecutor),
                new VideoClient(requestBuilder, new VideoJsonSerializer(assetsSerializer), authRequestExecutor),
                new VideoSessionClient(requestBuilder, new PlayerSessionJsonSerializer(), authRequestExecutor)
        );
    }

}
