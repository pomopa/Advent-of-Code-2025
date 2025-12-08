package core;

import days.day01.SecretEntrance;
import days.day02.GiftShop;
import days.day03.Lobby;
import days.day04.PrintingDepartment;
import days.day05.Cafeteria;
import days.day06.TrashCompactor;
import days.day07.Laboratories;
import days.day08.Playground;

import java.util.HashMap;
import java.util.Map;

public class SolverRegistry {
    private static final Map<Integer, Solver> solvers = new HashMap<>();

    static {
        solvers.put(1, new SecretEntrance());
        solvers.put(2, new GiftShop());
        solvers.put(3, new Lobby());
        solvers.put(4, new PrintingDepartment());
        solvers.put(5, new Cafeteria());
        solvers.put(6, new TrashCompactor());
        solvers.put(7, new Laboratories());
        solvers.put(8, new Playground());
    }

    public static Solver get(int day) {
        return solvers.get(day);
    }
}
