package days.day08;

import core.Solver;

import java.util.*;

/**
 * Solver for Day 08: Playground problem.
 * <p>
 * Implements the {@link core.Solver} interface, providing solutions for both
 * Silver and Gold variants. The problem involves analyzing 3D points, computing
 * distances, and using a Disjoint Set Union (DSU) structure to group points
 * or connect components.
 * </p>
 */
public class Playground implements Solver {

    /**
     * Represents a 3D point with coordinates x, y, z.
     */
    private record Point(int x, int y, int z) {}

    /**
     * Parses input strings into an array of {@link Point} objects.
     *
     * @param input list of strings in the format "x,y,z"
     * @return an array of {@link Point} objects
     */
    private Point[] parsePoints(List<String> input) {
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
        return pts;
    }

    /**
     * Computes all unique pairs of points along with their squared distances,
     * sorted by distance.
     *
     * @param pts array of points
     * @return list of arrays [distanceSquared, index1, index2], sorted by distance
     */
    private List<long[]> computeSortedPairs(Point[] pts) {
        int n = pts.length;
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
        return pairs;
    }

    /**
     * Solves the Silver variant of the Playground problem.
     * <p>
     * Selects the K shortest distance pairs and unions them using DSU.
     * Computes the sizes of connected components, sorts them, and returns
     * the product of the three largest component sizes.
     * </p>
     *
     * @param input list of strings representing 3D points
     * @return the product of the sizes of the three largest connected components
     */
    @Override
    public long solveSilver(List<String> input) {
        Point[] pts = parsePoints(input);
        List<long[]> pairs = computeSortedPairs(pts);
        int n = pts.length;
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

    /**
     * Solves the Gold variant of the Playground problem.
     * <p>
     * Iteratively connects points by increasing distance using DSU until all points
     * are in a single component. Returns the product of x-coordinates of the
     * last two points that connected the final components.
     * </p>
     *
     * @param input list of strings representing 3D points
     * @return the product of x-coordinates of the last connected points
     */
    @Override
    public long solveGold(List<String> input) {
        Point[] pts = parsePoints(input);
        List<long[]> pairs = computeSortedPairs(pts);
        int n = pts.length;
        DSU dsu = new DSU(n);
        int components = n;

        for (long[] p : pairs) {
            int a = (int)p[1];
            int b = (int)p[2];

            int ra = dsu.find(a);
            int rb = dsu.find(b);

            if (ra != rb) {
                dsu.union(ra, rb);
                components--;

                if (components == 1) {
                    return (long)pts[a].x * pts[b].x;
                }
            }
        }

        return 0;
    }

}
