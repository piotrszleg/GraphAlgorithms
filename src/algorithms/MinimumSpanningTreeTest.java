package algorithms;

import graphs.Edge;
import graphs.Graph;
import graphs.ListGraph;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class MinimumSpanningTreeTest {

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

    @Test
    void generate() {
        ListGraph<Character> graph=new ListGraph<>();
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
        ListGraph<Character> expected=new ListGraph<>();
        expected.addMatrix(letters, new Integer[][]{
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
        ListGraph<Character> result=new ListGraph<>();
        MinimumSpanningTree.generate(graph, result);
        expected.prettyPrint();
        result.prettyPrint();
        assertEquals(sumWeights(expected), sumWeights(result));
        // assertTrue(Graph.equivalent(expected, result));
    }
}