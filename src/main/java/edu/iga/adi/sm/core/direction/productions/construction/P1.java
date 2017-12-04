package edu.iga.adi.sm.core.direction.productions.construction;

import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.direction.Vertex;
import edu.iga.adi.sm.core.direction.productions.Production;

public class P1 extends Production {

    public P1(Vertex node, Mesh mesh) {
        super(node, mesh);
    }

    public Vertex apply(Vertex node) {
        setLeftChild(node);
        setRightChild(node);
        return node;
    }

    private void setLeftChild(Vertex node) {
        Vertex leftChild = Vertex.aVertex()
                .withMesh(node.mesh)
                .withBeggining(0)
                .withEnding(node.mesh.getElementsX() / 2)
                .build();
        node.setLeftChild(leftChild);
        leftChild.setParent(node);
    }

    private void setRightChild(Vertex node) {
        Vertex rightChild = Vertex.aVertex()
                .withMesh(node.mesh)
                .withBeggining(node.mesh.getElementsX() / 2)
                .withEnding(node.mesh.getElementsX())
                .build();
        node.setRightChild(rightChild);
        rightChild.setParent(node);
    }

}
