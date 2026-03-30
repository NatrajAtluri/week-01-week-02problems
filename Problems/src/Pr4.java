import java.util.*;

class PlagiarismDetector {
    private Map<String, Set<String>> index = new HashMap<>();

    public List<String> generateNGrams(String text, int n) {
        String[] words = text.split("\\s+");
        List<String> grams = new ArrayList<>();

        for (int i = 0; i <= words.length - n; i++) {
            grams.add(String.join(" ", Arrays.copyOfRange(words, i, i + n)));
        }
        return grams;
    }

    public void addDocument(String docId, String text) {
        for (String gram : generateNGrams(text, 5)) {
            index.computeIfAbsent(gram, k -> new HashSet<>()).add(docId);
        }
    }

    public Map<String, Integer> analyze(String text) {
        Map<String, Integer> matches = new HashMap<>();

        for (String gram : generateNGrams(text, 5)) {
            if (index.containsKey(gram)) {
                for (String doc : index.get(gram)) {
                    matches.merge(doc, 1, Integer::sum);
                }
            }
        }
        return matches;
    }
}