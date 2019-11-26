package video.api.java.sdk.domain.account;

public class Account {
    public static class Quota {
        public final int quotaUsed;
        public final int quotaRemaining;
        public final int quotaTotal;

        public Quota(int quotaUsed, int quotaRemaining, int quotaTotal) {
            this.quotaUsed      = quotaUsed;
            this.quotaRemaining = quotaRemaining;
            this.quotaTotal     = quotaTotal;
        }
    }

    public final Quota quota;

    public Account(Quota quota) {
        this.quota = quota;
    }
}
