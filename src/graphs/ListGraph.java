package graphs;

import java.util.ArrayList;
import java.util.List;

public class ListGraph<I> implements Graph<ListVertex<I>, I> {
    private final ArrayList<ListVertex<I>> vertices = new ArrayList<>();

    public ListGraph() {
    }

    @Override
    public ListVertex<I> addVertex(I identifier) {
        ListVertex<I> vertex = new ListVertex<I>(identifier);
        this.vertices.add(vertex);
        return vertex;
    }

    @Override
    public Edge<ListVertex<I>> addEdge(ListVertex<I> start, ListVertex<I> end, int weight) {
        Edge<ListVertex<I>> edge = new Edge<>(start, end, weight);
        start.connect(end, weight);
        end.connect(start, weight);
        return edge;
    }

    public boolean contains(ListVertex<I> vertex) {
        for (ListVertex<I> checked : vertices) {
            if (checked.equals(vertex)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Edge<ListVertex<I>> getEdge(ListVertex<I> vertex1, ListVertex<I> vertex2) {
        return vertex1.getEdge(vertex2);
    }

    public Iterable<ListVertex<I>> vertices() {
        return this.vertices;
    }
}
