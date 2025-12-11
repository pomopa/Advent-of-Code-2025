package days.day11;

import core.Solver;

import java.util.*;

public class Reactor implements Solver {
    @Override
    public long solveSilver(List<String> input) {
        Map<String, List<String>> graph = parseInput(input);
        return dfsCount("you", graph, new HashSet<>());
    }

    @Override
    public long solveGold(List<String> input) {
        return 0;
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
                    String[] targets = rhs.split("\\s+");
                    for (String t : targets) {
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
        long total = 0L;
        List<String> neighbors = graph.getOrDefault(node, Collections.emptyList());
        for (String nb : neighbors) {
            total += dfsCount(nb, graph, visited);
        }
        visited.remove(node);
        return total;
    }
}
