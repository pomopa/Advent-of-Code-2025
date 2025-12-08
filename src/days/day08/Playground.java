package days.day08;

import core.Solver;

import java.util.*;

public class Playground implements Solver {
    @Override
    public long solveSilver(List<String> input) {
        record Point(int x, int y, int z) {}
        int n = input.size();
        Point[] pts = new Point[n];

        for (int i = 0; i < n; i++) {
            String[] p = input.get(i).split(",");
            pts[i] = new Point(
                    Integer.parseInt(p[0]),
                    Integer.parseInt(p[1]),
                    Integer.parseInt(p[2])
            );
        }

        List<long[]> pairs = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                long dx = pts[i].x - pts[j].x;
                long dy = pts[i].y - pts[j].y;
                long dz = pts[i].z - pts[j].z;
                long d2 = dx * dx + dy * dy + dz * dz;
                pairs.add(new long[]{d2, i, j});
            }
        }

        pairs.sort(Comparator.comparingLong(a -> a[0]));
        DSU dsu = new DSU(n);
        int K = 1000; // Modify this value for the test case to 10
        int used = 0;
        int idx = 0;

        while (used < K && idx < pairs.size()) {
            long[] p = pairs.get(idx);
            dsu.union((int)p[1], (int)p[2]);
            used++;
            idx++;
        }

        Map<Integer, Integer> comp = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int r = dsu.find(i);
            comp.put(r, comp.getOrDefault(r, 0) + 1);
        }

        List<Integer> sizes = new ArrayList<>(comp.values());
        sizes.sort(Collections.reverseOrder());

        long a = sizes.get(0);
        long b = sizes.get(1);
        long c = sizes.get(2);

        return a * b * c;
    }


    @Override
    public long solveGold(List<String> input) {
        return 0;
    }
}
