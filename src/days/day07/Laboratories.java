package days.day07;

import core.Solver;

import java.util.List;
import java.util.ArrayList;

/**
 * Solver for Day 07: Laboratories problem.
 * <p>
 * Implements the {@link core.Solver} interface, providing solutions for both
 * Silver and Gold variants. The problem involves simulating beam paths through
 * a laboratory grid and counting splits or total possible paths.
 * </p>
 */
public class Laboratories implements Solver {

    /**
     * Replaces characters at specified indexes in a string with the '|' character.
     *
     * @param input   the input string
     * @param indexes list of indexes to replace with '|'
     * @return a new string with specified characters replaced by '|'
     */
    private String replaceIndexesWithBar(String input, List<Integer> indexes) {
        char[] chars = input.toCharArray();

        for (int idx : indexes) {
            if (idx >= 0 && idx < chars.length) {
                chars[idx] = '|';
            }
        }

        return new String(chars);
    }

    /**
     * Solves the Silver variant of the Laboratories problem.
     * <p>
     * Simulates beam propagation through the grid. Counts the number of beam splits
     * when encountering a '^' in the path. Uses a simple propagation method marking
     * paths with '|'.
     * </p>
     *
     * @param input list of strings representing the laboratory grid
     * @return the number of beam splits for the Silver problem
     */
    @Override
    public long solveSilver(List<String> input) {
        long beamSplits = 0;
        int i = 2;
        input.set(1, replaceIndexesWithBar(
                input.get(1),
                List.of(input.get(0).length() / 2)
        ));

        while (i + 1 < input.size()) {
            String currentLine = input.get(i);
            String previousLine = input.get(i - 1);
            List<Integer> indexesToUpdate = new ArrayList<>();

            for (int index = 0; index < currentLine.length(); index++) {
                if (previousLine.charAt(index) == '|') {
                    if (currentLine.charAt(index) == '^') {
                        beamSplits++;
                        indexesToUpdate.add(index - 1);
                        indexesToUpdate.add(index + 1);
                    } else {
                        indexesToUpdate.add(index);
                    }
                }
            }

            input.set(i + 1,
                    replaceIndexesWithBar(input.get(i + 1), indexesToUpdate)
            );
            i += 2;
        }

        return beamSplits;
    }

    /**
     * Solves the Gold variant of the Laboratories problem.
     * <p>
     * Computes all possible beam paths through the grid using dynamic programming.
     * Each cell accumulates the number of ways a beam can reach it, considering
     * movement rules for '.' and other characters.
     * </p>
     *
     * @param input list of strings representing the laboratory grid
     * @return the total number of valid beam timelines for the Gold problem
     */
    @Override
    public long solveGold(List<String> input) {
        int rows = input.size();
        int cols = input.get(0).length();
        long[] counts = new long[cols];
        counts[input.get(0).length() / 2] = 1;

        for (int r = 1; r < rows; r++) {
            long[] nextCounts = new long[cols];
            String line = input.get(r);

            for (int x = 0; x < cols; x++) {
                long c = counts[x];
                if (c == 0) continue;

                char cell = line.charAt(x);

                if (cell == '.') {
                    nextCounts[x] += c;
                } else {
                    if (x - 1 >= 0) nextCounts[x - 1] += c;
                    if (x + 1 < cols) nextCounts[x + 1] += c;
                }
            }

            counts = nextCounts;
        }

        long totalTimelines = 0;
        for (long c : counts) totalTimelines += c;
        return totalTimelines;
    }

}
