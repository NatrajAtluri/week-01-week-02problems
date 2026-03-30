import java.util.*;

class DNSCache {

    class Entry {
        String ip;
        long expiry;

        Entry(String ip, long ttl) {
            this.ip = ip;
            this.expiry = System.currentTimeMillis() + ttl;
        }
    }

    private final int CAPACITY = 1000;

    private LinkedHashMap<String, Entry> cache =
            new LinkedHashMap<>(16, 0.75f, true) {
                protected boolean removeEldestEntry(Map.Entry<String, Entry> eldest) {
                    return size() > CAPACITY;
                }
            };

    private int hits = 0, misses = 0;

    public synchronized String resolve(String domain) {
        Entry e = cache.get(domain);

        if (e != null && e.expiry > System.currentTimeMillis()) {
            hits++;
            return e.ip;
        }

        misses++;
        String ip = queryDNS(domain);
        cache.put(domain, new Entry(ip, 300000)); // TTL 5 min
        return ip;
    }

    private String queryDNS(String domain) {
        return "1.1.1.1"; // mock
    }

    public String stats() {
        int total = hits + misses;
        return "Hit Rate: " + (hits * 100.0 / total) + "%";
    }
}