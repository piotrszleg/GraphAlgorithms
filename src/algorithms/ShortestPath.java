package algorithms;

import graphs.Edge;
import graphs.Graph;
import graphs.Vertex;

import java.util.*;

public class ShortestPath {
    static class Marking<V> {
        final V previous;
        final float distance;
        final V vertex;
        final int index;

        public Marking(V previous, float distance, V vertex, int index) {
            this.previous = previous;
            this.distance = distance;
            this.vertex=vertex;
            this.index=index;
        }
    }
    @SuppressWarnings("unchecked")
    public static <I, V extends Vertex<I>> List<V> shortestPath(Graph<V, I> graph, V start, V end) {
        HashMap<V, Marking<V>> markings=new HashMap<>();
        TreeSet<Marking<V>> Q=new TreeSet<>((Marking<V> a, Marking<V> b)->{
            float distanceDifference=a.distance-b.distance;
            if(distanceDifference!=0 && !Float.isNaN(distanceDifference))
                return (int)distanceDifference;
            else if(a==b)
                return 0;
             else
                 return a.index-b.index;
        });
        int index=0;
        for(V vertex : graph.vertices()){
            Marking<V> marking;
            if(!vertex.equals(start)) {
                if (graph.connected(start, vertex)) {
                    marking=new Marking<>(start, graph.weight(start, vertex), vertex, index++);
                } else {
                    marking=new Marking<>(null, Float.POSITIVE_INFINITY, vertex, index++);
                }
                Q.add(marking);
            } else {
                marking=new Marking<>(null, 0, vertex, index++);
            }
            markings.put(vertex, marking);
        }

        while(!Q.isEmpty()){
            Marking<V> uMarking=Q.first();
            Q.remove(uMarking);
            V u=uMarking.vertex;
            for(Edge<?> edge : u.edges()){
                V v=(V)edge.getEnd();
                if(v.equals(u)){
                    // in case the edge is flipped
                    v=(V)edge.getStart();
                }
                if(Q.contains(markings.get(v))) {
                    Marking<V> vMarking=markings.get(v);
                    float newDistance = markings.get(u).distance + edge.getWeight();
                    if (newDistance < vMarking.distance) {
                        Q.remove(vMarking);
                        Marking<V> newMarking=new Marking<>(u, newDistance, v, vMarking.index);
                        markings.put(v, newMarking);
                        Q.add(newMarking);
                    }
                }
            }
        }
        LinkedList<V> result=new LinkedList<>();
        V current=end;
        while (current!=null){
            result.addFirst(current);
            current=markings.get(current).previous;
        }
        return result;
    }
}
