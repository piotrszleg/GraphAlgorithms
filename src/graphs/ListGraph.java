package graphs;

import java.util.ArrayList;

public class ListGraph<I> implements Graph<ListVertex<I>, I> {
    private final ArrayList<ListVertex<I>> vertices = new ArrayList<>();
    boolean directed;

    public ListGraph(Boolean directed){
        this.directed=directed;
    }

    public ListGraph() {
    }

    @Override
    public ListVertex<I> addVertex(I identifier) {
        ListVertex<I> vertex = new ListVertex<>(identifier, directed);
        this.vertices.add(vertex);
        return vertex;
    }

    @Override
    public Edge<ListVertex<I>> addEdge(ListVertex<I> start, ListVertex<I> end, int weight) {
        Edge<ListVertex<I>> edge = new Edge<>(start, end, weight, directed);
        start.connect(end, weight);
        if(!directed)
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

    @Override
    public void removeVertex(ListVertex<I> vertex) {
        for(Edge<ListVertex<I>> edge : edges()){
            if(edge.contains(vertex)){
                removeEdge(edge);
            }
        }
        vertices.remove(vertex);
    }

    @Override
    public void removeEdge(Edge<ListVertex<I>> edge) {
        edge.getStart().removeEdge(edge);
        edge.getEnd().removeEdge(edge);
    }

    public Iterable<ListVertex<I>> vertices() {
        return this.vertices;
    }

    @Override
    public boolean isDirected() {
        return directed;
    }
}
