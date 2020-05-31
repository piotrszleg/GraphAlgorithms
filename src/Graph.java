public interface Graph<V extends Vertex, E extends Edge<V>> {
    V addVertex();
    E addEdge(V vertex1, V vertex2, int weight);

    boolean contains(V vertex);

    E getEdge(V vertex1, V vertex2);

    default boolean connected(V vertex1, V vertex2){
        return getEdge(vertex1, vertex2)!=null;
    }
    default int weight(V vertex1, V vertex2){
        return getEdge(vertex1, vertex2).getWeight();
    }
    default boolean contains(E edge) {
        E foundEdge=getEdge(edge.getStart(), edge.getEnd());
        if(foundEdge==null){
            return false;
        } else {
            return foundEdge.getWeight()==edge.getWeight();
        }
    }

    Iterable<V> vertices();

    Iterable<E> edges();
}
