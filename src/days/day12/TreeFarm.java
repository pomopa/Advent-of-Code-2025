package days.day12;

import core.Solver;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Solver for Day 12: Tree Farm problem.
 * <p>
 * Implements the {@link core.Solver} interface, providing solutions for Silver and Gold challenges.
 * The problem involves placing tree-shaped blocks into rectangular regions while respecting constraints:
 * - Silver: Count how many regions can be fully fitted with the given shapes.
 * - Gold: Not implemented here (last problem, solution assumed to be achieving all stars).
 * </p>
 */
public class TreeFarm implements Solver {

    /**
     * Solves the Silver variant of the Tree Farm problem.
     * <p>
     * Parses the input to read shapes and rectangular regions, computes all possible orientations
     * of each shape (rotations and flips), and counts the number of regions that can be completely filled.
     * </p>
     *
     * @param input list of strings defining shapes and regions. Shapes are given in '#' and '.' format.
     *              Regions are given as "WxH: counts..." where counts correspond to the quantity of each shape.
     * @return number of regions that can be successfully filled with the given shapes
     */
    @Override
    public long solveSilver(List<String> input) {
        List<boolean[][]> shapes = new ArrayList<>();
        List<String> regionLines = new ArrayList<>();

        Pattern regionPattern = Pattern.compile("^(\\d+)x(\\d+):\\s*(.*)$");
        boolean readingShapes = true;
        List<String> buffer = new ArrayList<>();
        for (String raw : input) {
            String line = raw.strip();
            if (line.isEmpty()) {
                if (!buffer.isEmpty()) {
                    boolean[][] shape = bufferToShape(buffer);
                    shapes.add(shape);
                    buffer.clear();
                }
                continue;
            }
            Matcher m = regionPattern.matcher(line);
            if (m.matches()) {
                if (!buffer.isEmpty()) {
                    shapes.add(bufferToShape(buffer));
                    buffer.clear();
                }
                readingShapes = false;
                regionLines.add(line);
            } else if (readingShapes) {
                if (line.matches("^\\d+:\\s*$")) {
                    if (!buffer.isEmpty()) {
                        shapes.add(bufferToShape(buffer));
                        buffer.clear();
                    }
                } else {
                    buffer.add(line);
                }
            } else {
                regionLines.add(line);
            }
        }
        if (!buffer.isEmpty()) {
            shapes.add(bufferToShape(buffer));
            buffer.clear();
        }

        List<List<int[]>> shapeOrientations = new ArrayList<>();
        for (boolean[][] s : shapes) {
            shapeOrientations.add(generateOrientations(s));
        }

        int fitCount = 0;
        for (String rline : regionLines) {
            Matcher m = regionPattern.matcher(rline);
            if (!m.matches()) continue;
            int W = Integer.parseInt(m.group(1));
            int H = Integer.parseInt(m.group(2));
            String countsStr = m.group(3).trim();
            String[] toks = countsStr.split("\\s+");
            int[] counts = new int[shapes.size()];
            for (int i = 0; i < counts.length && i < toks.length; i++) {
                counts[i] = Integer.parseInt(toks[i]);
            }

            int totalCells = 0;
            for (int si = 0; si < shapes.size(); si++) {
                int shapeArea = shapeArea(shapes.get(si));
                totalCells += shapeArea * counts[si];
            }
            if (totalCells > W * H) {
                continue;
            }

            List<Integer> pieces = new ArrayList<>();
            for (int si = 0; si < counts.length; si++) {
                for (int c = 0; c < counts[si]; c++) pieces.add(si);
            }

            if (pieces.isEmpty()) {
                fitCount++;
                continue;
            }

            Map<Integer, List<int[]>> placementsByShape = new HashMap<>();
            for (int si = 0; si < shapes.size(); si++) {
                if (counts[si] == 0) continue;
                List<int[]> pl = computePlacementsForShape(shapeOrientations.get(si), W, H);
                placementsByShape.put(si, pl);
            }

            Integer[] pieceOrder = pieces.toArray(new Integer[0]);
            Arrays.sort(pieceOrder, Comparator.comparingInt(a -> placementsByShape.get(a).size()));

            boolean[] grid = new boolean[W * H];
            boolean success = backtrackPlace(0, pieceOrder, placementsByShape, grid);
            if (success) fitCount++;
        }

        return fitCount;
    }

