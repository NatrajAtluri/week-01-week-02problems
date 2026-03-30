import java.util.*;

class TwoSum {

    public List<int[]> twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        List<int[]> result = new ArrayList<>();

        for (int num : nums) {
            int complement = target - num;
            if (map.containsKey(complement)) {
                result.add(new int[]{complement, num});
            }
            map.put(num, 1);
        }
        return result;
    }
}