package graphs;

import java.util.Objects;

public interface Vertex<I> {
    Iterable<Edge<?>> edges();
    I getIdentifier();

    default boolean corresponds(Vertex<I> other){
        return Objects.equals(getIdentifier(), other.getIdentifier());
    }
}
