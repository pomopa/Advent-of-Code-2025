package days.day09;

import core.Solver;

import java.util.ArrayList;
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

    private List<Line> buildLines(Point[] pts) {
        List<Line> lines = new ArrayList<>();
        int n = pts.length;

        for (int i = 1; i < n; i++) {
            Point a = pts[i - 1];
            Point b = pts[i];
            lines.add(normLine(a, b));
        }
        lines.add(normLine(pts[n - 1], pts[0]));

        return lines;
    }

    private Line normLine(Point a, Point b) {
        return new Line(a.x, a.y, b.x, b.y);
    }

    private boolean isInside(List<Line> hLines, List<Line> vLines, long x, long y) {
        for (Line l : vLines) if (l.isPointOn(x, y)) return true;
        for (Line l : hLines) if (l.isPointOn(x, y)) return true;

        int count = 0;
        for (Line h : hLines) {
            if (h.y < y && h.x <= x && x < h.x + h.dx) {
                count++;
            }
        }
        return (count % 2) == 1;
    }

    private boolean intersects(Line a, Line b) {
        if (a.dy == 0) {
            return a.x < b.x && b.x < a.x + a.dx && b.y < a.y && a.y < b.y + b.dy;
        }
        return a.y < b.y && b.y < a.y + a.dy && b.x < a.x && a.x < b.x + b.dx;
    }

    @Override
    public long solveGold(List<String> input) {
        Point[] pts = parsePoints(input);
        int n = pts.length;
        List<Line> lines = buildLines(pts);

        List<Line> hLines = new ArrayList<>();
        List<Line> vLines = new ArrayList<>();

        for (Line l : lines) {
            if (l.dy == 0) hLines.add(l);
            else if (l.dx == 0) vLines.add(l);
        }

        long best = 0;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {

                Point a = pts[i];
                Point b = pts[j];

                long area = calculateArea(a, b);
                if (area <= best) continue;

                long fromX = Math.min(a.x, b.x);
                long toX   = Math.max(a.x, b.x);
                long fromY = Math.min(a.y, b.y);
                long toY   = Math.max(a.y, b.y);

                Line top = new Line(fromX+1, fromY,   toX-1, fromY);
                Line bot = new Line(fromX+1, toY,     toX-1, toY);
                Line left = new Line(fromX, fromY+1,  fromX, toY-1);
                Line right= new Line(toX,   fromY+1,  toX,   toY-1);

                boolean ok = isInside(hLines, vLines, fromX, fromY)
                        && isInside(hLines, vLines, toX, fromY)
                        && isInside(hLines, vLines, toX, toY)
                        && isInside(hLines, vLines, fromX, toY);

                if (!ok) continue;

                for (Line v : vLines) {
                    if (intersects(v, top) || intersects(v, bot)) { ok = false; break; }
                }
                if (!ok) continue;

                for (Line h : hLines) {
                    if (intersects(h, left) || intersects(h, right)) { ok = false; break; }
                }
                if (!ok) continue;

                best = area;
            }
        }

        return best;
    }
}
