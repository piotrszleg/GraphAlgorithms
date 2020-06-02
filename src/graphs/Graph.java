package graphs;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

public interface Graph<V extends Vertex<I>, I> {
    V addVertex(I identifier);
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

    void removeVertex(V vertex);
    void removeEdge(Edge<V> edge);

    Iterable<V> vertices();

    default void addMatrix(I[] identifiers, Integer[][] matrix){
        int verticesCount=matrix[0].length;
        ArrayList<V> vertices=new ArrayList<V>(verticesCount);
        for(int i=0; i<verticesCount; i++){
            vertices.add(addVertex(identifiers[i]));
        }
        for(int i=0; i<matrix.length; i++){
            for(int j=0; j<matrix.length; j++){
                if(matrix[i][j]!=null) {
                    addEdge(vertices.get(i), vertices.get(j), matrix[i][j]);
                }
            }
        }
    }

    static <V extends Vertex<I>, I> boolean equivalent(Graph<V, I> a, Graph<V, I> b){
        if (a == b) return true;
        int aEdgesCount=0;
        int bEdgesCount=0;

        for(Edge<V> aEdge : a.edges()){
            bEdgesCount=0;
            boolean equivalentFound=false;
            for(Edge<V> bEdge : b.edges()){
                if(aEdge.equivalent(bEdge)){
                    equivalentFound=true;
                    break;
                }
                bEdgesCount++;
            }
            if(!equivalentFound){
                return false;
            }
            aEdgesCount++;
        }
        // from the equivalency of edges comes equal vertices count
        return aEdgesCount!=bEdgesCount;
    }

    default public Iterable<Edge<V>> edges() {
        return () -> new Iterator<Edge<V>>() {
            Iterator<V> verticesIterator = vertices().iterator();
            Iterator<Edge<?>> edgesIterator = null;
            HashSet<Edge<?>> visited=new HashSet<>();
            Edge<V> next=null;

            void updateEdgesIterator(){
                if (edgesIterator == null || !edgesIterator.hasNext()) {
                    if (verticesIterator.hasNext()) {
                        edgesIterator = verticesIterator.next().edges().iterator();
                    } else {
                        edgesIterator=null;
                    }
                }
            }

            @SuppressWarnings("unchecked")
            Edge<V> fetchNext(){
                if(next!=null){
                    return next;
                }
                updateEdgesIterator();
                if(edgesIterator != null){
                    next=(Edge<V>)edgesIterator.next();
                    if(visited.contains(next)){
                        next=null;
                        return fetchNext();
                    } else {
                        visited.add(next);
                        return next;
                    }
                } else {
                    next=null;
                    return null;
                }
            }

            @Override
            public boolean hasNext() {
                return fetchNext()!=null;
            }

            @Override
            public Edge<V> next() {
                Edge<V> result=fetchNext();
                next=null;
                if(result!=null){
                    return result;
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }

    default V findVertex(I identifier){
        for(V vertex : vertices()){
            if(vertex.getIdentifier().equals(identifier)){
                return vertex;
            }
        }
        return null;
    }

    default void prettyPrint(){
        System.out.print("V={");
        for(Vertex<I> vertex : vertices()){
            System.out.print(vertex);
        }
        System.out.print("}\nE={");
        for(Edge<V> edge : edges()){
            System.out.print(edge);
        }
        System.out.println("}");
    }
}
