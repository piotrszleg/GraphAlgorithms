package algorithms;

import graphs.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(Parameterized.class)
class ShortestPathTest {

    static Stream<Graph<?, Character>> graphs(){
        return Stream.of(new ListGraph<>(), new MatrixGraph<>());
    }

    <V extends Vertex<I>, I> int pathDistance(Graph<V, I> graph, List<V> path){
        int result=0;
        V previous=null;
        for(V vertex : path){
            if(previous!=null) {
                result += graph.weight(previous, vertex);
            }
            previous=vertex;
        }
        return result;
    }

    @ParameterizedTest
    @MethodSource("graphs")
    <V extends Vertex<Character>> void shortestPath(Graph<V, Character> graph) {
        Character[] letters=new Character[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
        graph.addMatrix(
                letters,
                new Integer[][]{
                /*    a,    b,    c,    d,    e,    f,    g,    h    */
                /*a*/{null, 2,    5,    null, null, null, null, null},
                /*b*/{2,    null, null, 3,    4,    null, null, null},
                /*c*/{5,    null, null, 5,    null, 6,    null, null},
                /*d*/{null, 3,    5,    null, 3,    1,    null, null},
                /*e*/{null, 4,    null, 3,    null, 4,    8,    2   },
                /*f*/{null, null, 6,    1,    null, null, 7,    null},
                /*g*/{null, null, null, null, 8,    7,    null, 1   },
                /*h*/{null, null, null, null, 2,    null, 1,    null},
        });
        V start=graph.findVertex('a');
        BiConsumer<Character, Integer> checkDistance=(Character identifier, Integer expectedDistance)->{
            V end=graph.findVertex(identifier);
            List<V> path=ShortestPath.shortestPath(graph, start, end);
            assertEquals(expectedDistance, pathDistance(graph, path));
        };
        checkDistance.accept('c', 5);
        checkDistance.accept('g', 9);
        checkDistance.accept('d', 5);
        checkDistance.accept('f', 6);
    }
}