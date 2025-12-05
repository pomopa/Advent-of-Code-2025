package days.day03;

import core.Solver;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class Lobby implements Solver {
    private long maxJoltageForLine(String line, int k) {
        int n = line.length();
        Deque<Character> stack = new ArrayDeque<>();
        int toRemove = n - k;

        for (char c : line.toCharArray()) {
            while (!stack.isEmpty() && toRemove > 0 && stack.peekLast() < c) {
                stack.pollLast();
                toRemove--;
            }
            stack.addLast(c);
        }

        while (stack.size() > k) {
            stack.pollLast();
        }

        StringBuilder sb = new StringBuilder(k);
        for (char c : stack) sb.append(c);

        return Long.parseLong(sb.toString());
    }

    @Override
    public long solveSilver(List<String> input) {
        long joltage = 0;

        for (String line : input) {
            joltage += maxJoltageForLine(line, 2);
        }

        return joltage;
    }

    @Override
    public long solveGold(List<String> input) {
        long joltage = 0;

        for (String line : input) {
            joltage += maxJoltageForLine(line, 12);
        }

        return joltage;
    }
}
