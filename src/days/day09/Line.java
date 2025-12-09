package days.day09;

public class Line {
    long x, y;
    long dx, dy;

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