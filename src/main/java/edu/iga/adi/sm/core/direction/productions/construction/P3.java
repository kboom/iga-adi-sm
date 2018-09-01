package edu.iga.adi.sm.core.direction.productions.construction;

import edu.iga.adi.sm.core.direction.Vertex;
import edu.iga.adi.sm.core.direction.productions.Production;

public class P3 extends Production {

    public P3(Vertex node) {
        super(node);
    }

    public Vertex apply(Vertex node) {
        setLeftChild(node);
        setMiddleChild(node);
        setRightChild(node);
        return node;
    }

    private void setLeftChild(Vertex node) {
        Vertex leftChild = Vertex.aVertex()
                .withMesh(node.mesh)
                .withBeginning(node.beginning)
                .withEnding(node.beginning + (node.ending - node.beginning) / 3.0)
                .build();
        node.setLeftChild(leftChild);
        leftChild.setParent(node);
    }

    private void setMiddleChild(Vertex node) {
        Vertex middleChild = Vertex.aVertex()
                .withMesh(node.mesh)
                .withBeginning(node.beginning + (node.ending - node.beginning) / 3.0)
                .withEnding(node.ending - (node.ending - node.beginning) / 3.0)
                .build();
        node.setMiddleChild(middleChild);
        middleChild.setParent(node);
    }

    private void setRightChild(Vertex node) {
        Vertex rightChild = Vertex.aVertex()
                .withMesh(node.mesh)
                .withBeginning(node.beginning + (node.ending - node.beginning) * 2.0 / 3.0)
                .withEnding(node.ending)
                .build();
        node.setRightChild(rightChild);
        rightChild.setParent(node);
    }

}