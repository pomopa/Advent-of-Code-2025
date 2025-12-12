package ui;

import java.util.InputMismatchException;
import java.util.Scanner;

public class SafeInput {

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
