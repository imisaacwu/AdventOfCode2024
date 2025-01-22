import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import file.FileIO;

public class day19 {
    public static void main(String[] args) {
        List<String> input = FileIO.read("input/day19.in");
        Map<Character, List<String>> towels = new HashMap<>();
        for (String towel : input.get(0).split(", ")) {
            if (!towels.containsKey(towel.charAt(0))) {
                towels.put(towel.charAt(0), new ArrayList<>());
            }
            towels.get(towel.charAt(0)).add(towel);
        }

        Map<String, Long> cache = new HashMap<>();
        int possible = 0;
        long arrangements = 0;
        for (int design = 2; design < input.size(); design++) {
            String pattern = input.get(design);
            long perms = permutations(pattern, towels, cache);
            if (perms > 0) { possible++; }
            arrangements += perms;
        }

        System.out.println("Day 19:");
        System.out.printf("Part 1: %d\n", possible);
        System.out.printf("Part 2: %d\n", arrangements);
    }

    private static long permutations(String pattern, Map<Character, List<String>> towels, Map<String, Long> cache) {
        if (pattern.isEmpty()) { return 1; }
        if (cache.containsKey(pattern)) { return cache.get(pattern); }
        List<String> arr = new ArrayList<>(towels.get(pattern.charAt(0)));
        for (int i = arr.size() - 1; i >= 0; i--) {
            if (!pattern.startsWith(arr.get(i))) {
                arr.remove(i);
            }
        }
        long count = 0;
        for (String towel : arr) {
            count += permutations(pattern.substring(towel.length()), towels, cache);
        }
        cache.put(pattern, count);
        return count;
    }
}