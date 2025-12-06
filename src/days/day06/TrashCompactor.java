package days.day06;

import core.Solver;

import java.util.ArrayList;
import java.util.List;

public class TrashCompactor implements Solver {
    @Override
    public long solveSilver(List<String> input) {
        long result = 0;
        List<String[]> values = new ArrayList<>();
        for (String line : input) {
            line = line.trim();
            String[] parts = line.split("\\s+");
            values.add(parts);
        }
        int columns = values.get(0).length;
        int lines = values.size();

        for (int i = 0; i < columns; i++) {
            String operator = values.get(lines - 1)[i];
            long temp = Long.parseLong(values.get(0)[i]);
            if (operator.equals("+")) {
                for (int j = 1; j < lines - 1; j++) {
                    temp += Long.parseLong(values.get(j)[i]);
                }
            } else {
                for (int j = 1; j < lines - 1; j++) {
                    temp *= Long.parseLong(values.get(j)[i]);
                }
            }
            result += temp;
        }

        return result;
    }

    @Override
    public long solveGold(List<String> input) {
        return 0;
    }
}
