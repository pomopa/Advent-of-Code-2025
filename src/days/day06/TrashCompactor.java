package days.day06;

import core.Solver;

import java.util.ArrayList;
import java.util.List;

/**
 * Solver for Day 06: Trash Compactor problem.
 * <p>
 * Implements the {@link core.Solver} interface, providing solutions for both
 * Silver and Gold variants. The problem involves performing arithmetic operations
 * on columns of numbers with an operator specified in the input.
 * </p>
 */
public class TrashCompactor implements Solver {

    /**
     * Applies the given operator ('+' or '*') to a list of numbers sequentially.
     *
     * @param numbers  list of numbers to operate on
     * @param operator the operator character ('+' or '*')
     * @return the result of applying the operator on all numbers
     */
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

    /**
     * Solves the Silver variant of the Trash Compactor problem.
     * <p>
     * Processes each column separately: collects all numbers from that column,
     * applies the operator indicated at the bottom row, and sums the results.
     * </p>
     *
     * @param input list of strings representing the problem input
     * @return the total result for the Silver problem
     */
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

    /**
     * Pads all input lines with spaces to ensure they have equal length.
     *
     * @param input list of input strings
     * @return a new list of strings padded to equal length
     */
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

    /**
     * Solves the Gold variant of the Trash Compactor problem.
     * <p>
     * Processes the input column by column from right to left, combining digits
     * into numbers and applying operators as indicated in the last row. Supports
     * numbers spanning multiple columns.
     * </p>
     *
     * @param input list of strings representing the problem input
     * @return the total result for the Gold problem
     */
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