    /**
     * Solves the Gold variant of the Tree Farm problem.
     * <p>
     * Not implemented; always returns 0.
     * </p>
     *
     * @param input same input format as Silver
     * @return 0
     */
    @Override
    public long solveGold(List<String> input) {
        // It's the last problem, the gold implementation consisted in having all stars!
        return 0;
    }

    /**
     * Converts a list of strings representing a shape into a 2D boolean array and trims empty borders.
     */
    private static boolean[][] bufferToShape(List<String> buffer) {
        int H = buffer.size();
        int W = buffer.get(0).length();
        boolean[][] s = new boolean[H][W];
        for (int y = 0; y < H; y++) {
            String row = buffer.get(y);
            for (int x = 0; x < W; x++) {
                char c = x < row.length() ? row.charAt(x) : '.';
                s[y][x] = (c == '#');
            }
        }

        return trimShape(s);
    }

    /**
     * Removes empty borders from a shape.
     */
    private static boolean[][] trimShape(boolean[][] s) {
        int H = s.length, W = s[0].length;
        int minX = W, minY = H, maxX = -1, maxY = -1;
        for (int y = 0; y < H; y++) {
            for (int x = 0; x < W; x++) {
                if (s[y][x]) {
                    if (x < minX) minX = x;
                    if (y < minY) minY = y;
                    if (x > maxX) maxX = x;
                    if (y > maxY) maxY = y;
                }
            }
        }
        if (maxX < 0) {
            return new boolean[][]{{}};
        }
        int newW = maxX - minX + 1;
        int newH = maxY - minY + 1;
        boolean[][] t = new boolean[newH][newW];
        for (int y = 0; y < newH; y++) {
            System.arraycopy(s[minY + y], minX, t[y], 0, newW);
        }
        return t;
    }

    /**
     * Counts the number of filled cells in a shape.
     */
    private static int shapeArea(boolean[][] s) {
        int count = 0;
        for (boolean[] booleans : s)
            for (int x = 0; x < s[0].length; x++)
                if (booleans[x]) count++;
        return count;
    }

    /**
     * Generates all distinct orientations (rotations + horizontal flips) of a shape.
     */
    private static List<int[]> generateOrientations(boolean[][] s) {
        Set<String> seen = new HashSet<>();
        List<int[]> out = new ArrayList<>();

        boolean[][] cur = s;
        for (int rot = 0; rot < 4; rot++) {
            boolean[][] r = rotate(cur);
            for (int flip = 0; flip < 2; flip++) {
                boolean[][] f = (flip == 0) ? r : flipHorizontal(r);
                boolean[][] t = trimShape(f);
                List<int[]> coords = new ArrayList<>();
                for (int y = 0; y < t.length; y++) {
                    for (int x = 0; x < t[0].length; x++) {
                        if (t[y][x]) coords.add(new int[]{x, y});
                    }
                }
                coords.sort(Comparator.comparingInt(a -> (a[1] * 1000 + a[0])));
                StringBuilder key = new StringBuilder();
                for (int[] p : coords) {
                    key.append(p[0]).append(',').append(p[1]).append(';');
                }
                String ks = key.toString();
                if (!seen.contains(ks)) {
                    seen.add(ks);
                    int[] arr = new int[coords.size() * 2];
                    for (int i = 0; i < coords.size(); i++) {
                        arr[2 * i] = coords.get(i)[0];
                        arr[2 * i + 1] = coords.get(i)[1];
                    }
                    out.add(arr);
                }
            }
            cur = r;
        }
        return out;
    }

