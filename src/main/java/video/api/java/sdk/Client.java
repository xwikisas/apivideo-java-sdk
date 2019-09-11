package video.api.java.sdk;

import video.api.java.sdk.infrastructure.unirest.analytic.event.AnalyticsEventClient;
import video.api.java.sdk.infrastructure.unirest.analytic.live.AnalyticsLiveClient;
import video.api.java.sdk.infrastructure.unirest.analytic.video.AnalyticsVideoClient;
import video.api.java.sdk.infrastructure.unirest.caption.CaptionClient;
import video.api.java.sdk.infrastructure.unirest.live.LiveClient;
import video.api.java.sdk.infrastructure.unirest.player.PlayerClient;
import video.api.java.sdk.infrastructure.unirest.video.VideoClient;

public class Client {
    public final VideoClient          videos;
    public final PlayerClient         players;
    public final LiveClient           lives;
    public final CaptionClient        captions;
    public final AnalyticsEventClient analyticsEvent;
    public final AnalyticsLiveClient  analyticsLive;
    public final AnalyticsVideoClient analyticsVideo;

    public Client(VideoClient videoClient, PlayerClient playerClient,
            LiveClient liveClient, CaptionClient captionClient,
            AnalyticsVideoClient analyticsVideoClient, AnalyticsLiveClient analyticsLiveClient,
            AnalyticsEventClient analyticsEventClient
    ) {
        this.videos         = videoClient;
        this.players        = playerClient;
        this.lives          = liveClient;
        this.captions       = captionClient;
        this.analyticsEvent = analyticsEventClient;
        this.analyticsLive  = analyticsLiveClient;
        this.analyticsVideo = analyticsVideoClient;

    }


}
