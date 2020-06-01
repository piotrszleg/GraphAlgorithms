package graphs;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class MatrixVertex implements Vertex {
    @Override
    public Iterable<Edge<?>> edges() {
        MatrixVertex vertexThis=this;
        return ()->new Iterator<Edge<?>>() {
            int index=0;

            boolean skip(){
                for(; index<graph.vertices.length; index++){
                    if(graph.connected(vertexThis, new MatrixVertex(graph, index))){
                        return true;
                    }
                }
                return false;
            }

            @Override
            public boolean hasNext() {
                if(index>=graph.vertices.length){
                    return false;
                }
                return skip();
            }

            @Override
            public Edge<MatrixVertex> next() {
                if(hasNext()){
                    return graph.getEdge(vertexThis, new MatrixVertex(graph, index++));
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }

    MatrixGraph graph;
    int index;

    public MatrixVertex(MatrixGraph graph, int index) {
        this.graph=graph;
        this.index = index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MatrixVertex)) return false;
        MatrixVertex that = (MatrixVertex) o;
        return index == that.index &&
                Objects.equals(graph, that.graph);
    }

    @Override
    public int hashCode() {
        return Objects.hash(graph, index);
    }
}
