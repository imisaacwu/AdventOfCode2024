import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import file.FileIO;

public class day03 {
    public static void main(String[] args) {
        List<String> input = FileIO.read("input/day03.in");
        final Pattern p = Pattern.compile("mul\\((\\d+),(\\d+)\\)|(do|don't)\\(\\)");

        boolean enabled = true;
        int result = 0, filtered = 0;
        for (String s : input) {
            Matcher m = p.matcher(s);
            while (m.find()) {
                if (m.group(0).startsWith("mul")) {
                    int product = Integer.parseInt(m.group(1)) * Integer.parseInt(m.group(2));
                    result += product;
                    if (enabled) {
                        filtered += product;
                    }
                } else {
                    enabled = !m.group(0).startsWith("don't");
                }
            }
        }

        System.out.println("Day 03:");
        System.out.printf("Part 1: %d\n", result);
        System.out.printf("Part 2: %d\n", filtered);
    }
}