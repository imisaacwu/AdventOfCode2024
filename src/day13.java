import java.util.ArrayList;
import java.util.List;

import file.FileIO;
import math.Matrix;
import structures.Direction;
import structures.Tuple;

public class day13 {
    public static void main(String[] args) {
        List<String> input = FileIO.read("input/day13.in");
        List<Machine> machines = new ArrayList<>();
        Machine curr = new Machine();

        for (String s : input) {
            String[] arr = s.replace(",", "").split(" ");
            if (s.startsWith("Button A")) {
                curr.a = new Direction(
                    Integer.parseInt(arr[2].split("\\+")[1]),
                    Integer.parseInt(arr[3].split("\\+")[1]));
            } else if (s.startsWith("Button B")) {
                curr.b = new Direction(
                    Integer.parseInt(arr[2].split("\\+")[1]),
                    Integer.parseInt(arr[3].split("\\+")[1]));
            } else if (s.startsWith("Prize")) {
                curr.prize = new Tuple.Pair<>(
                    Long.parseLong(arr[1].split("=")[1]),
                    Long.parseLong(arr[2].split("=")[1]));
            } else {
                machines.add(curr);
                curr = new Machine();
            }
        }
        machines.add(curr);

        long tokens = 0, farTokens = 0;
        for (Machine m : machines) {
            tokens += m.tokens();
            m.prize = new Tuple.Pair<>(m.prize.v0() + 10000000000000l, m.prize.v1() + 10000000000000l);
            farTokens += m.tokens();
        }

        System.out.println("Day 13:");
        System.out.printf("Part 1: %d\n", tokens);
        System.out.printf("Part 2: %d\n", farTokens);
    }

    static class Machine {
        Direction a, b;
        Tuple.Pair<Long, Long> prize;

        public long tokens() {
            Matrix buttons = Matrix.fromStr(String.format("%d %d\n%d %d", a.Δr(), b.Δr(), a.Δc(), b.Δc()));
            Matrix p = Matrix.fromStr(String.format("%d\n%d", prize.v0(), prize.v1()));

            Matrix v = buttons.inverse().multiply(p);

            long ta = Math.round(v.get(0, 0)), tb = Math.round(v.get(1, 0));
            // Check for partial button presses
            if (ta * a.Δr() + tb * b.Δr() == prize.v0() &&
                ta * a.Δc() + tb * b.Δc() == prize.v1()) {
                return 3 * ta + tb;
            }
            return 0;
        }
    }
}