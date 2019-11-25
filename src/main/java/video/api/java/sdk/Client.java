package video.api.java.sdk;

import video.api.java.sdk.domain.analytics.LiveStreamSessionClient;
import video.api.java.sdk.domain.analytics.PlayerSessionEventClient;
import video.api.java.sdk.domain.analytics.VideoSessionClient;
import video.api.java.sdk.domain.caption.CaptionClient;
import video.api.java.sdk.domain.live.LiveStreamClient;
import video.api.java.sdk.domain.player.PlayerClient;
import video.api.java.sdk.domain.video.VideoClient;

public class Client {
    public final CaptionClient            captions;
    public final LiveStreamClient         liveStreams;
    public final LiveStreamSessionClient  liveStreamAnalytics;
    public final PlayerClient             players;
    public final PlayerSessionEventClient playerSessionEvents;
    public final VideoClient              videos;
    public final VideoSessionClient       videoAnalytics;

    public Client(
            CaptionClient captions,
            LiveStreamClient liveStreams,
            LiveStreamSessionClient liveStreamAnalytics,
            PlayerClient players,
            PlayerSessionEventClient playerSessionEvents,
            VideoClient videos,
            VideoSessionClient videoAnalytics
    ) {
        this.captions            = captions;
        this.liveStreams         = liveStreams;
        this.liveStreamAnalytics = liveStreamAnalytics;
        this.players             = players;
        this.playerSessionEvents = playerSessionEvents;
        this.videos              = videos;
        this.videoAnalytics      = videoAnalytics;
    }
}
