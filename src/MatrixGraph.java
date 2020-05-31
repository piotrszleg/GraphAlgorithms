import java.util.*;

public class MatrixGraph implements Graph<MatrixVertex>{

    // null represents vertices not being connected, otherwise it is their edge weight
    boolean[] vertices=new boolean[4];
    Integer[] matrix=new Integer[16];
    int verticesCounter=0;

    void resize(int newVerticesCount){
        Integer[] newMatrix=new Integer[newVerticesCount*newVerticesCount];
        // unset weights are null by default
        for(int x=0; x<vertices.length; x++){
            for(int y=0; y<vertices.length; y++) {
                newMatrix[x+y*newVerticesCount]=matrix[x+y*vertices.length];
            }
        }
        vertices= Arrays.copyOf(vertices, newVerticesCount);
        matrix=newMatrix;
    }

    @Override
    public MatrixVertex addVertex() {
        while(verticesCounter>=vertices.length){
            resize(vertices.length*2);
        }
        vertices[verticesCounter]=true;
        MatrixVertex result=new MatrixVertex(this, verticesCounter);
        verticesCounter++;
        return result;
    }

    @Override
    public Edge<MatrixVertex> addEdge(MatrixVertex vertex1, MatrixVertex vertex2, int weight) {
        matrix[vertex1.index+vertex2.index*vertices.length]=weight;
        matrix[vertex1.index*vertices.length+vertex2.index]=weight;
        return new Edge<>(vertex1, vertex2, weight);
    }

    @Override
    public boolean contains(MatrixVertex vertex) {
        return vertex.index<vertices.length && vertices[vertex.index];
    }

    @Override
    public Edge<MatrixVertex> getEdge(MatrixVertex vertex1, MatrixVertex vertex2) {
        int index=vertex1.index+vertex2.index*vertices.length;
        if(index>=matrix.length){
            return null;
        }
        Integer weight=matrix[index];
        if(weight!=null){
            return new Edge<>(vertex1, vertex2, weight);
        } else {
            return null;
        }
    }

    @Override
    public Iterable<MatrixVertex> vertices() {
        MatrixGraph graphThis=this;
        return ()->new Iterator<MatrixVertex>(){
            int index=0;
            @Override
            public boolean hasNext() {
                if(index>=vertices.length){
                    return false;
                }
                for(int i=index; i<vertices.length; i++){
                    if(vertices[i]) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public MatrixVertex next() {
                if(hasNext()) {
                    return new MatrixVertex(graphThis, index++);
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }
}
