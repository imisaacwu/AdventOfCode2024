import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import lib.FileIO;

public class day11 {
    public static void main(String[] args) {
        long[] input = Arrays.stream(FileIO.read("input/day11.in").get(0).split(" ")).mapToLong(s -> Long.parseLong(s)).toArray();

        // Symbol, number of stones with symbol
        Map<Long, Long> stones = new HashMap<>(), next = new HashMap<>();
        // Symbol, next symbol(s)
        Map<Long, Long[]> cache = new HashMap<>();

        // Initialize map
        for (long l : input) {
            stones.put(l, stones.getOrDefault(stones, 0l) + 1);
        }

        long count25 = 0, count75 = 0;
        for (int blink = 1; blink <= 75; blink++) {
            next.clear();

            for (long i : stones.keySet()) {
                if (i == 0) {
                    // First rule
                    next.put(1l, next.getOrDefault(1l, 0l) + stones.get(i));
                } else if (cache.containsKey(i)) {
                    // Check if we've already calculated the result of this symbol already
                    for (Long l : cache.get(i)) {
                        next.put(l, next.getOrDefault(l, 0l) + stones.get(i));
                    }
                } else {
                    int digits = (int) (Math.log10(i) + 1);
                    if (digits % 2 == 0) {
                        // Second rule
                        long front = (long) (i / Math.pow(10, digits / 2));
                        long back = (long) (i % Math.pow(10, digits / 2));
                        next.put(front, next.getOrDefault(front, 0l) + stones.get(i));
                        next.put(back, next.getOrDefault(back, 0l) + stones.get(i));
                        cache.put(i, new Long[]{front, back});
                    } else {
                        // Third rule
                        long num = 2024 * i;
                        next.put(num, next.getOrDefault(num, 0l) + stones.get(i));
                        cache.put(i, new Long[]{num});
                    }
                }
            }
            stones = Map.copyOf(next);

            if (blink == 25) {
                for (long num : stones.keySet()) {
                    count25 += stones.get(num);
                }
            }
        }

        for (long num : stones.keySet()) {
            count75 += stones.get(num);
        }

        System.out.println("Day 11:");
        System.out.printf("Part 1: %d\n", count25);
        System.out.printf("Part 2: %d\n", count75);
    }
}