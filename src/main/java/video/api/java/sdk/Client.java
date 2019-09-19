package video.api.java.sdk;

import video.api.java.sdk.domain.analytic.analyticEvent.SessionEventAnalyticsClient;
import video.api.java.sdk.domain.analytic.analyticLive.LiveStreamAnalyticsClient;
import video.api.java.sdk.domain.analytic.analyticVideo.VideoAnalyticsClient;
import video.api.java.sdk.domain.caption.CaptionClient;
import video.api.java.sdk.domain.live.LiveStreamClient;
import video.api.java.sdk.domain.player.PlayerClient;
import video.api.java.sdk.domain.video.VideoClient;

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
