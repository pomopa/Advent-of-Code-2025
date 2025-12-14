package ui;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Utility class for safely reading user input from the console.
 * <p>
 * Provides methods to read integers within a specific range and to read
 * string choices that match a set of accepted options. Handles invalid
 * input gracefully by prompting the user until valid input is entered.
 * </p>
 */
public class SafeInput {

    /**
     * Reads an integer from the user within a specified range.
     * <p>
     * Continuously prompts the user until a valid integer within the given
     * range {@code [min, max]} is entered. If the user enters a non-integer
     * or a number outside the range, an error message is displayed.
     * </p>
     *
     * @param sc     the {@link Scanner} object used to read input
     * @param prompt the message displayed to the user
     * @param min    the minimum acceptable integer value (inclusive)
     * @param max    the maximum acceptable integer value (inclusive)
     * @return a valid integer within the specified range
     */
    public static int readIntInRange(Scanner sc, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = sc.nextInt();
                if (value < min || value > max) {
                    System.out.println("Error: Value must be between " + min + " and " + max + ".");
                    continue;
                }
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid number.");
                sc.nextLine();
            }
        }
    }

    /**
     * Reads a string choice from the user that matches one of the accepted options.
     * <p>
     * Continuously prompts the user until a valid choice is entered. Comparison
     * is case-insensitive. If the input does not match any of the accepted options,
     * an error message is displayed showing the valid choices.
     * </p>
     *
     * @param sc       the {@link Scanner} object used to read input
     * @param prompt   the message displayed to the user
     * @param accepted the list of accepted string options
     * @return a valid choice string matching one of the accepted options
     */
    public static String readChoice(Scanner sc, String prompt, String... accepted) {
        while (true) {
            System.out.print(prompt);
            String value = sc.next().trim().toLowerCase();
            for (String option : accepted) {
                if (value.equals(option.toLowerCase())) {
                    return value;
                }
            }
            System.out.println("Error: Invalid option. Valid choices: " + String.join(", ", accepted));
        }
    }
}
