import java.util.*;

class MultiLevelCache {

    private LinkedHashMap<String, String> L1 =
            new LinkedHashMap<>(16, 0.75f, true) {
                protected boolean removeEldestEntry(Map.Entry<String, String> e) {
                    return size() > 10000;
                }
            };

    private Map<String, String> L2 = new HashMap<>();
    private Map<String, String> L3 = new HashMap<>();

    public String get(String key) {
        if (L1.containsKey(key)) return L1.get(key);

        if (L2.containsKey(key)) {
            String val = L2.get(key);
            L1.put(key, val);
            return val;
        }

        String val = L3.get(key);
        if (val != null) L2.put(key, val);

        return val;
    }

    public void put(String key, String value) {
        L1.put(key, value);
        L2.put(key, value);
        L3.put(key, value);
    }
}