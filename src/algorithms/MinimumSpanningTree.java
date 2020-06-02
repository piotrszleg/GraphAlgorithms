package algorithms;

import graphs.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;

public class MinimumSpanningTree {
    static class Marking<SV extends Vertex, DV extends Vertex> {
        DV correspondingVertex;
        HashSet<SV> set;
        public Marking(SV vertex, DV correspondingVertex){
            set=new HashSet<>();
            set.add(vertex);
            this.correspondingVertex=correspondingVertex;
        }
    }

    public static <I, V extends Vertex<I>> Graph<V, I> generate(Graph<V, I> graph, Graph<V, I> result){
        HashMap<V, Marking<V, V>> verticesSets=new HashMap<V, Marking<V, V>>();
        for(V vertex : graph.vertices()){
            Marking<V, V> marking =new Marking<>(vertex, result.addVertex(vertex.getIdentifier()));
            verticesSets.put(vertex, marking);
        }
        TreeSet<Edge<V>> edges=new TreeSet<>();
        for(Edge<V> edge : graph.edges()){
            edges.add(edge);
        }
        while(!edges.isEmpty()) {
            Edge<V> edge=edges.first();
            edges.remove(edge);
            Marking<V, V> startMarking =verticesSets.get(edge.getStart());
            Marking<V, V> endMarking =verticesSets.get(edge.getEnd());
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
        return result;
    }
}
