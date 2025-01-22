import java.util.ArrayList;
import java.util.List;

import file.FileIO;

public class day17 {
    public static void main(String[] args) {
        List<String> input = FileIO.read("input/day17.in");
        long A = Long.parseLong(input.get(0).split(" ")[2]);
        long B = Long.parseLong(input.get(1).split(" ")[2]);
        long C = Long.parseLong(input.get(2).split(" ")[2]);
        final String program = input.get(4).split(" ")[1].replaceAll(",", "");

        List<String> result = runProgram(program, A, B, C);

        List<Long> As = new ArrayList<>(List.of(0l));
        for (int i = program.length() - 1; i >= 0; i--) {
            // Iterate backwards through each digit in the program
            List<Long> temp = new ArrayList<>();
            for (Long a : As) {
                for (long j = 0; j < 8; j++) {
                    // Test possible values of A
                    if (digit((a << 3) + j) == (program.charAt(i) - '0')) {
                        temp.add((a << 3) + j);
                    }
                }
            }
            // Retain valid values of A so far
            As = temp;
        }

        // Double-check As
        // (TODO: Figure out why we get false As: [16247842866690, 16247842886907])
        for (int i = As.size() - 1; i >= 0; i--) {
            if (!program.equals(String.join("", runProgram(As.get(i))))) {
                As.remove(i);
            }
        }

        System.out.println("Day 17:");
        System.out.printf("Part 1: %s\n", String.join(",", result));
        System.out.printf("Part 2: %s\n", As.get(0));
    }

    // Simplified, hard-coded version of input program that
    // calculates one digit of output given chunks of A
    static long digit(long A) {
        long partial = (A % 8) ^ 4;
        return ((partial ^ (A >> partial)) ^ 4) % 8;
    }

    // Simplified, hard-coded version of input program
    static String runProgram(long A) {
        StringBuilder out = new StringBuilder();
        while (A != 0) {
            out.append(digit(A));
            A >>= 3;
        }
        return out.toString();
    }

    static List<String> runProgram(String program, long A, long B, long C) {
        List<String> output = new ArrayList<>();
        int instr_ptr = 0;
        while (instr_ptr < program.length()) {
            int opcode = program.charAt(instr_ptr) - '0';
            int operand = program.charAt(instr_ptr + 1) - '0';
            long combo;

            switch (operand) {
                case 4: combo = A; break;
                case 5: combo = B; break;
                case 6: combo = C; break;
                default: combo = operand;
            }

            switch (opcode) {
                case 0:
                    A = (long) (A / Math.pow(2, combo));
                    break;
                case 1:
                    B = B ^ operand;
                    break;
                case 2:
                    B = combo % 8;
                    break;
                case 3:
                    if (A != 0) {
                        instr_ptr = operand;
                        continue;
                    }
                    break;
                case 4:
                    B = B ^ C;
                    break;
                case 5:
                    output.add(String.valueOf(combo % 8));
                    break;
                case 6:
                    B = (long) (A / Math.pow(2, combo));
                    break;
                case 7:
                    C = (long) (A / Math.pow(2, combo));
                    break;
            }
            instr_ptr += 2;
        }
        return output;
    }
}