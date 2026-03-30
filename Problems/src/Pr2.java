
    import java.util.*;
import java.util.concurrent.*;

    class InventoryManager {
        private ConcurrentHashMap<String, Integer> stock = new ConcurrentHashMap<>();
        private ConcurrentHashMap<String, Queue<Integer>> waitingList = new ConcurrentHashMap<>();

        public void addProduct(String productId, int count) {
            stock.put(productId, count);
            waitingList.put(productId, new ConcurrentLinkedQueue<>());
        }

        public int checkStock(String productId) {
            return stock.getOrDefault(productId, 0);
        }

        public synchronized String purchaseItem(String productId, int userId) {
            int currentStock = stock.getOrDefault(productId, 0);

            if (currentStock > 0) {
                stock.put(productId, currentStock - 1);
                return "Success, remaining: " + (currentStock - 1);
            } else {
                Queue<Integer> queue = waitingList.get(productId);
                queue.offer(userId);
                return "Added to waiting list, position #" + queue.size();
            }
        }
    }

