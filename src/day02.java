import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import file.FileIO;

public class day02 {
    public static void main(String[] args) {
        List<String> input = FileIO.read("input/day02.in");
        int safe = 0, safeDampened = 0;

        for (String s : input) {
            List<Integer> report = Arrays.stream(s.split(" ")).mapToInt(c -> Integer.parseInt(c)).boxed().collect(Collectors.toList());
            if (isSafe(report)) {
                safe++;
                safeDampened++;
            } else {
                for (int i = 0; i < report.size(); i++) {
                    int removed = report.remove(i);
                    if (isSafe(report)) {
                        safeDampened++;
                        break;
                    }
                    report.add(i, removed);
                }
            }
        }

        System.out.println("Day 02:");
        System.out.printf("Part 1: %d\n", safe);
        System.out.printf("Part 2: %d\n", safeDampened);
    }

    static boolean isSafe(List<Integer> report) {
        boolean increasing = report.get(0) < report.get(1);
        for (int i = 1; i < report.size(); i++) {
            int diff = Math.abs(report.get(i-1) - report.get(i));
            if (diff <= 0 || diff > 3 ||
                (increasing && report.get(i-1) > report.get(i)) ||
                (!increasing && report.get(i-1) < report.get(i))) {
                return false;
            }
        }
        return true;
    }
}