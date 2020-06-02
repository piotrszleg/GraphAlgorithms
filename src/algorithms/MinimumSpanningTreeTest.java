package algorithms;

import graphs.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.HashSet;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(Parameterized.class)
class MinimumSpanningTreeTest {

    @SuppressWarnings("unchecked")
    static Stream<Graph<?, Character>[]> graphs(){
        ListGraph<Character>[] listGraphArguments=new ListGraph[2];
        listGraphArguments[0]=new ListGraph<Character>();
        listGraphArguments[1]=new ListGraph<Character>();
        MatrixGraph<Character>[] matrixGraphArguments=new MatrixGraph[2];
        matrixGraphArguments[0]=new MatrixGraph<Character>();
        matrixGraphArguments[1]=new MatrixGraph<Character>();
        return Stream.of(listGraphArguments, matrixGraphArguments);
    }

    int sumWeights(Graph<?, ?> graph){
        HashSet<Edge<?>> visited=new HashSet<>();
        int sum=0;
        for(Edge<?> edge : graph.edges()){
            if(!visited.contains(edge)) {
                sum += edge.getWeight();
                visited.add(edge);
            }
        }
        return sum;
    }

    @ParameterizedTest
    @MethodSource("graphs")
    <V extends Vertex<Character>> void generate(Graph<V, Character> graph, Graph<V, Character> result) {
        Character[] letters=new Character[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
        graph.addMatrix(letters, new Integer[][]{
                /*    a,    b,    c,    d,    e,    f,    g,    h */
                /*a*/{null, 4,    null, 2,    3,    null, null, null},
                /*b*/{4,    null, 2,    null, 3,    8,    null, 4   },
                /*c*/{null, 2,    null, null, null, 9,    null, null},
                /*d*/{2,    null, null, null, null, null, 5,    null},
                /*e*/{3,    3,    null, null, null, null, 5,    1   },
                /*f*/{null, 8,    9,    null, null, null, null, 7   },
                /*g*/{null, null, null, 5,    5,    null, null, 6   },
                /*h*/{null, 4,    null, null, 1,    7,    6,    null},
        });
        ListGraph<Character> exampleResult=new ListGraph<>();
        exampleResult.addMatrix(letters, new Integer[][]{
                /*    a,    b,    c,    d,    e,    f,    g,    h   */
                /*a*/{null, null, null, 2,    3,    null, null, null},
                /*b*/{null, null, 2,    null, 3,    null, null, null},
                /*c*/{null, 2,    null, null, null, null, null, null},
                /*d*/{2,    null, null, null, null, null, 5,    null},
                /*e*/{3,    3,    null, null, null, null, null, 1   },
                /*f*/{null, null, null, null, null, null, null, 7   },
                /*g*/{null, null, null, 5,    null, null, null, null},
                /*h*/{null, null, null, null, 1,    7,    null, null},
        });
        MinimumSpanningTree.generate(graph, result);
        assertEquals(sumWeights(exampleResult), sumWeights(result));
    }
}