import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

import static  org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.runners.Parameterized;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;

@RunWith(Parameterized.class)
class GraphTest {
    static Stream<Graph> graphs(){
        return Stream.of(new ListGraph());
    }

    @ParameterizedTest
    @MethodSource("graphs")
    void AddVertexTest(Graph graph) {
        Vertex vertex = graph.addVertex();
        assertTrue(graph.contains(vertex));
    }

    @Test
    void VerticesTest() {
        ListGraph graph = new ListGraph();
        ListVertex vertex1 = graph.addVertex();
        ListVertex vertex2 = graph.addVertex();
        ListVertex vertex3 = graph.addVertex();
        HashSet<ListVertex> set = new HashSet<ListVertex>();

        for(ListVertex vertex : graph.vertices()) {
            assertTrue(graph.contains(vertex));
            set.add(vertex);
        }
        assertEquals(3, set.size());
    }

    @Test
    void AddEdgeTest() {
        ListGraph graph = new ListGraph();
        ListVertex vertex1 = graph.addVertex();
        ListVertex vertex2 = graph.addVertex();
        graph.addEdge(vertex1, vertex2, 11);
        assertTrue(graph.connected(vertex1, vertex2));
        assertEquals(11, graph.weight(vertex1, vertex2));
    }

    @Test
    void EdgesTest() {
        ListGraph graph = new ListGraph();
        ListVertex vertex1 = graph.addVertex();
        ListVertex vertex2 = graph.addVertex();
        ListVertex vertex3 = graph.addVertex();
        graph.addEdge(vertex1, vertex2, 10);
        graph.addEdge(vertex2, vertex3, 5);
        graph.addEdge(vertex3, vertex1, 2);
        HashSet<ListEdge> set = new HashSet<ListEdge>();

        for(ListEdge edge : graph.edges()) {
            assertTrue(graph.contains(edge));
            set.add(edge);
        }
        assertEquals(6, set.size());
    }

    @Test
    void EdgesTwoSided(){
        ListGraph graph = new ListGraph();
        ListVertex vertex1 = graph.addVertex();
        ListVertex vertex2 = graph.addVertex();
        graph.addEdge(vertex1, vertex2, 10);
        ListEdge edge1=graph.getEdge(vertex1, vertex2);
        ListEdge edge2=graph.getEdge(vertex2, vertex1);
        assertEquals(edge1, edge2);
        assertEquals(edge2, edge1);
        assertEquals(edge1.hashCode(), edge2.hashCode());
    }
}
