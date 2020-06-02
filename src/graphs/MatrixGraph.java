package graphs;

import java.util.*;

public class MatrixGraph<I> implements Graph<MatrixVertex<I>, I>{

    // null represents vertices not being connected, otherwise it is their edge weight
    I[] vertices=(I[])new Object[4];
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
    public MatrixVertex<I> addVertex(I identifer) {
        while(verticesCounter>=vertices.length){
            resize(vertices.length*2);
        }
        vertices[verticesCounter]=identifer;
        MatrixVertex<I> result=new MatrixVertex<I>(this, verticesCounter);
        verticesCounter++;
        return result;
    }

    @Override
    public Edge<MatrixVertex<I>> addEdge(MatrixVertex<I> vertex1, MatrixVertex<I> vertex2, int weight) {
        matrix[vertex1.index+vertex2.index*vertices.length]=weight;
        matrix[vertex1.index*vertices.length+vertex2.index]=weight;
        return new Edge<>(vertex1, vertex2, weight);
    }

    @Override
    public boolean contains(MatrixVertex<I> vertex) {
        return vertex.index<vertices.length && vertices[vertex.index]!=null;
    }

    @Override
    public Edge<MatrixVertex<I>> getEdge(MatrixVertex<I> vertex1, MatrixVertex<I> vertex2) {
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
    public Iterable<MatrixVertex<I>> vertices() {
        MatrixGraph<I> graphThis=this;
        return ()->new Iterator<MatrixVertex<I>>(){
            int index=0;
            @Override
            public boolean hasNext() {
                if(index>=vertices.length){
                    return false;
                }
                for(int i=index; i<vertices.length; i++){
                    if(vertices[i]!=null) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public MatrixVertex<I> next() {
                if(hasNext()) {
                    return new MatrixVertex<I>(graphThis, index++);
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }
}
