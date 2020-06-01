package algorithms;

import graphs.ListGraph;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MinimumSpanningTreeTest {

    static int countIterable(Iterable<?> iterator){
        int counter=0;
        for(Object element : iterator){
            counter++;
        }
        return counter;
    }

    @Test
    void generate() {
        ListGraph graph=new ListGraph();
        graph.addMatrix(new Integer[][]{
                /*    a, b, c, d, e, f, g, h */
                /*a*/{null, 4, null, 2, 3, null, null, null},
                /*b*/{4, null, 2, null, 3, 8, null, 4},
                /*c*/{null, 2, null, null, null, 9, null, null},
                /*d*/{2, null, null, null, null, null, 5, null},
                /*e*/{3, 3, null, null, null, null, 5, 1},
                /*f*/{null, 8, 9, null, null, null, null, 7},
                /*g*/{null, null, null, 5, 5, null, null, 6},
                /*h*/{null, 4, null, 1, null, 7, 6, null},
        });
        ListGraph expected=new ListGraph();
        expected.addMatrix(new Integer[][]{
                /*    a, b, c, d, e, f, g, h */
                /*a*/{null, null, null, 2, 3, null, null, null},
                /*b*/{null, null, 2, null, 3, 8, null, 4},
                /*c*/{null, 2, null, null, null, null, null, null},
                /*d*/{2, null, null, null, null, null, 5, null},
                /*e*/{3, 3, null, null, null, null, null, 1},
                /*f*/{null, null, null, null, null, null, null, 7},
                /*g*/{null, null, null, 5, null, null, null, null},
                /*h*/{null, null, null, 1, null, 7, null, null},
        });
        ListGraph result=MinimumSpanningTree.generate(graph);
        assertEquals(countIterable(expected.vertices()), countIterable(result.vertices()));
        assertEquals(countIterable(expected.edges()), countIterable(result.edges()));
    }
}