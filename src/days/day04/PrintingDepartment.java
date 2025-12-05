package days.day04;

import core.Solver;

import java.util.List;

public class PrintingDepartment implements Solver {
    private boolean isAccessible(int line, int column, List<String> input) {
        int count = 0;

        for (int l = line - 1; l < line + 2; l++) {
            for (int c = column - 1; c < column + 2; c++) {
                if (l < 0 || l >= input.size() || c < 0 || c >= input.get(0).length()) continue;
                if (input.get(l).charAt(c) == '@') {
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
        return 0;
    }
}
