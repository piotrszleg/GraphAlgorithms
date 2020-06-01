package algorithms;

import graphs.ListGraph;
import graphs.ListVertex;
import graphs.Vertex;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShortestPathTest {

    @Test
    void shortestPath() {
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
        boolean first=true;
        ListVertex start=null;
        ListVertex end=null;
        for(ListVertex v : graph.vertices()){
            if(first){
                first=false;
                start=v;
            }
            end=v;
        }
        ShortestPath.shortestPath(graph, start, end);
    }
}