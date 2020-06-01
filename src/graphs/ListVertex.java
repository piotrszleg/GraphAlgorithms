package graphs;

import java.util.Iterator;
import java.util.LinkedList;

public class ListVertex implements Vertex{

    static class Connection {
        final ListVertex neighbour;
        final int weight;

        public Connection(ListVertex neighbour, int weight) {
            this.neighbour = neighbour;
            this.weight = weight;
        }
    }

    private LinkedList<Connection> connections=new LinkedList<>();

    public void connect(ListVertex other, int weight){
        connections.add(new Connection(other, weight));
    }

    private Edge<ListVertex> connectionToEdge(Connection connection){
        return new Edge<>(this, connection.neighbour, connection.weight);
    }

    public Edge<ListVertex> getEdge(ListVertex other){
        for(Connection connection : connections){
            if(connection.neighbour==other){
                return connectionToEdge(connection);
            }
        }
        return null;
    }

    @Override
    public Iterable<Edge<?>> edges(){
        return ()->new Iterator<>() {
            Iterator<Connection> connectionsIterator =connections.iterator();
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

    public ListVertex(){

    }
}
