import java.util.Iterator;
import java.util.LinkedList;

public class ListVertex implements Vertex{

    class Connection {
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

    private ListEdge connectionToEdge(Connection connection){
        return new ListEdge(this, connection.neighbour, connection.weight);
    }

    public ListEdge getEdge(ListVertex other){
        for(Connection connection : connections){
            if(connection.neighbour==other){
                return connectionToEdge(connection);
            }
        }
        return null;
    }

    public Iterable<ListEdge> edges(){
        return new Iterable<ListEdge>() {
            @Override
            public Iterator<ListEdge> iterator() {
                return new Iterator<ListEdge>() {
                    Iterator<Connection> connectionsIterator =connections.iterator();
                    @Override
                    public boolean hasNext() {
                        return connectionsIterator.hasNext();
                    }

                    @Override
                    public ListEdge next() {
                        return connectionToEdge(connectionsIterator.next());
                    }
                };
            }
        };
    }

    public ListVertex(){

    }
}
