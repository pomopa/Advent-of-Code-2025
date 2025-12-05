package ui;

import core.InputReader;
import core.Solver;
import core.SolverRegistry;

import java.util.List;
import java.util.Scanner;

public class TerminalUI {
    public static void start() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Day (1-25): ");
        int day = sc.nextInt();

        System.out.print("Dataset? (test/full): ");
        String set = sc.next();

        System.out.print("Part? (1=silver, 2=gold): ");
        int part = sc.nextInt();

        Solver solver = SolverRegistry.get(day);

        List<String> lines = InputReader.readLines(
                String.format("src/days/day%02d/Day%02dInput%s.txt",
                        day, day, set.equals("test") ? "Test" : "")
        );

        long result = (part == 1)
                ? solver.solveSilver(lines)
                : solver.solveGold(lines);

        System.out.println("Result: " + result);
    }
}

