package days.day10;

import core.Solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Factory implements Solver {
    @Override
    public long solveSilver(List<String> input) {
        long sum = 0;
        for (String line : input) sum += solveMachine(line);
        return sum;
    }

    @Override
    public long solveGold(List<String> input) {
        return 0;
    }

    private int solveMachine(String line) {
        int lb = line.indexOf('[');
        int rb = line.indexOf(']');
        String lights = line.substring(lb + 1, rb);
        int n = lights.length();

        int cb = line.indexOf('{');
        String buttonsPart = (cb >= 0) ? line.substring(rb + 1, cb) : line.substring(rb + 1);
        List<int[]> buttons = new ArrayList<>();
        int idx = 0;
        while (true) {
            int p1 = buttonsPart.indexOf('(', idx);
            if (p1 < 0) break;
            int p2 = buttonsPart.indexOf(')', p1);
            if (p2 < 0) break;

            String inside = buttonsPart.substring(p1 + 1, p2).trim();
            String[] parts = inside.split(",");
            int[] arr = new int[parts.length];
            for (int i = 0; i < parts.length; ++i) {
                arr[i] = Integer.parseInt(parts[i].trim());
            }

            buttons.add(arr);
            idx = p2 + 1;
        }

        int m = buttons.size();
        int[][] A = new int[n][m];
        int[] b = new int[n];
        for (int i = 0; i < n; ++i) {
            b[i] = (lights.charAt(i) == '#') ? 1 : 0;
        }
        for (int j = 0; j < m; ++j) {
            for (int pos : buttons.get(j)) {
                if (pos >= 0 && pos < n) {
                    A[pos][j] ^= 1;
                }
            }
        }

        int[][] M = new int[n][m];
        for (int i = 0; i < n; ++i) M[i] = Arrays.copyOf(A[i], m);
        int[] rhs = Arrays.copyOf(b, n);

        int row = 0;
        int[] pivotColForRow = new int[n];
        Arrays.fill(pivotColForRow, -1);

        for (int col = 0; col < m && row < n; ++col) {
            int sel = -1;
            for (int r = row; r < n; ++r) {
                if (M[r][col] == 1) { sel = r; break; }
            }
            if (sel == -1) continue;
            if (sel != row) {
                int[] tmp = M[sel]; M[sel] = M[row]; M[row] = tmp;
                int t = rhs[sel]; rhs[sel] = rhs[row]; rhs[row] = t;
            }
            pivotColForRow[row] = col;
            for (int r = 0; r < n; ++r) {
                if (r != row && M[r][col] == 1) {
                    for (int c = col; c < m; ++c) {
                        M[r][c] ^= M[row][c];
                    }
                    rhs[r] ^= rhs[row];
                }
            }
            row++;
        }

        int[] particular = new int[m];
        Arrays.fill(particular, 0);
        for (int r = 0; r < n; ++r) {
            int pc = pivotColForRow[r];
            if (pc != -1) {
                particular[pc] = rhs[r] & 1;
            }
        }

        List<Integer> freeCols = new ArrayList<>();
        boolean[] isPivotCol = new boolean[m];
        for (int r = 0; r < n; ++r) {
            if (pivotColForRow[r] != -1) isPivotCol[pivotColForRow[r]] = true;
        }
        for (int c = 0; c < m; ++c) if (!isPivotCol[c]) freeCols.add(c);

        int k = freeCols.size();
        int[][] basis = new int[k][m];
        for (int i = 0; i < k; ++i) {
            int fcol = freeCols.get(i);
            basis[i][fcol] = 1;
            for (int r = 0; r < n; ++r) {
                int pc = pivotColForRow[r];
                if (pc != -1) {
                    if (M[r][fcol] == 1) basis[i][pc] = 1;
                }
            }
        }

        int best = Integer.MAX_VALUE;
        int combos = 1 << k;
        for (int mask = 0; mask < combos; ++mask) {
            int weight = 0;
            int[] x = Arrays.copyOf(particular, m);
            int mm = mask;
            int bi = 0;
            while (mm != 0) {
                if ((mm & 1) != 0) {
                    int[] bv = basis[bi];
                    for (int c = 0; c < m; ++c) x[c] ^= bv[c];
                }
                bi++;
                mm >>>= 1;
            }
            for (int c = 0; c < m; ++c) if (x[c] == 1) weight++;
            if (weight < best) best = weight;
        }

        return best;
    }

}
