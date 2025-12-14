package days.day04;

import core.Solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Solver for Day 04: Printing Department problem.
 * <p>
 * Implements the {@link core.Solver} interface, providing solutions for both
 * Silver and Gold variants. The problem involves counting accessible rolls of paper
 * in a 2D board according to specific adjacency rules.
 * </p>
 */
public class PrintingDepartment implements Solver {

    /**
     * Determines whether a paper roll at the specified position is accessible.
     * <p>
     * A roll is considered accessible if there are at most 4 other rolls ('@') in its
     * 3x3 neighborhood (including itself). Returns {@code false} if more than 4 rolls
     * are adjacent.
     * </p>
     *
     * @param line  the row index of the roll
     * @param column the column index of the roll
     * @param board  the 2D board as a list of character sequences
     * @return {@code true} if the roll is accessible, {@code false} otherwise
     */
    private boolean isAccessible(int line, int column, List<? extends CharSequence> board) {
        int count = 0;
        for (int l = line - 1; l <= line + 1; l++) {
            for (int c = column - 1; c <= column + 1; c++) {
                if (l < 0 || l >= board.size() || c < 0 || c >= board.get(0).length()) continue;
                if (board.get(l).charAt(c) == '@') {
                    count++;
                    if (count > 4) return false;
                }
            }
        }
        return true;
    }

    /**
     * Solves the Silver variant of the Printing Department problem.
     * <p>
     * Counts all accessible rolls on the board without modifying the input.
     * </p>
     *
     * @param input list of strings representing the 2D board
     * @return the total number of accessible rolls for the Silver problem
     */
    @Override
    public long solveSilver(List<String> input) {
        int n_lines = input.size();
        int n_columns = input.get(0).length();
        long rolls = 0;

        for (int line = 0; line < n_lines; line++) {
            for (int column = 0; column < n_columns; column++) {
                if (input.get(line).charAt(column) == '.') continue;
                if (isAccessible(line, column, input)) rolls++;
            }
        }

        return rolls;
    }

    /**
     * Solves the Gold variant of the Printing Department problem.
     * <p>
     * Simulates removing accessible rolls iteratively from the board until all rolls
     * have been removed, counting each accessible roll removal as a "roll".
     * </p>
     *
     * @param input list of strings representing the 2D board
     * @return the total number of accessible rolls for the Gold problem
     */
    @Override
    public long solveGold(List<String> input) {
        int n_lines = input.size();
        int n_columns = input.get(0).length();
        long rolls = 0;
        boolean changes;
        int n_papers = 0;
        boolean[] activeRows = new boolean[n_lines];
        Arrays.fill(activeRows, true);

        List<StringBuilder> board = new ArrayList<>();
        for (String line : input) {
            board.add(new StringBuilder(line));
        }

        for (StringBuilder sb : board) {
            for (int c = 0; c < sb.length(); c++) {
                if (sb.charAt(c) != '.') n_papers++;
            }
        }

        do {
            changes = false;
            for (int line = 0; line < n_lines; line++) {
                if (!activeRows[line]) continue;
                boolean hasPaper = false;

                for (int column = 0; column < n_columns; column++) {
                    if (board.get(line).charAt(column) == '.') continue;

                    if (isAccessible(line, column, board)) {
                        board.get(line).setCharAt(column, '.');
                        n_papers--;
                        changes = true;
                        rolls++;
                    } else {
                        hasPaper = true;
                    }
                }

                activeRows[line] = hasPaper;
            }
        } while (n_papers > 0 && changes);

        return rolls;
    }
}
