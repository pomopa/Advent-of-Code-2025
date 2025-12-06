package days.day05;

import core.Solver;

import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class Cafeteria implements Solver {
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

    @Override
    public long solveGold(List<String> input) {
        return 0;
    }
}
