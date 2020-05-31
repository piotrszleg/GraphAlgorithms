import java.util.Objects;

public class ListEdge implements Edge<ListVertex> {
    private final ListVertex start;
    private final ListVertex end;
    private final int weight;

    public ListEdge(ListVertex start, ListVertex end, int weight) {
        this.start = start;
        this.end = end;
        this.weight=weight;
    }

    @Override
    public ListVertex getStart() {
        return start;
    }

    @Override
    public ListVertex getEnd() {
        return end;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListEdge)) return false;
        ListEdge listEdge = (ListEdge) o;

        boolean inOrder=Objects.equals(start, listEdge.start) &&
                Objects.equals(end, listEdge.end);

        boolean reversed=Objects.equals(start, listEdge.end) &&
                Objects.equals(end, listEdge.start);

        return weight == listEdge.weight && (inOrder || reversed);
    }

    @Override
    public int hashCode() {
        // addition is alternating so the order of start and end doesn't matter
        return Objects.hashCode(start)+Objects.hashCode(end)+Objects.hashCode(weight);
    }
}
