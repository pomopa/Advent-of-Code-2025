package days.day09;

import core.Solver;

import java.util.List;

public class MovieTheater implements Solver {
    private record Point(long x, long y) {}
    private Point[] parsePoints(List<String> input) {
        int n = input.size();
        Point[] pts = new Point[n];

        for (int i = 0; i < n; i++) {
            String[] p = input.get(i).split(",");
            pts[i] = new Point(
                    Long.parseLong(p[0]),
                    Long.parseLong(p[1])
            );
        }
        return pts;
    }

    private long calculateArea(Point a, Point b) {
        return (Math.abs(a.x - b.x) + 1) * (Math.abs(a.y - b.y) + 1);
    }

    @Override
    public long solveSilver(List<String> input) {
        Point[] pts = parsePoints(input);
        long area = 0;

        for (int i = 0; i < pts.length; i++) {
            for (int j = i + 1; j < pts.length; j++) {
                long temp = calculateArea(pts[i], pts[j]);
                if (temp > area) {
                    area = temp;
                }
            }
        }

        return area;
    }

    @Override
    public long solveGold(List<String> input) {
        return 0;
    }
}
