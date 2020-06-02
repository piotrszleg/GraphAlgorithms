package graphs;

import java.util.Objects;

public class Edge<V extends Vertex<?>> implements Comparable<Edge<V>> {
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

    public boolean contains(V vertex){
        return start.equals(vertex) || end.equals(vertex);
    }

    public V otherEnd(V vertex){
        if(start.equals(vertex)) return end;
        else return start;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Edge)) return false;
        Edge<V> edge = (Edge<V>) o;

        boolean inOrder=Objects.equals(start, edge.start) &&
                Objects.equals(end, edge.end);

        boolean reversed=Objects.equals(start, edge.end) &&
                Objects.equals(end, edge.start);

        return weight == edge.weight && (inOrder || reversed);
    }

    public boolean equivalent(Edge<V> other){
        boolean inOrder=start.corresponds(other.start) &&
                end.corresponds(other.end);

        boolean reversed=end.corresponds(other.start) &&
                start.corresponds(other.end);

        return weight == other.weight && (inOrder || reversed);
    }

    @Override
    public int hashCode() {
        // addition is alternating, so the order of start and end doesn't matter
        return Objects.hashCode(start)+Objects.hashCode(end)+Objects.hashCode(weight);
    }

    @Override
    public int compareTo(Edge o) {
        int weightsDifference=this.weight-o.weight;
        if(weightsDifference!=0) {
            return weightsDifference;
        } else if(equals(o)) {
            return 0;
        } else {
            return -1;
        }
    }

    @Override
    public String toString() {
        return "("+start.toString()+", "+end.toString()+")";
    }
}
