package days.day05;

import core.Solver;

import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

/**
 * Solver for Day 05: Cafeteria problem.
 * <p>
 * Implements the {@link core.Solver} interface, providing solutions for both
 * Silver and Gold variants. The problem involves counting values that fall
 * within specified ranges and calculating total covered ranges.
 * </p>
 */
public class Cafeteria implements Solver {

    /**
     * Parses the input into separate ranges and individual values.
     * <p>
     * Lines containing a dash '-' are treated as ranges [start-end], while other
     * lines are treated as individual values.
     * </p>
     *
     * @param input list of strings representing the problem input
     * @return a {@link ParsedInput} object containing lists of ranges and values
     */
    private ParsedInput parseInput(List<String> input) {
        List<long[]> ranges = new ArrayList<>();
        List<Long> values = new ArrayList<>();

        for (String line : input) {
            line = line.trim();
            if (line.isEmpty()) continue;

            if (line.contains("-")) {
                String[] parts = line.split("-");
                long start = Long.parseLong(parts[0]);
                long end = Long.parseLong(parts[1]);
                ranges.add(new long[]{start, end});
            } else {
                values.add(Long.parseLong(line));
            }
        }

        return new ParsedInput(ranges, values);
    }

    /**
     * Merges overlapping or contiguous ranges into a minimal list of ranges.
     *
     * @param ranges list of ranges represented as long arrays [start, end]
     * @return a new list of merged ranges
     */
    private List<long[]> mergeRanges(List<long[]> ranges) {
        ranges.sort(Comparator.comparingLong(a -> a[0]));

        List<long[]> merged = new ArrayList<>();
        long[] curr = ranges.get(0);

        for (int i = 1; i < ranges.size(); i++) {
            long[] r = ranges.get(i);

            if (r[0] <= curr[1]) {
                curr[1] = Math.max(curr[1], r[1]);
            } else {
                merged.add(curr);
                curr = r;
            }
        }
        merged.add(curr);
        return merged;
    }

    /**
     * Checks whether a value is contained within any of the given ranges.
     *
     * @param ranges list of merged ranges
     * @param value  the value to check
     * @return {@code true} if the value falls within a range, {@code false} otherwise
     */
    private boolean isInRanges(List<long[]> ranges, long value) {
        int low = 0, high = ranges.size() - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            long[] r = ranges.get(mid);

            if (value < r[0]) {
                high = mid - 1;
            } else if (value > r[1]) {
                low = mid + 1;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * Solves the Silver variant of the Cafeteria problem.
     * <p>
     * Counts the number of individual values that fall within any of the input ranges.
     * </p>
     *
     * @param input list of strings representing the problem input
     * @return the count of values within ranges for the Silver problem
     */
    @Override
    public long solveSilver(List<String> input) {
        ParsedInput parsed = parseInput(input);
        List<long[]> ranges = parsed.ranges;
        List<Long> values = parsed.values;

        List<long[]> merged = mergeRanges(ranges);
        long count = 0;

        for (long v : values) {
            if (isInRanges(merged, v)) {
                count++;
            }
        }

        return count;
    }

    /**
     * Solves the Gold variant of the Cafeteria problem.
     * <p>
     * Computes the total number of integers covered by all ranges after merging.
     * </p>
     *
     * @param input list of strings representing the problem input
     * @return the total count of numbers in ranges for the Gold problem
     */
    @Override
    public long solveGold(List<String> input) {
        ParsedInput parsed = parseInput(input);
        List<long[]> ranges = parsed.ranges;

        List<long[]> merged = mergeRanges(ranges);

        long count = 0;

        for (long[] range : merged) {
            count += range[1] - range[0] + 1;
        }

        return count;
    }
}
