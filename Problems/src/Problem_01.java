import java.util.*;

public class Problem_01 {
    private Map<String, Integer> userDatabase = new HashMap<>(); // Username -> UserId
    private Map<String, Integer> popularityTracker = new HashMap<>(); // Username -> Count

    public boolean checkAvailability(String username) {
        popularityTracker.put(username, popularityTracker.getOrDefault(username, 0) + 1);
        return !userDatabase.containsKey(username);
    }

    public List<String> suggestAlternatives(String username) {
        List<String> suggestions = new ArrayList<>();
        int suffix = 1;
        while (suggestions.size() < 3) {
            String candidate = username + suffix;
            if (!userDatabase.containsKey(candidate)) {
                suggestions.add(candidate);
            }
            suffix++;
        }
        return suggestions;
    }

    public String getMostAttempted() {
        return Collections.max(popularityTracker.entrySet(), Map.Entry.comparingByValue()).getKey();
    }
}