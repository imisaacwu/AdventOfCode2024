import java.util.ArrayList;
import java.util.List;
import lib.FileIO;

public class day09 {
    public static void main(String[] args) {
        char[] input = FileIO.read("input/day09.in").get(0).toCharArray();
        List<Block> blocks = new ArrayList<>(), full = new ArrayList<>();

        for (int i = 0; i < input.length; i++) {
            if (i % 2 == 0) {
                blocks.add(new Block(i / 2, input[i] - '0', 0));
                full.add(new Block(i / 2, input[i] - '0', 0));
            } else {
                blocks.get(blocks.size() - 1).free = input[i] - '0';
                full.get(full.size() - 1).free = input[i] - '0';
            }
        }

        int front = 0, back = blocks.size() - 1;
        while (front < back) {
            Block f = blocks.get(front), b = blocks.get(back);

            while (f.free > 0) {
                // Switch to next back source
                if (b.len <= 0) {
                    blocks.remove(b);
                    b = blocks.get(--back);
                    if (front == back) { break; }
                }

                // Fill space
                f.space.add(b.id);
                f.free--;
                b.len--;
            }

            front++;
        }

        back = full.size() - 1;
        while (back > 0) {
            front = 0;
            while (front < back) {
                if (full.get(front).free >= full.get(back).len) {
                    // Found space, insert
                    for (int i = 0; i < full.get(back).len; i++) {
                        full.get(front).space.add(full.get(back).id);
                    }
                    full.get(front).free -= full.get(back).len;
                    // Essentially skipping in checksum, but the position is still there
                    full.get(back).id = 0;
                    break;
                }
                front++;
            }
            back--;
        }

        long sum = 0, pos = 0;
        for (Block b : blocks) {
            for (int i = 0; i < b.len; i++) {
                sum += pos * b.id;
                pos++;
            }
            for (int n : b.space) {
                if (n < 0) { break; }
                sum += pos * n;
                pos++;
            }
        }

        long fullSum = 0;
        pos = 0;
        for (Block b : full) {
            for (int i = 0; i < b.len; i++) {
                fullSum += pos * b.id;
                pos++;
            }
            for (int n : b.space) {
                if (n >= 0) {
                    fullSum += pos * n;
                }
                pos++;
            }
            pos += b.free;
        }

        System.out.println("Day 09:");
        System.out.printf("Part 1: %s\n", sum);
        System.out.printf("Part 2: %d\n", fullSum);
    }

    // Represents a block of data and its free space in front of it
    static class Block {
        int id, len, free;
        List<Integer> space;

        public Block(int id, int len, int free) {
            this.id = id;
            this.len = len;
            this.free = free;
            space = new ArrayList<>();
        }

        public String toString() {
            String s = id + "x" + len + "+" + space;
            return s;
        }
    }
}