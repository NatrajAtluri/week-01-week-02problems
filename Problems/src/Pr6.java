import java.util.concurrent.*;

class RateLimiter {

    class TokenBucket {
        int tokens;
        long lastRefill;

        TokenBucket(int maxTokens) {
            this.tokens = maxTokens;
            this.lastRefill = System.currentTimeMillis();
        }
    }

    private ConcurrentHashMap<String, TokenBucket> buckets = new ConcurrentHashMap<>();
    private final int MAX_TOKENS = 1000;
    private final int REFILL_RATE = 1000; // per hour

    public synchronized boolean allowRequest(String clientId) {
        buckets.putIfAbsent(clientId, new TokenBucket(MAX_TOKENS));
        TokenBucket bucket = buckets.get(clientId);

        long now = System.currentTimeMillis();
        long elapsed = now - bucket.lastRefill;

        int refill = (int) (elapsed / 3600000.0 * REFILL_RATE);
        if (refill > 0) {
            bucket.tokens = Math.min(MAX_TOKENS, bucket.tokens + refill);
            bucket.lastRefill = now;
        }

        if (bucket.tokens > 0) {
            bucket.tokens--;
            return true;
        }

        return false;
    }
}