package video.api.java.sdk.domain.analytics;

public class PlayerSessionEvent {
    public final String  type;
    public final String  emittedAt;
    public final Integer at;
    public final Integer from;
    public final Integer to;

    public PlayerSessionEvent(String type, String emittedAt, Integer at, Integer from, Integer to) {
        this.type      = type;
        this.emittedAt = emittedAt;
        this.at        = at;
        this.from      = from;
        this.to        = to;
    }
}
