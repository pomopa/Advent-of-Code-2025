package days.day09;

import core.Solver;

import java.util.ArrayList;
import java.util.List;

/**
 * Solver for Day 09: Movie Theater problem.
 * <p>
 * Implements the {@link core.Solver} interface, providing solutions for both
 * Silver and Gold variants. The problem involves computing maximum rectangular
 * areas defined by points, and checking containment/intersection within
 * horizontal and vertical line constraints.
 * </p>
 */
public class MovieTheater implements Solver {

    /**
     * Represents a 2D point with coordinates x and y.
     */
    private record Point(long x, long y) {}

    /**
     * Parses input strings into an array of {@link Point} objects.
     *
     * @param input list of strings in the format "x,y"
     * @return an array of {@link Point} objects
     */
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

    /**
     * Calculates the area of a rectangle defined by two points.
     * <p>
     * The rectangle is axis-aligned and includes both points, so 1 is added
     * to both width and height.
     * </p>
     *
     * @param a first point
     * @param b second point
     * @return the area of the rectangle
     */
    private long calculateArea(Point a, Point b) {
        return (Math.abs(a.x - b.x) + 1) * (Math.abs(a.y - b.y) + 1);
    }

    /**
     * Solves the Silver variant of the Movie Theater problem.
     * <p>
     * Computes all pairwise rectangles formed by points and returns the
     * maximum area among them.
     * </p>
     *
     * @param input list of strings representing 2D points
     * @return the largest rectangular area
     */
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

    /**
     * Builds line segments connecting consecutive points, forming a closed polygon.
     *
     * @param pts array of points
     * @return list of {@link Line} segments forming the polygon
     */
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

    /**
     * Converts two points into a {@link Line} segment.
     *
     * @param a first point
     * @param b second point
     * @return a normalized {@link Line} connecting a and b
     */
    private Line normLine(Point a, Point b) {
        return new Line(a.x, a.y, b.x, b.y);
    }

    /**
     * Checks whether a point (x, y) lies inside the polygon defined by horizontal
     * and vertical lines, using a point-in-polygon and edge check.
     *
     * @param hLines list of horizontal lines
     * @param vLines list of vertical lines
     * @param x      x-coordinate of the point
     * @param y      y-coordinate of the point
     * @return true if the point is inside or on the boundary, false otherwise
     */
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

    /**
     * Checks if two lines intersect.
     *
     * @param a first line
     * @param b second line
     * @return true if the lines intersect, false otherwise
     */
    private boolean intersects(Line a, Line b) {
        if (a.dy == 0) {
            return a.x < b.x && b.x < a.x + a.dx && b.y < a.y && a.y < b.y + b.dy;
        }
        return a.y < b.y && b.y < a.y + a.dy && b.x < a.x && a.x < b.x + b.dx;
    }

    /**
     * Solves the Gold variant of the Movie Theater problem.
     * <p>
     * Finds the largest rectangle fully contained within the polygon defined by points,
     * without intersecting any polygon edges.
     * </p>
     *
     * @param input list of strings representing 2D points
     * @return the area of the largest rectangle
     */
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
