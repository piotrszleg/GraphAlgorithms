package graphs;

import java.util.Iterator;
import java.util.LinkedList;

public class ListVertex<I> implements Vertex<I>{

    class Connection {
        final ListVertex<I> neighbour;
        final int weight;

        public Connection(ListVertex<I> neighbour, int weight) {
            this.neighbour = neighbour;
            this.weight = weight;
        }
    }

    private final LinkedList<Connection> connections=new LinkedList<>();
    private final I identifier;
    boolean directed;

    public void connect(ListVertex<I> other, int weight){
        connections.add(new Connection(other, weight));
    }

    private Edge<ListVertex<I>> connectionToEdge(Connection connection){
        return new Edge<>(this, connection.neighbour, connection.weight, directed);
    }

    public Edge<ListVertex<I>> getEdge(ListVertex<I> other){
        for(Connection connection : connections){
            if(connection.neighbour==other){
                return connectionToEdge(connection);
            }
        }
        return null;
    }

    void removeEdge(Edge<ListVertex<I>> edge){
        ListVertex<I> otherEnd=edge.otherEnd(this);
        connections.removeIf(connection -> connection.neighbour.equals(otherEnd));
    }

    @Override
    public Iterable<Edge<?>> edges(){
        return ()->new Iterator<>() {
            final Iterator<Connection> connectionsIterator =connections.iterator();
            @Override
            public boolean hasNext() {
                return connectionsIterator.hasNext();
            }

            @Override
            public Edge<?> next() {
                if(hasNext()) {
                    return connectionToEdge(connectionsIterator.next());
                } else {
                    return null;
                }
            }
        };
    }

    @Override
    public I getIdentifier() {
        return identifier;
    }

    public ListVertex(I identifier, boolean directed){
        this.identifier=identifier;
        this.directed=directed;
    }

    @Override
    public String toString() {
        return identifier.toString();
    }
}
