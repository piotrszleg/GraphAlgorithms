package graphs;

import java.util.*;

public class MatrixGraph<I> implements Graph<MatrixVertex<I>, I>{

    // null represents vertices not being connected, otherwise it is their edge weight
    @SuppressWarnings("unchecked")
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
    public MatrixVertex<I> addVertex(I identifier) {
        while(verticesCounter>=vertices.length){
            resize(vertices.length*2);
        }
        vertices[verticesCounter]=identifier;
        MatrixVertex<I> result= new MatrixVertex<>(this, verticesCounter);
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

    void checkDownsize(int index){
        // the gap needs to be big enough to not waste allocations
        if(index<vertices.length/4) {
            // there must be no elements after index
            for (int i = index; i < vertices.length; i++) {
                if (vertices[i] != null){
                    return;
                }
            }
            resize(index);
        }
    }

    @Override
    public void removeVertex(MatrixVertex<I> vertex) {
        // remove all edges containing vertex
        for(int i=0; i<vertices.length; i++){
            matrix[i+vertex.index*vertices.length]=null;
            matrix[vertex.index+i*vertices.length]=null;
        }
        vertices[vertex.index]=null;
        checkDownsize(vertex.index);
    }

    @Override
    public void removeEdge(Edge<MatrixVertex<I>> edge) {
        matrix[edge.getStart().index+edge.getEnd().index*vertices.length]=null;
        matrix[edge.getEnd().index+edge.getStart().index*vertices.length]=null;
    }

    @Override
    public Iterable<MatrixVertex<I>> vertices() {
        MatrixGraph<I> graphThis=this;
        return ()-> new Iterator<>() {
            int index = 0;

            @Override
            public boolean hasNext() {
                if (index >= vertices.length) {
                    return false;
                }
                for (int i = index; i < vertices.length; i++) {
                    if (vertices[i] != null) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public MatrixVertex<I> next() {
                if (hasNext()) {
                    return new MatrixVertex<>(graphThis, index++);
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }
}
