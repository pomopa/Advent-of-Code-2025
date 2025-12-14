package days.day03;

import core.Solver;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * Solver for Day 03: Lobby problem.
 * <p>
 * Implements the {@link core.Solver} interface, providing solutions for both
 * Silver and Gold variants. The problem involves calculating the maximum joltage
 * by selecting a subsequence of digits from each input line.
 * </p>
 */
public class Lobby implements Solver {

    /**
     * Calculates the maximum number that can be formed by selecting {@code k} digits
     * from the given line while preserving order.
     * <p>
     * Uses a greedy stack-based algorithm to ensure that the resulting number is
     * maximized.
     * </p>
     *
     * @param line the string of digits
     * @param k    the number of digits to select
     * @return the maximum number formed as a {@code long}
     */
    private long maxJoltageForLine(String line, int k) {
        int n = line.length();
        Deque<Character> stack = new ArrayDeque<>();
        int toRemove = n - k;

        for (char c : line.toCharArray()) {
            while (!stack.isEmpty() && toRemove > 0 && stack.peekLast() < c) {
                stack.pollLast();
                toRemove--;
            }
            stack.addLast(c);
        }

        while (stack.size() > k) {
            stack.pollLast();
        }

        StringBuilder sb = new StringBuilder(k);
        for (char c : stack) sb.append(c);

        return Long.parseLong(sb.toString());
    }

    /**
     * Solves the Silver variant of the Lobby problem.
     * <p>
     * For each input line, selects 2 digits to maximize the joltage, and sums
     * the results across all lines.
     * </p>
     *
     * @param input list of strings representing the problem input
     * @return the total maximum joltage for the Silver problem
     */
    @Override
    public long solveSilver(List<String> input) {
        long joltage = 0;

        for (String line : input) {
            joltage += maxJoltageForLine(line, 2);
        }

        return joltage;
    }

    /**
     * Solves the Gold variant of the Lobby problem.
     * <p>
     * For each input line, selects 12 digits to maximize the joltage, and sums
     * the results across all lines.
     * </p>
     *
     * @param input list of strings representing the problem input
     * @return the total maximum joltage for the Gold problem
     */
    @Override
    public long solveGold(List<String> input) {
        long joltage = 0;

        for (String line : input) {
            joltage += maxJoltageForLine(line, 12);
        }

        return joltage;
    }
}
