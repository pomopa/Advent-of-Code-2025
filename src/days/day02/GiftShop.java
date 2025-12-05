package days.day02;

import core.Solver;

import java.math.BigInteger;
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
        long sum = 0;
        List<String> ranges = getValues(input.get(0));

        for (String range : ranges) {
            String[] bounds = range.split("-");
            long start = Long.parseLong(bounds[0]);
            long end = Long.parseLong(bounds[1]);

            for (long id = start; id <= end; id++) {
                if (isRepeatedSequence(id)) {
                    sum += id;
                }
            }
        }

        return sum;
    }

    @Override
    public long solveGold(List<String> input) {
        return 0;
    }
}
