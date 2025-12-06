package days.day06;

import core.Solver;

import java.util.ArrayList;
import java.util.List;

public class TrashCompactor implements Solver {
    private long applyOperator(List<Long> numbers, char operator) {
        long temp = numbers.get(0);
        for (int i = 1; i < numbers.size(); i++) {
            if (operator == '+') {
                temp += numbers.get(i);
            } else {
                temp *= numbers.get(i);
            }
        }
        return temp;
    }
    @Override
    public long solveSilver(List<String> input) {
        long result = 0;
        List<String[]> values = new ArrayList<>();
        for (String line : input) {
            values.add(line.trim().split("\\s+"));
        }
        int columns = values.get(0).length;
        int lines = values.size();

        for (int i = 0; i < columns; i++) {
            String operatorStr = values.get(lines - 1)[i];
            char operator = operatorStr.charAt(0);

            List<Long> numbers = new ArrayList<>();
            for (int j = 0; j < lines - 1; j++) {
                numbers.add(Long.parseLong(values.get(j)[i]));
            }

            result += applyOperator(numbers, operator);
        }

        return result;
    }

    private List<String> padLinesToEqualLength(List<String> input) {
        int maxLength = input.stream()
                .mapToInt(String::length)
                .max()
                .orElse(0);

        List<String> paddedInput = new ArrayList<>();
        for (String line : input) {
            StringBuilder sb = new StringBuilder(line);
            while (sb.length() < maxLength) {
                sb.append(' ');
            }
            paddedInput.add(sb.toString());
        }

        return paddedInput;
    }

    @Override
    public long solveGold(List<String> input) {
        input = padLinesToEqualLength(input);
        int columns = input.get(0).length() - 1;
        int lines = input.size();
        long result = 0;

        while (columns >= 0) {
            List<Long> numbers = new ArrayList<>();
            do {
                StringBuilder numberBuilder = new StringBuilder();

                for (int row = 0; row < lines - 1; row++) {
                    char c = input.get(row).charAt(columns);
                    if (c != ' ') {
                        numberBuilder.append(c);
                    }
                }
                numbers.add(Long.parseLong(numberBuilder.toString()));
                columns--;
            } while (input.get(lines - 1).charAt(columns + 1) == ' ');

            char operator = input.get(lines - 1).charAt(columns + 1);
            result += applyOperator(numbers, operator);

            columns--;
        }

        return result;
    }
}
