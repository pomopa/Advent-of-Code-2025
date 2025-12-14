package days.day09;

/**
 * Represents a line segment in 2D space.
 * <p>
 * Stores a line segment defined by two endpoints and provides a method
 * to check if a point lies on the line segment. The line is stored
 * in a normalized form so that {@code x, y} is the "start" point
 * and {@code dx, dy} are the differences to the end point.
 * </p>
 */
public class Line {
    /** X-coordinate of the start point. */
    long x;

    /** Y-coordinate of the start point. */
    long y;

    /** Difference in X from start to end point (endX - startX). */
    long dx;

    /** Difference in Y from start to end point (endY - startY). */
    long dy;

    /**
     * Constructs a line segment from two points (x1, y1) to (x2, y2).
     * <p>
     * Normalizes the line so that the start point is the smaller x-coordinate,
     * or if x-coordinates are equal, the smaller y-coordinate.
     * </p>
     *
     * @param x1 X-coordinate of the first point
     * @param y1 Y-coordinate of the first point
     * @param x2 X-coordinate of the second point
     * @param y2 Y-coordinate of the second point
     */
    Line(long x1, long y1, long x2, long y2) {
        if (x1 < x2 || (x1 == x2 && y1 <= y2)) {
            this.x = x1; this.y = y1;
            this.dx = x2 - x1;
            this.dy = y2 - y1;
        } else {
            this.x = x2; this.y = y2;
            this.dx = x1 - x2;
            this.dy = y1 - y2;
        }
    }

    /**
     * Checks if a point (px, py) lies on this line segment.
     * <p>
     * Supports only horizontal and vertical lines.
     * </p>
     *
     * @param px X-coordinate of the point to check
     * @param py Y-coordinate of the point to check
     * @return {@code true} if the point lies on the line segment, {@code false} otherwise
     */
    boolean isPointOn(long px, long py) {
        if (dx == 0) {
            return px == x && py >= y && py <= y + dy;
        }
        if (dy == 0) {
            return py == y && px >= x && px <= x + dx;
        }
        return false;
    }
}