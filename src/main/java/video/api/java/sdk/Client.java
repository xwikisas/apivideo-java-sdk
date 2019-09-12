package video.api.java.sdk;

import video.api.java.sdk.infrastructure.unirest.analytic.event.SessionEventAnalyticsClient;
import video.api.java.sdk.infrastructure.unirest.analytic.live.LiveStreamAnalyticsClient;
import video.api.java.sdk.infrastructure.unirest.analytic.video.VideoAnalyticsClient;
import video.api.java.sdk.infrastructure.unirest.caption.CaptionClient;
import video.api.java.sdk.infrastructure.unirest.live.LiveStreamClient;
import video.api.java.sdk.infrastructure.unirest.player.PlayerClient;
import video.api.java.sdk.infrastructure.unirest.video.VideoClient;

public class Client {
    public final CaptionClient               captions;
    public final LiveStreamClient            liveStreams;
    public final LiveStreamAnalyticsClient   liveStreamAnalytics;
    public final PlayerClient                players;
    public final SessionEventAnalyticsClient sessionEventAnalytics;
    public final VideoClient                 videos;
    public final VideoAnalyticsClient        videoAnalytics;

    public Client(
            CaptionClient captions,
            LiveStreamClient liveStreams,
            LiveStreamAnalyticsClient liveStreamAnalytics,
            PlayerClient players,
            SessionEventAnalyticsClient sessionEventAnalytics,
            VideoClient videos,
            VideoAnalyticsClient videoAnalytics
    ) {
        this.captions              = captions;
        this.liveStreams           = liveStreams;
        this.liveStreamAnalytics   = liveStreamAnalytics;
        this.players               = players;
        this.sessionEventAnalytics = sessionEventAnalytics;
        this.videos                = videos;
        this.videoAnalytics        = videoAnalytics;
    }
}
