package days.day11;

import core.Solver;

import java.util.*;

/**
 * Solver for Day 11: Reactor problem.
 * <p>
 * Implements the {@link core.Solver} interface, providing solutions for both Silver and Gold challenges.
 * The problem involves navigating a directed graph of reactor modules:
 * - Silver: Count all paths from the starting node "you" to "out" without revisiting nodes.
 * - Gold: Count all paths from "svr" to "out" that pass through both "fft" and "dac" modules, using memoization for efficiency.
 * </p>
 */
public class Reactor implements Solver {

    /**
     * Solves the Silver variant of the Reactor problem.
     * <p>
     * Parses the input into a directed graph and counts all distinct paths
     * from the starting node "you" to the node "out" using DFS.
     * </p>
     *
     * @param input list of strings, each representing a node and its outgoing edges in the format "node: neighbor1 neighbor2 ..."
     * @return total number of distinct paths from "you" to "out"
     */
    @Override
    public long solveSilver(List<String> input) {
        Map<String, List<String>> graph = parseInput(input);
        return dfsCount("you", graph, new HashSet<>());
    }

    // Must consider that test input for silver problem and gold problem are different
    /**
     * Solves the Gold variant of the Reactor problem.
     * <p>
     * Counts all paths from "svr" to "out" that pass through both "fft" and "dac" nodes.
     * Uses memoization to avoid recalculating paths for repeated states.
     * </p>
     *
     * @param input list of strings representing the reactor graph, similar format to Silver
     * @return number of valid paths from "svr" to "out" visiting both "fft" and "dac"
     */
    @Override
    public long solveGold(List<String> input) {
        Map<String, List<String>> graph = parseInput(input);
        Map<String, Long> memo = new HashMap<>();
        return countSpecial("svr", graph, false, false, memo);
    }

    /**
     * Parses input strings into a graph representation.
     *
     * @param input list of strings describing edges in the format "node: neighbor1 neighbor2 ..."
     * @return map representing the adjacency list of the graph
     */
    private Map<String, List<String>> parseInput(List<String> input) {
        Map<String, List<String>> graph = new HashMap<>();
        for (String line : input) {
            if (line == null || line.trim().isEmpty()) continue;

            String[] parts = line.split(":", 2);
            String node = parts[0].trim();
            List<String> outs = new ArrayList<>();

            if (parts.length > 1) {
                String rhs = parts[1].trim();
                if (!rhs.isEmpty()) {
                    for (String t : rhs.split("\\s+")) {
                        if (!t.isEmpty()) outs.add(t.trim());
                    }
                }
            }
            graph.put(node, outs);
        }
        return graph;
    }

    /**
     * Depth-First Search (DFS) to count all paths from a node to "out".
     * Avoids revisiting nodes to prevent cycles.
     *
     * @param node    current node
     * @param graph   adjacency list of the graph
     * @param visited set of already visited nodes
     * @return number of paths from current node to "out"
     */
    private long dfsCount(String node, Map<String, List<String>> graph, Set<String> visited) {
        if (node.equals("out")) return 1L;
        if (visited.contains(node)) return 0L;

        visited.add(node);
        long total = 0;
        for (String nb : graph.getOrDefault(node, Collections.emptyList())) {
            total += dfsCount(nb, graph, visited);
        }
        visited.remove(node);
        return total;
    }

    /**
     * Counts all paths from a node to "out" that pass through both "fft" and "dac".
     * Uses memoization to store intermediate results and improve efficiency.
     *
     * @param node    current node
     * @param graph   adjacency list
     * @param hasFft  whether "fft" has been visited in the current path
     * @param hasDac  whether "dac" has been visited in the current path
     * @param memo    memoization map storing previously computed results for states
     * @return number of valid paths from current node to "out" visiting both "fft" and "dac"
     */
    private long countSpecial(String node, Map<String, List<String>> graph, boolean hasFft, boolean hasDac, Map<String, Long> memo) {
        boolean newFft = hasFft || node.equals("fft");
        boolean newDac = hasDac || node.equals("dac");

        String key = node + "|" + newFft + "|" + newDac;
        if (memo.containsKey(key)) return memo.get(key);

        if (node.equals("out")) {
            long result = (newFft && newDac) ? 1L : 0L;
            memo.put(key, result);
            return result;
        }

        List<String> neighbors = graph.getOrDefault(node, Collections.emptyList());
        if (neighbors.isEmpty()) {
            memo.put(key, 0L);
            return 0L;
        }

        long total = 0;
        for (String nb : neighbors) {
            total += countSpecial(nb, graph, newFft, newDac, memo);
        }

        memo.put(key, total);
        return total;
    }
}
