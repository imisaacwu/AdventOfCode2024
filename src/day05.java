import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lib.FileIO;

public class day05 {
    public static void main(String[] args) {
        List<String> input = FileIO.read("input/day05.in");

        Map<Integer, Page> rules = new HashMap<>();
        while(!input.get(0).isBlank()) {
            // Read in rules of which pages must go before (pre) and after (post) this Page
            int[] nums = Arrays.stream(input.get(0).split("\\|")).mapToInt(str -> Integer.parseInt(str)).toArray();
            if (!rules.containsKey(nums[0])) { rules.put(nums[0], new Page(nums[0])); }
            if (!rules.containsKey(nums[1])) { rules.put(nums[1], new Page(nums[1])); }
            
            rules.get(nums[0]).post.add(nums[1]);
            rules.get(nums[1]).pre.add(nums[0]);

            input.remove(0);
        }

        // Remove empty line
        input.remove(0);

        int sum = 0, sorted_sum = 0;
        for (String s : input) {
            // Get instructions
            int[] pages = Arrays.stream(s.split(",")).mapToInt(str -> Integer.parseInt(str)).toArray();
            boolean ordered = true;

            // Verify no pages are in the wrong place
            for (int p = 0; p < pages.length; p++) {
                // Check for pages defined to follow after appearing before this page
                for (int pre = 0; pre < p; pre++) {
                    if (rules.get(pages[p]).post.contains(pages[pre])) {
                        ordered = false;
                    }
                }
                // Check for pages defined to appear before following after this page
                for (int post = p + 1; post < pages.length; post++) {
                    if (rules.get(pages[p]).pre.contains(pages[post])) {
                        ordered = false;
                    }
                }
            }

            if (ordered) {
                sum += pages[pages.length/2];
            } else {
                // Sort the Pages, find the middle
                Page[] sort = Arrays.stream(pages).mapToObj(p -> rules.get(p)).toArray(Page[]::new);
                Arrays.sort(sort);
                sorted_sum += sort[sort.length/2].page;
            }
        }

        System.out.println("Day 05:");
        System.out.printf("Part 1: %d\n", sum);
        System.out.printf("Part 2: %d\n", sorted_sum);
    }

    static class Page implements Comparable<Page> {
        int page;
        List<Integer> pre = new ArrayList<>(), post = new ArrayList<>();
        
        public Page(int page) {
            this.page = page;
        }

        @Override
        public int compareTo(day05.Page o) {
            if (this.pre.contains(o.page)) {
                return 1;
            } else {
                return -1;
            }
        }
    }
}