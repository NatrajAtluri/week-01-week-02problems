import java.util.*;
import java.util.concurrent.*;

class Analytics {
    private ConcurrentHashMap<String, Integer> pageViews = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Set<String>> uniqueVisitors = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Integer> sources = new ConcurrentHashMap<>();

    public void processEvent(String url, String userId, String source) {
        pageViews.merge(url, 1, Integer::sum);

        uniqueVisitors.computeIfAbsent(url, k -> ConcurrentHashMap.newKeySet()).add(userId);

        sources.merge(source, 1, Integer::sum);
    }

    public List<Map.Entry<String, Integer>> topPages() {
        PriorityQueue<Map.Entry<String, Integer>> pq =
                new PriorityQueue<>(Map.Entry.comparingByValue());

        for (Map.Entry<String, Integer> entry : pageViews.entrySet()) {
            pq.offer(entry);
            if (pq.size() > 10) pq.poll();
        }

        return new ArrayList<>(pq);
    }
}