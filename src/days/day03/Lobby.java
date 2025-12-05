package days.day03;

import core.Solver;

import java.util.List;

public class Lobby implements Solver {
    @Override
    public long solveSilver(List<String> input) {
        long joltage = 0;

        for (String line : input) {
            int n = line.length();

            int[] maxRight = new int[n];
            int currMax = 0;

            for (int i = n - 1; i >= 0; i--) {
                int d = line.charAt(i) - '0';
                currMax = Math.max(currMax, d);
                maxRight[i] = currMax;
            }

            int best = 0;

            for (int i = 0; i < n - 1; i++) {
                int left = line.charAt(i) - '0';
                int right = maxRight[i + 1];

                int candidate = left * 10 + right;
                if (candidate > best) best = candidate;

                if (best == 99) break;
            }

            joltage += best;
        }

        return joltage;
    }

    @Override
    public long solveGold(List<String> input) {
        return 0;
    }
}
