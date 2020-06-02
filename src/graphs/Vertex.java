package graphs;

import java.util.Objects;

public interface Vertex<I> {
    Iterable<Edge<?>> edges();
    I getIdentifier();

    default <V extends Vertex<?>> boolean corresponds(V other){
        return Objects.equals(getIdentifier(), other.getIdentifier());
    }
}
