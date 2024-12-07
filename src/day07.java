import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lib.FileIO;

public class day07 {
    public static void main(String[] args) {
        List<String> input = FileIO.read("input/day07.in");

        long part1 = 0, part2 = 0;
        for (String s : input) {
            String[] in = s.split(": ");
            long target = Long.parseLong(in[0]);
            int[] nums = Arrays.stream(in[1].split(" ")).mapToInt(str -> Integer.parseInt(str)).toArray();

            List<String> eqs = new ArrayList<>();
            eqs.addAll(makeEqs(nums, '+'));
            eqs.addAll(makeEqs(nums, '*'));
            eqs.addAll(makeEqs(nums, '|'));

            for (String eq : eqs) {
                if (evaluate(eq) == target) {
                    if (!eq.contains("|")) { part1 += target; }
                    part2 += target;
                    break;
                }
            }
        }

        System.out.println("Day 07:");
        System.out.printf("Part 1: %d\n", part1);
        System.out.printf("Part 2: %d\n", part2);
    }

    static List<String> makeEqs(int[] nums, char op) {
        if (nums.length == 2) {
            switch (op) {
                case '+': return new ArrayList<>(Arrays.asList(nums[0] + " + " + nums[1]));
                case '*': return new ArrayList<>(Arrays.asList(nums[0] + " * " + nums[1]));
                case '|': return new ArrayList<>(Arrays.asList(nums[0] + " | " + nums[1]));
            }
        }
        List<String> sub = new ArrayList<>();
        sub.addAll(makeEqs(Arrays.copyOfRange(nums, 1, nums.length), '+'));
        sub.addAll(makeEqs(Arrays.copyOfRange(nums, 1, nums.length), '*'));
        sub.addAll(makeEqs(Arrays.copyOfRange(nums, 1, nums.length), '|'));

        List<String> eqs = new ArrayList<>();
        for (String s : sub) {
            eqs.add(nums[0] + " " + op + " " + s);
        }

        return eqs;
    }

    static long evaluate(String eq) {
        String[] elements = eq.split(" ");
        long result = Long.parseLong(elements[0]);
        char op = elements[1].charAt(0);
        
        for (int i = 2; i < elements.length; i++) {
            if (i % 2 == 0) {
                long other = Long.parseLong(elements[i]);
                switch (op) {
                    case '+':
                        result += other;
                        break;
                    case '*':
                        result *= other;
                        break;
                    case '|':
                        result = Long.parseLong(result + "" + other);
                        break;
                }
            } else {
                op = elements[i].charAt(0);
            }
        }

        return result;
    }
}