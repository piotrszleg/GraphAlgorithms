import java.util.ArrayList;

public class ListGraph implements Graph<ListVertex> {
    private final ArrayList<ListVertex> vertices = new ArrayList<ListVertex>();

    public ListGraph() {
    }

    @Override
    public ListVertex addVertex() {
        ListVertex vertex = new ListVertex();
        this.vertices.add(vertex);
        return vertex;
    }

    @Override
    public Edge<ListVertex> addEdge(ListVertex start, ListVertex end, int weight) {
        Edge<ListVertex> edge = new Edge<>(start, end, weight);
        start.connect(end, weight);
        end.connect(start, weight);
        return edge;
    }

    public boolean contains(ListVertex vertex) {
        for (ListVertex checked : vertices) {
            if (checked.equals(vertex)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Edge<ListVertex> getEdge(ListVertex vertex1, ListVertex vertex2) {
        return vertex1.getEdge(vertex2);
    }

    public Iterable<ListVertex> vertices() {
        return this.vertices;
    }
}
