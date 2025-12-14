package core;

import java.util.List;

/**
 * Interface for problem-solving classes for each day.
 * <p>
 * Defines methods to solve the two variants of each problem: "Silver" and "Gold".
 * Implementing classes provide specific logic for each variant.
 * </p>
 */
public interface Solver {
    /**
     * Solves the "Silver" variant of the problem using the provided input.
     *
     * @param input a {@link List} of {@link String} representing the input data
     * @return the result of the "Silver" problem as a {@code long}
     */
    long solveSilver(List<String> input);

    /**
     * Solves the "Gold" variant of the problem using the provided input.
     *
     * @param input a {@link List} of {@link String} representing the input data
     * @return the result of the "Gold" problem as a {@code long}
     */
    long solveGold(List<String> input);
}
