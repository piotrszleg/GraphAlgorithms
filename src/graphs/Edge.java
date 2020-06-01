package graphs;

import java.util.Objects;

public class Edge<V> implements Comparable<Edge> {
    private final V start;
    private final V end;
    private final int weight;

    public Edge(V start, V end, int weight) {
        this.start = start;
        this.end = end;
        this.weight=weight;
    }

    public V getStart() {
        return start;
    }

    public V getEnd() {
        return end;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Edge)) return false;
        Edge<V> listEdge = (Edge<V>) o;

        boolean inOrder=Objects.equals(start, listEdge.start) &&
                Objects.equals(end, listEdge.end);

        boolean reversed=Objects.equals(start, listEdge.end) &&
                Objects.equals(end, listEdge.start);

        return weight == listEdge.weight && (inOrder || reversed);
    }

    @Override
    public int hashCode() {
        // addition is alternating, so the order of start and end doesn't matter
        return Objects.hashCode(start)+Objects.hashCode(end)+Objects.hashCode(weight);
    }

    @Override
    public int compareTo(Edge o) {
        return this.weight-o.weight;
    }
}
