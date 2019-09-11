package video.api.java.sdk;


import video.api.java.sdk.infrastructure.unirest.AuthRequestExecutor;
import video.api.java.sdk.infrastructure.unirest.analytic.event.AnalyticEventJsonSerializer;
import video.api.java.sdk.infrastructure.unirest.analytic.event.AnalyticsEventClient;
import video.api.java.sdk.infrastructure.unirest.analytic.live.AnalyticLiveJsonSerializer;
import video.api.java.sdk.infrastructure.unirest.analytic.live.AnalyticsLiveClient;
import video.api.java.sdk.infrastructure.unirest.analytic.video.AnalyticVideoJsonSerializer;
import video.api.java.sdk.infrastructure.unirest.analytic.video.AnalyticsVideoClient;
import video.api.java.sdk.infrastructure.unirest.caption.CaptionClient;
import video.api.java.sdk.infrastructure.unirest.caption.CaptionJsonSerializer;
import video.api.java.sdk.infrastructure.unirest.live.LiveClient;
import video.api.java.sdk.infrastructure.unirest.live.LiveJsonSerializer;
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
        AuthRequestExecutor authRequestExecutor = new AuthRequestExecutor(baseUri, apiKey);

        VideoJsonSerializer videoSerializer = new VideoJsonSerializer();

        PlayerJsonSerializer playerSerializer = new PlayerJsonSerializer();

        LiveJsonSerializer liveSerializer = new LiveJsonSerializer();

        CaptionJsonSerializer captionJsonSerializer = new CaptionJsonSerializer();

        AnalyticVideoJsonSerializer analyticVideoJsonSerializer = new AnalyticVideoJsonSerializer();

        AnalyticLiveJsonSerializer analyticLiveJsonSerializer = new AnalyticLiveJsonSerializer();

        AnalyticEventJsonSerializer analyticEventJsonSerializer = new AnalyticEventJsonSerializer();

        return new Client(
                new VideoClient(videoSerializer, authRequestExecutor, baseUri),
                new PlayerClient(playerSerializer, authRequestExecutor, baseUri),
                new LiveClient(liveSerializer, authRequestExecutor, baseUri),
                new CaptionClient(captionJsonSerializer, authRequestExecutor, baseUri),
                new AnalyticsVideoClient(analyticVideoJsonSerializer, authRequestExecutor, baseUri),
                new AnalyticsLiveClient(analyticLiveJsonSerializer, authRequestExecutor, baseUri),
                new AnalyticsEventClient(analyticEventJsonSerializer, authRequestExecutor, baseUri)
        );
    }

}
