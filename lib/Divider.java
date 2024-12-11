package lib;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// Interface to initialize Grid
public interface Divider<T> {
    List<T> toList(String s);
    
    public static class Int implements Divider<Integer> {
        @Override
        public List<Integer> toList(String s) {
            return Arrays.stream(s.split("")).mapToInt(c -> Integer.parseInt(c)).boxed().collect(Collectors.toList());
        }
    }

    public static class Char implements Divider<Character> {
        @Override
        public List<Character> toList(String s) {
            return s.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
        }
    }
}

