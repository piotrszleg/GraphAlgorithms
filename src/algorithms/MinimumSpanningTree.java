package algorithms;

import graphs.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;

public class MinimumSpanningTree {
    static class Marking<V extends Vertex<?>> {
        final V correspondingVertex;
        HashSet<V> set;
        public Marking(V vertex, V correspondingVertex){
            set=new HashSet<>();
            set.add(vertex);
            this.correspondingVertex=correspondingVertex;
        }
    }

    public static <I, V extends Vertex<I>> void generate(Graph<V, I> graph, Graph<V, I> result){
        HashMap<V, Marking<V>> verticesSets= new HashMap<>();
        for(V vertex : graph.vertices()){
            Marking<V> marking =new Marking<>(vertex, result.addVertex(vertex.getIdentifier()));
            verticesSets.put(vertex, marking);
        }
        TreeSet<Edge<V>> edges=new TreeSet<>();
        for(Edge<V> edge : graph.edges()){
            edges.add(edge);
        }
        while(!edges.isEmpty()) {
            Edge<V> edge=edges.first();
            edges.remove(edge);
            Marking<V> startMarking =verticesSets.get(edge.getStart());
            Marking<V> endMarking =verticesSets.get(edge.getEnd());
            if(!startMarking.set.equals(endMarking.set)){
                // sets union
                startMarking.set.addAll(endMarking.set);
                HashSet<V> oldEndMarkingSet=endMarking.set;
                for(V vertex : oldEndMarkingSet){
                    verticesSets.get(vertex).set=startMarking.set;
                }
                // add the edge to result graph
                result.addEdge(startMarking.correspondingVertex, endMarking.correspondingVertex, edge.getWeight());
            }
        }
    }
}
