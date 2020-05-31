import java.util.HashSet;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.runners.Parameterized;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;

@RunWith(Parameterized.class)
class GraphTest {
    static Stream<Graph<?>> graphs(){
        return Stream.of(new ListGraph(), new MatrixGraph());
    }

    @ParameterizedTest
    @MethodSource("graphs")
    <V extends Vertex> void addVertexTest(Graph<V> graph) {
        V vertex = graph.addVertex();
        assertTrue(graph.contains(vertex));
    }

    @ParameterizedTest
    @MethodSource("graphs")
    <V extends Vertex> void verticesTest(Graph<V> graph) {
        Vertex vertex1 = graph.addVertex();
        Vertex vertex2 = graph.addVertex();
        Vertex vertex3 = graph.addVertex();
        HashSet<Vertex> set = new HashSet<Vertex>();

        for(V vertex : graph.vertices()) {
            assertTrue(graph.contains(vertex));
            set.add(vertex);
        }
        assertEquals(3, set.size());
    }

    @ParameterizedTest
    @MethodSource("graphs")
    <V extends Vertex> void AddEdgeTest(Graph<V> graph) {
        V vertex1 = graph.addVertex();
        V vertex2 = graph.addVertex();
        graph.addEdge(vertex1, vertex2, 11);
        assertTrue(graph.connected(vertex1, vertex2));
        assertEquals(11, graph.weight(vertex1, vertex2));
    }

    @ParameterizedTest
    @MethodSource("graphs")
    <V extends Vertex> void EdgesTest(Graph<V> graph) {
        V vertex1 = graph.addVertex();
        V vertex2 = graph.addVertex();
        V vertex3 = graph.addVertex();
        graph.addEdge(vertex1, vertex2, 10);
        graph.addEdge(vertex2, vertex3, 5);
        graph.addEdge(vertex3, vertex1, 2);
        HashSet<Edge<V>> set = new HashSet<>();

        for(Edge<V> edge : graph.edges()) {
            assertTrue(graph.contains(edge));
            set.add(edge);
        }
        assertEquals(3, set.size());
    }

    @ParameterizedTest
    @MethodSource("graphs")
    <V extends Vertex> void EdgesTwoSided(Graph<V> graph){
        V vertex1 = graph.addVertex();
        V vertex2 = graph.addVertex();
        graph.addEdge(vertex1, vertex2, 10);
        Edge<V> edge1=graph.getEdge(vertex1, vertex2);
        Edge<V> edge2=graph.getEdge(vertex2, vertex1);
        assertEquals(edge1, edge2);
        assertEquals(edge2, edge1);
        assertEquals(edge1.hashCode(), edge2.hashCode());
    }
}
