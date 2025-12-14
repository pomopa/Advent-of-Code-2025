package ui;

import core.InputReader;
import core.Solver;
import core.SolverRegistry;
import core.TimeLogger;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Terminal-based user interface for selecting and executing problem solvers.
 * <p>
 * This class interacts with the user through the console, prompting for the day,
 * dataset type (test or full), and problem part (silver or gold). It reads the
 * corresponding input file, executes the solver, displays the result and execution
 * time, and logs the execution time to a file using {@link TimeLogger}.
 * </p>
 */
public class TerminalUI {

    /**
     * Starts the terminal user interface.
     * <p>
     * Prompts the user for input, executes the appropriate solver for the selected
     * day and part, prints the result and execution time, and logs the execution
     * time to "logs/executionTime.log".
     * </p>
     */
    public static void start() {
        Scanner sc = new Scanner(System.in);

        int day = SafeInput.readIntInRange(sc, "Day (1-12): ", 1, 12);
        String set = SafeInput.readChoice(sc, "Dataset? (test/full): ", "test", "full");
        int part = SafeInput.readIntInRange(sc, "Part? (1 = silver, 2 = gold): ", 1, 2);

        Solver solver = SolverRegistry.get(day);

        List<String> lines = InputReader.readLines(
                String.format("src/days/day%02d/Day%02dInput%s.txt",
                        day, day, set.equals("test") ? "Test" : "")
        );

        long startTime = System.nanoTime();
        long result = (part == 1)
                ? solver.solveSilver(lines)
                : solver.solveGold(lines);
        long endTime = System.nanoTime();

        System.out.println("Result: " + result);
        long duration = endTime - startTime;

        long seconds = TimeUnit.NANOSECONDS.toSeconds(duration);
        long milliseconds = TimeUnit.NANOSECONDS.toMillis(duration) % 1000;
        long microseconds = TimeUnit.NANOSECONDS.toMicros(duration) % 1000;
        long nanoseconds = duration % 1000;

        System.out.printf("Time: %d s : %d ms : %d µs : %d ns%n",
                seconds, milliseconds, microseconds, nanoseconds);
        TimeLogger.log("Day" + day, (part == 1) ? "Silver" : "Gold", duration);
    }
}

