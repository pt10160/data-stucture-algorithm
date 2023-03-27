package graphs.shortestpaths;

import graphs.Edge;
import graphs.Graph;

import java.util.*;

/**
 * Topological sorting implementation of the {@link ShortestPathSolver} interface for <b>directed acyclic graphs</b>.
 *
 * @param <V> the type of vertices.
 * @see ShortestPathSolver
 */
public class ToposortDAGSolver<V> implements ShortestPathSolver<V> {
    private final Map<V, Edge<V>> edgeTo;
    private final Map<V, Double> distTo;

    /**
     * Constructs a new instance by executing the toposort-DAG-shortest-paths algorithm on the graph from the start.
     *
     * @param graph the input graph.
     * @param start the start vertex.
     */
    public ToposortDAGSolver(Graph<V> graph, V start) {
        edgeTo = new HashMap<>();
        distTo = new HashMap<>();
        // TODO: Replace with your code

        Set<V> visited = new HashSet<>();
        List<V> order = new ArrayList<>();
        dfsPostOrder(graph, start, visited, order);
        Collections.reverse(order);

        edgeTo.put(start, null);
        distTo.put(start, 0.0);

        for (V node : order) {
            List<Edge<V>> neighbors = graph.neighbors(node);
            for(Edge<V> e: neighbors){
                V to = e.to;
                double oldDist = distTo.getOrDefault(to, Double.POSITIVE_INFINITY);
                double newDist = distTo.get(node) + e.weight;
                if (newDist < oldDist) {
                    edgeTo.put(to, e);
                    distTo.put(to, newDist);
                }
            }
        }
        // throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Recursively adds nodes from the graph to the result in DFS postorder from the start vertex.
     *
     * @param graph   the input graph.
     * @param start   the start vertex.
     * @param visited the set of visited vertices.
     * @param result  the destination for adding nodes.
     */
    private void dfsPostOrder(Graph<V> graph, V start, Set<V> visited, List<V> result) {
        // TODO: Replace with your code
        if (!visited.contains(start)){
            visited.add(start);
            for (Edge<V> neighbor: graph.neighbors(start)) {
                V neighborV = neighbor.to;
                dfsPostOrder(graph, neighborV, visited, result);
            }
            result.add(start);
        }

        // throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<V> solution(V goal) {
        List<V> path = new ArrayList<>();
        V curr = goal;
        path.add(curr);
        while (edgeTo.get(curr) != null) {
            curr = edgeTo.get(curr).from;
            path.add(curr);
        }
        Collections.reverse(path);
        return path;
    }
}
