package graphs;

import java.util.ArrayList;
import java.util.Iterator;

public interface Graph<V extends Vertex> {
    V addVertex();
    Edge<V> addEdge(V vertex1, V vertex2, int weight);

    boolean contains(V vertex);

    Edge<V> getEdge(V vertex1, V vertex2);

    default boolean connected(V vertex1, V vertex2){
        return getEdge(vertex1, vertex2)!=null;
    }
    default int weight(V vertex1, V vertex2){
        return getEdge(vertex1, vertex2).getWeight();
    }
    default boolean contains(Edge<V> edge) {
        Edge<V> foundEdge=getEdge(edge.getStart(), edge.getEnd());
        if(foundEdge==null){
            return false;
        } else {
            return foundEdge.getWeight()==edge.getWeight();
        }
    }

    Iterable<V> vertices();

    default void addMatrix(Integer[][] matrix){
        int verticesCount=matrix[0].length;
        ArrayList<V> vertices=new ArrayList<V>(verticesCount);
        for(int i=0; i<verticesCount; i++){
            vertices.add(addVertex());
        }
        for(int i=0; i<matrix.length; i++){
            for(int j=0; j<matrix.length; j++){
                if(matrix[i][j]!=null) {
                    addEdge(vertices.get(i), vertices.get(j), matrix[i][j]);
                }
            }
        }
    }

    /*default boolean defaultEquals(Object o){
        if (this == o) return true;
        if (!(o instanceof Edge)) return false;
        Graph<?> graph = (Graph<?>) o;
        for(Vertex v : this.vertices()){
            for(Vertex u : graph.vertices()){
                if(!v.equals(u))
            }
        }
    }*/

    default public Iterable<Edge<V>> edges() {
        return () -> new Iterator<Edge<V>>() {
            Iterator<V> verticesIterator = vertices().iterator();
            Iterator<Edge<?>> edgesIterator = null;

            void updateEdgesIterator(){
                if (edgesIterator == null || !edgesIterator.hasNext()) {
                    if (verticesIterator.hasNext()) {
                        edgesIterator = verticesIterator.next().edges().iterator();
                    } else {
                        edgesIterator=null;
                    }
                }
            }

            @Override
            public boolean hasNext() {
                updateEdgesIterator();
                return edgesIterator != null;
            }

            @Override
            @SuppressWarnings("unchecked")
            public Edge<V> next() {
                updateEdgesIterator();
                return (Edge<V>)edgesIterator.next();
            }
        };
    }
}
