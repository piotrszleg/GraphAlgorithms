import java.util.ArrayList;
import java.util.Iterator;

public class ListGraph implements Graph<ListVertex, ListEdge> {
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
    public ListEdge addEdge(ListVertex start, ListVertex end, int weight) {
        ListEdge edge = new ListEdge(start, end, weight);
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
    public ListEdge getEdge(ListVertex vertex1, ListVertex vertex2) {
        return vertex1.getEdge(vertex2);
    }

    public Iterable<ListVertex> vertices() {
        return this.vertices;
    }

    public Iterable<ListEdge> edges() {
        return new Iterable<ListEdge>() {
            @Override
            public Iterator<ListEdge> iterator() {
                return new Iterator<ListEdge>() {
                    Iterator<ListVertex> verticesIterator=vertices.iterator();
                    Iterator<ListEdge> neighboursIterator=null;

                    @Override
                    public boolean hasNext() {
                        return neighboursIterator==null || neighboursIterator.hasNext() || verticesIterator.hasNext();
                    }

                    @Override
                    public ListEdge next() {
                        if(neighboursIterator==null || !neighboursIterator.hasNext()){
                            if(verticesIterator.hasNext()){
                                neighboursIterator=verticesIterator.next().edges().iterator();
                            } else {
                                return null;
                            }
                        }
                        return neighboursIterator.next();
                    }
                };
            }
        };
    }
}
