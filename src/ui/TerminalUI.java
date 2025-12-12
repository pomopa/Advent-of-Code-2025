package ui;

import core.InputReader;
import core.Solver;
import core.SolverRegistry;
import core.TimeLogger;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class TerminalUI {
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

