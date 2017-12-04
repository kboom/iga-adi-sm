package edu.iga.adi.sm.core.direction.productions.construction;

import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.direction.Vertex;
import edu.iga.adi.sm.core.direction.productions.Production;

import static edu.iga.adi.sm.core.direction.Vertex.aVertex;

public class P2 extends Production {

    public P2(Vertex node, Mesh mesh) {
        super(node, mesh);
    }

    public Vertex apply(Vertex node) {
        setLeftChild(node);
        setRightChild(node);
        return node;
    }

    private void setLeftChild(Vertex node) {
        Vertex leftChild = aVertex()
                .withMesh(node.mesh)
                .withBeggining(node.beginning)
                .withEnding(node.beginning + (node.ending - node.beginning) * 0.5)
                .build();
        node.setLeftChild(leftChild);
        leftChild.setParent(node);
    }

    private void setRightChild(Vertex node) {
        Vertex rightChild = aVertex()
                .withMesh(node.mesh)
                .withBeggining(node.beginning + (node.ending - node.beginning) * 0.5)
                .withEnding(node.ending)
                .build();
        node.setRightChild(rightChild);
        rightChild.setParent(node);
    }

}
