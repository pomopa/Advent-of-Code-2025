package core;

import days.day01.SecretEntrance;

import java.util.HashMap;
import java.util.Map;

public class SolverRegistry {
    private static final Map<Integer, Solver> solvers = new HashMap<>();

    static {
        solvers.put(1, new SecretEntrance());
    }

    public static Solver get(int day) {
        return solvers.get(day);
    }
}
