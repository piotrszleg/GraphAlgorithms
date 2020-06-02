package graphs;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class MatrixVertex<I> implements Vertex<I> {
    @Override
    public Iterable<Edge<?>> edges() {
        MatrixVertex<I> vertexThis=this;
        return ()-> new Iterator<>() {
            int index = 0;

            boolean skip() {
                for (; index < graph.vertices.length; index++) {
                    if (graph.connected(vertexThis, new MatrixVertex<>(graph, index))) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public boolean hasNext() {
                if (index >= graph.vertices.length) {
                    return false;
                }
                return skip();
            }

            @Override
            public Edge<MatrixVertex<I>> next() {
                if (hasNext()) {
                    return graph.getEdge(vertexThis, new MatrixVertex<>(graph, index++));
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }

    final MatrixGraph<I> graph;
    final int index;

    public MatrixVertex(MatrixGraph<I> graph, int index) {
        this.graph=graph;
        this.index = index;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MatrixVertex)) return false;
        MatrixVertex<I> that = (MatrixVertex<I>) o;
        return index == that.index &&
                Objects.equals(graph, that.graph);
    }

    @Override
    public I getIdentifier() {
        return graph.vertices[index];
    }

    @Override
    public int hashCode() {
        return Objects.hash(graph, index);
    }

    @Override
    public String toString() {
        return graph.vertices[index].toString();
    }
}
