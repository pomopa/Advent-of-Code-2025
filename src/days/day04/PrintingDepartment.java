package days.day04;

import core.Solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrintingDepartment implements Solver {

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
