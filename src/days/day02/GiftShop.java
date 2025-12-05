package days.day02;

import core.Solver;

import java.util.Arrays;
import java.util.List;

public class GiftShop implements Solver {
    private List<String> getValues(String line) {
        return Arrays.stream(line.split(","))
                .map(String::trim)
                .toList();
    }

    private boolean isRepeatedSequence(long n) {
        String s = Long.toString(n);
        int len = s.length();
        if (len % 2 != 0) return false;
        String first = s.substring(0, len / 2);
        String second = s.substring(len / 2);
        return first.equals(second);
    }

    @Override
    public long solveSilver(List<String> input) {
        List<String> values = getValues(input.get(0));
        return 0;
    }

    @Override
    public long solveGold(List<String> input) {
        return 0;
    }
}
