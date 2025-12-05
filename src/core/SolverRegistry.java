package core;

import days.day01.SecretEntrance;
import days.day02.GiftShop;
import days.day03.Lobby;

import java.util.HashMap;
import java.util.Map;

public class SolverRegistry {
    private static final Map<Integer, Solver> solvers = new HashMap<>();

    static {
        solvers.put(1, new SecretEntrance());
        solvers.put(2, new GiftShop());
        solvers.put(3, new Lobby());
    }

    public static Solver get(int day) {
        return solvers.get(day);
    }
}
