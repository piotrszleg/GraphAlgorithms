package algorithms;

import graphs.Edge;
import graphs.Graph;
import graphs.Vertex;

import java.util.*;

public class ShortestPath {
    static class Marking<V> {
        V previous;
        float distance;
        V vertex;

        public Marking(V previous, float distance, V vertex) {
            this.previous = previous;
            this.distance = distance;
            this.vertex=vertex;
        }
    }
    @SuppressWarnings("unchecked")
    public static <I, V extends Vertex<I>> List<V> shortestPath(Graph<V, I> graph, V start, V end) {
        HashMap<V, Marking<V>> markings=new HashMap<>();
        TreeSet<Marking<V>> Q=new TreeSet<>(Comparator.comparing((Marking<V> marking)->marking.distance));
        for(V vertex : graph.vertices()){
            Marking<V> marking;
            if(vertex!=start) {
                if (graph.connected(start, vertex)) {
                    marking=new Marking<>(null, graph.weight(start, vertex), vertex);
                } else {
                    marking=new Marking<>(null, Float.POSITIVE_INFINITY, vertex);
                }
                Q.add(marking);
            } else {
                marking=new Marking<>(null, 0, vertex);
            }
            markings.put(vertex, marking);
        }

        while(!Q.isEmpty()){
            Marking<V> uMarking=Q.first();
            Q.remove(uMarking);
            V u=uMarking.vertex;
            for(Edge<?> edge : u.edges()){
                V v=(V)edge.getEnd();
                if(v==u){
                    // in case the edge is flipped for some reason
                    v=(V)edge.getStart();
                }
                if(Q.contains(markings.get(v))) {
                    float newDistance = markings.get(u).distance + edge.getWeight();
                    if (newDistance < markings.get(v).distance) {
                        markings.put(v, new Marking<>(u, newDistance, v));
                    }
                }
            }
        }
        LinkedList<V> result=new LinkedList<>();
        V current=end;
        while (current!=start && current!=null){
            result.addFirst(current);
            current=markings.get(current).previous;
        }
        return result;
    }
}
