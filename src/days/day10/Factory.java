package days.day10;

import core.Solver;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Must import into the project Ojango 56.1.1
import org.ojalgo.optimisation.ExpressionsBasedModel;
import org.ojalgo.optimisation.Variable;
import org.ojalgo.optimisation.Expression;
import org.ojalgo.optimisation.Optimisation;

public class Factory implements Solver {

    @Override
    public long solveSilver(List<String> input) {
        long sum = 0;
        for (String line : input) sum += solveMachineLights(line);
        return sum;
    }

    @Override
    public long solveGold(List<String> input) {
        long sum = 0;
        for (String line : input) sum += solveMachineJoltage(line);
        return sum;
    }

    private long solveMachineJoltage(String line) {
        int cb = line.indexOf('{');
        int ce = line.indexOf('}');
        String[] ts = line.substring(cb + 1, ce).split(",");
        int n = ts.length;
        long[] target = new long[n];
        for (int i = 0; i < n; i++) target[i] = Long.parseLong(ts[i].trim());

        List<int[]> buttons = new ArrayList<>();
        Matcher m = Pattern.compile("\\(([^)]*)\\)").matcher(line);
        while (m.find()) {
            String s = m.group(1).trim();
            if (s.isEmpty()) {
                buttons.add(new int[0]);
            } else {
                String[] parts = s.split(",");
                int[] arr = new int[parts.length];
                for (int i = 0; i < parts.length; i++) {
                    arr[i] = Integer.parseInt(parts[i].trim());
                }
                buttons.add(arr);
            }
        }
        int mcols = buttons.size();

        ExpressionsBasedModel model = new ExpressionsBasedModel();
        Variable[] x = new Variable[mcols];
        for (int j = 0; j < mcols; j++) {
            x[j] = model.addVariable("b" + j).lower(0).integer(true);
        }

        for (int i = 0; i < n; i++) {
            Expression expr = model.addExpression("c" + i).level(target[i]);
            for (int j = 0; j < mcols; j++) {
                int[] btn = buttons.get(j);
                int coeff = 0;
                for (int p : btn) if (p == i) coeff++;
                if (coeff != 0) expr.set(x[j], coeff);
            }
        }

        Expression obj = model.addExpression("objective");
        for (int j = 0; j < mcols; j++) {
            obj.set(x[j], 1);
        }
        obj.weight(1.0);

        Optimisation.Result res = model.minimise();
        if (!res.getState().isOptimal()) return -1L;

        long totalPresses = 0;
        for (int j = 0; j < mcols; j++) {
            int idx = model.indexOf(x[j]);
            long v = Math.round(res.doubleValue(idx));
            totalPresses += v;
        }
        return totalPresses;
    }

    private int solveMachineLights(String line) {
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
