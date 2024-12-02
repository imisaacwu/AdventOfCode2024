import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import lib.FileIO;

public class day01 {
    public static void main(String[] args) {
        List<String> input = FileIO.read("input/day01.in");
        PriorityQueue<Integer> left = new PriorityQueue<>(), right = new PriorityQueue<>();
        Map<Integer, Integer> counts = new HashMap<>();

        for (String s : input) {
            String[] nums = s.split("   ");
            left.add(Integer.parseInt(nums[0]));
            Integer r = Integer.parseInt(nums[1]);
            right.add(r);
            counts.put(r, counts.getOrDefault(r, 0) + 1);
        }

        int sum = 0;
        int sim = 0;
        while (!left.isEmpty()) {
            int l = left.poll();
            sum += Math.abs(l - right.poll());
            sim += l * counts.getOrDefault(l, 0);
        }

        System.out.println("Day 01:");
        System.out.printf("Part 1: %d\n", sum);
        System.out.printf("Part 2: %d\n", sim);
    }
}