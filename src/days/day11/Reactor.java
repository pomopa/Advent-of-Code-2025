package days.day11;

import core.Solver;

import java.util.*;

public class Reactor implements Solver {

    @Override
    public long solveSilver(List<String> input) {
        Map<String, List<String>> graph = parseInput(input);
        return dfsCount("you", graph, new HashSet<>());
    }

    // Must consider that test input for silver problem and gold problem are different
    @Override
    public long solveGold(List<String> input) {
        Map<String, List<String>> graph = parseInput(input);
        Map<String, Long> memo = new HashMap<>();
        return countSpecial("svr", graph, false, false, memo);
    }

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
