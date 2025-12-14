package days.day02;

import core.Solver;

import java.util.Arrays;
import java.util.List;

/**
 * Solver for Day 02: Gift Shop problem.
 * <p>
 * Implements the {@link core.Solver} interface, providing solutions for both
 * Silver and Gold variants. The problem involves identifying numbers with
 * repeated digit sequences according to specific rules.
 * </p>
 */
public class GiftShop implements Solver {

    /**
     * Splits a line by commas and trims whitespace from each value.
     *
     * @param line the input line containing comma-separated values
     * @return a list of trimmed strings
     */
    private List<String> getValues(String line) {
        return Arrays.stream(line.split(","))
                .map(String::trim)
                .toList();
    }

    /**
     * Checks if a number consists of a repeated sequence repeated multiple times.
     * <p>
     * For example, 1212 is repeated sequence twice, 123123 repeated sequence three times is true.
     * </p>
     *
     * @param n the number to check
     * @return {@code true} if the number is a repeated sequence multiple times, {@code false} otherwise
     */
    private boolean isRepeatedSequenceMultiple(long n) {
        String s = Long.toString(n);
        int len = s.length();
        for (int i = 1; i <= len / 2; i++) {
            if (len % i != 0) continue;
            String pattern = s.substring(0, i);
            StringBuilder sb = new StringBuilder();
            int times = len / i;
            sb.append(pattern.repeat(times));
            if (sb.toString().equals(s)) return true;
        }
        return false;
    }

    /**
     * Checks if a number consists of a sequence repeated exactly twice.
     * <p>
     * For example, 1212 returns true, 123123 returns false.
     * </p>
     *
     * @param n the number to check
     * @return {@code true} if the number is repeated twice, {@code false} otherwise
     */
    private boolean isRepeatedSequenceTwice(long n) {
        String s = Long.toString(n);
        int len = s.length();
        if (len % 2 != 0) return false;
        String first = s.substring(0, len / 2);
        String second = s.substring(len / 2);
        return first.equals(second);
    }

    /**
     * Solves the Silver variant of the Gift Shop problem.
     * <p>
     * Sums all numbers in the input ranges that consist of a sequence repeated exactly twice.
     * </p>
     *
     * @param input list of strings representing the problem input
     * @return the sum of all valid numbers for the Silver problem
     */
    @Override
    public long solveSilver(List<String> input) {
        long sum = 0;
        List<String> ranges = getValues(input.get(0));

        for (String range : ranges) {
            String[] bounds = range.split("-");
            long start = Long.parseLong(bounds[0]);
            long end = Long.parseLong(bounds[1]);

            for (long id = start; id <= end; id++) {
                if (isRepeatedSequenceTwice(id)) {
                    sum += id;
                }
            }
        }

        return sum;
    }

    /**
     * Solves the Gold variant of the Gift Shop problem.
     * <p>
     * Sums all numbers in the input ranges that consist of a repeated sequence
     * multiple times (including twice or more).
     * </p>
     *
     * @param input list of strings representing the problem input
     * @return the sum of all valid numbers for the Gold problem
     */
    @Override
    public long solveGold(List<String> input) {
        long sum = 0;
        List<String> ranges = getValues(input.get(0));

        for (String range : ranges) {
            String[] bounds = range.split("-");
            long start = Long.parseLong(bounds[0]);
            long end = Long.parseLong(bounds[1]);

            for (long id = start; id <= end; id++) {
                if (isRepeatedSequenceMultiple(id)) {
                    sum += id;
                }
            }
        }

        return sum;
    }
}
