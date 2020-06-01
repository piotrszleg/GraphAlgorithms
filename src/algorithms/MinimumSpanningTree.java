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

    public static <SV extends Vertex, DV extends Vertex> Graph<DV> generate(Graph<SV> graph, Graph<DV> result){
        HashMap<SV, Marking<SV, DV>> verticesSets=new HashMap<SV, Marking<SV, DV>>();
        for(SV vertex : graph.vertices()){
            Marking<SV, DV> marking =new Marking<>(vertex, result.addVertex());
            verticesSets.put(vertex, marking);
        }
        TreeSet<Edge<SV>> edges=new TreeSet<>();
        for(Edge<SV> edge : graph.edges()){
            edges.add(edge);
        }
        while(!edges.isEmpty()) {
            Edge<SV> edge=edges.first();
            edges.remove(edge);
            Marking<SV, DV> startMarking =verticesSets.get(edge.getStart());
            Marking<SV, DV> endMarking =verticesSets.get(edge.getEnd());
            if(!startMarking.set.equals(endMarking.set)){
                // sets union
                startMarking.set.addAll(endMarking.set);
                endMarking.set= startMarking.set;
                // add the edge to result graph
                result.addEdge(startMarking.correspondingVertex, endMarking.correspondingVertex, edge.getWeight());
            }
        }
        return result;
    }

    public static <V extends Vertex> ListGraph generate(Graph<V> graph){
        Graph<ListVertex> result=new ListGraph();
        return (ListGraph)generate(graph, result);
    }
}
