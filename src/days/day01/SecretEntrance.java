package days.day01;

import core.Solver;
import java.util.List;

/**
 * Solver for Day 01: Secret Entrance problem.
 * <p>
 * Implements the {@link core.Solver} interface, providing solutions for both
 * the Silver and Gold variants of the problem. The logic calculates dial
 * movements and counts zeros according to the problem rules.
 * </p>
 */
public class SecretEntrance implements Solver {

    /**
     * Computes the password count based on the input lines and method.
     * <p>
     * Iterates over each instruction in the input, calculates dial movement,
     * and counts occurrences of zeros using two different methods:
     * <ul>
     *     <li>Method 0x (Gold): counts all crossings of zero.</li>
     *     <li>Method 1 (Silver): counts zeros at the final dial position.</li>
     * </ul>
     * </p>
     *
     * @param method_0x if {@code true}, uses the Gold method; if {@code false}, uses Silver
     * @param lines     list of strings representing movement instructions
     * @return the number of zeros counted according to the chosen method
     */
    private static int find_password(boolean method_0x, List<String> lines) {
        int dial = 50;
        int countZerosP1 = 0;
        int countZerosP2 = 0;

        for (String line : lines) {
            char dirChar = line.charAt(0);
            int direction = (dirChar == 'L') ? -1 : 1;
            int value = Integer.parseInt(line.substring(1));

            int start = dial;
            int delta = direction * value;
            int end = start + delta;

            int raw = Math.floorMod(-start * direction, 100);
            int first_t = (raw == 0) ? 100 : raw;

            int crosses = 0;
            if (first_t <= value) {
                crosses = 1 + (value - first_t) / 100;
            }
            countZerosP1 += crosses;

            dial = Math.floorMod(end, 100);
            if (dial == 0) {
                countZerosP2++;
            }
        }

        return method_0x ? countZerosP1 : countZerosP2;
    }

    /**
     * Solves the Silver variant of the Secret Entrance problem.
     *
     * @param input list of strings representing the problem input
     * @return the result for the Silver problem as a {@code long}
     */
    @Override
    public long solveSilver(List<String> input) {
        return find_password(false, input);
    }

    /**
     * Solves the Gold variant of the Secret Entrance problem.
     *
     * @param input list of strings representing the problem input
     * @return the result for the Gold problem as a {@code long}
     */
    @Override
    public long solveGold(List<String> input) {
        return find_password(true, input);
    }
}
