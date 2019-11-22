package video.api.java.sdk;

import video.api.java.sdk.domain.analytic.SessionEventClient;
import video.api.java.sdk.domain.analytic.LiveStreamSessionClient;
import video.api.java.sdk.domain.analytic.VideoSessionClient;
import video.api.java.sdk.domain.caption.CaptionClient;
import video.api.java.sdk.domain.live.LiveStreamClient;
import video.api.java.sdk.domain.player.PlayerClient;
import video.api.java.sdk.domain.video.VideoClient;

public class Client {
    public final CaptionClient           captions;
    public final LiveStreamClient        liveStreams;
    public final LiveStreamSessionClient liveStreamAnalytics;
    public final PlayerClient            players;
    public final SessionEventClient      sessionEventAnalytics;
    public final VideoClient             videos;
    public final VideoSessionClient      videoAnalytics;

    public Client(
            CaptionClient captions,
            LiveStreamClient liveStreams,
            LiveStreamSessionClient liveStreamAnalytics,
            PlayerClient players,
            SessionEventClient sessionEventAnalytics,
            VideoClient videos,
            VideoSessionClient videoAnalytics
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