    /**
     * Rotates a shape 90 degrees clockwise.
     *
     * @param s the original 2D boolean array representing the shape
     * @return a new 2D boolean array of the rotated shape
     */
    private static boolean[][] rotate(boolean[][] s) {
        int H = s.length, W = s[0].length;
        boolean[][] r = new boolean[W][H];
        for (int y = 0; y < H; y++) for (int x = 0; x < W; x++) r[x][H - 1 - y] = s[y][x];
        return r;
    }

    /**
     * Flips a shape horizontally (mirror along vertical axis).
     *
     * @param s the original 2D boolean array representing the shape
     * @return a new 2D boolean array of the flipped shape
     */
    private static boolean[][] flipHorizontal(boolean[][] s) {
        int H = s.length, W = s[0].length;
        boolean[][] f = new boolean[H][W];
        for (int y = 0; y < H; y++) for (int x = 0; x < W; x++) f[y][W - 1 - x] = s[y][x];
        return f;
    }

    /**
     * Computes all possible placements of a given shape (with its orientations) within a grid of size WxH.
     *
     * @param orientations list of shape coordinates in different orientations
     * @param W the width of the grid
     * @param H the height of the grid
     * @return list of placements, where each placement is an array of grid indices occupied by the shape
     */
    private static List<int[]> computePlacementsForShape(List<int[]> orientations, int W, int H) {
        List<int[]> placements = new ArrayList<>();
        for (int[] ori : orientations) {
            int maxX = 0, maxY = 0;
            for (int i = 0; i < ori.length; i += 2) {
                int x = ori[i], y = ori[i + 1];
                if (x > maxX) maxX = x;
                if (y > maxY) maxY = y;
            }
            int limitX = W - (maxX + 1);
            int limitY = H - (maxY + 1);
            if (limitX < 0 || limitY < 0) continue;
            for (int ox = 0; ox <= limitX; ox++) {
                for (int oy = 0; oy <= limitY; oy++) {
                    int[] cells = new int[ori.length / 2];
                    for (int i = 0, k = 0; i < ori.length; i += 2, k++) {
                        int x = ori[i] + ox;
                        int y = ori[i + 1] + oy;
                        cells[k] = x + y * W;
                    }
                    placements.add(cells);
                }
            }
        }
        return placements;
    }

    /**
     * Recursively attempts to place all remaining pieces on the grid using backtracking.
     *
     * @param idx current index in the pieceOrder array
     * @param pieceOrder array representing the order in which pieces are placed
     * @param placementsByShape map from shape index to list of possible placements
     * @param grid boolean array representing the occupied cells of the grid
     * @return true if all pieces can be successfully placed; false otherwise
     */
    private static boolean backtrackPlace(int idx, Integer[] pieceOrder, Map<Integer, List<int[]>> placementsByShape,
                                          boolean[] grid) {
        if (idx >= pieceOrder.length) return true; // all placed
        int shape = pieceOrder[idx];
        List<int[]> placements = placementsByShape.get(shape);

        for (int[] place : placements) {
            if (canPlace(place, grid)) {
                setPlace(place, grid, true);
                boolean ok = backtrackPlace(idx + 1, pieceOrder, placementsByShape, grid);
                if (ok) return true;
                setPlace(place, grid, false);
            }
        }
        return false;
    }

    /**
     * Checks if a set of cells can be placed on the grid without overlapping existing filled cells.
     *
     * @param cells array of grid indices representing the cells the shape would occupy
     * @param grid boolean array representing the occupied cells of the grid
     * @return true if all cells are free; false otherwise
     */
    private static boolean canPlace(int[] cells, boolean[] grid) {
        for (int c : cells) {
            if (grid[c]) return false;
        }
        return true;
    }

    /**
     * Marks or unmarks a set of cells on the grid.
     *
     * @param cells array of grid indices to mark or unmark
     * @param grid boolean array representing the occupied cells of the grid
     * @param val true to mark cells as occupied, false to unmark
     */
    private static void setPlace(int[] cells, boolean[] grid, boolean val) {
        for (int c : cells) grid[c] = val;
    }
}
