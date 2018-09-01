package edu.iga.adi.sm.core.direction.productions.construction;

import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.direction.Vertex;
import edu.iga.adi.sm.core.direction.productions.Production;

import static edu.iga.adi.sm.core.direction.Vertex.aVertex;

abstract class P1 extends Production {

    P1(Vertex node) {
        super(node);
    }

    protected abstract int getElementCount(Mesh mesh);

    public final Vertex apply(Vertex node) {
        setLeftChild(node);
        setRightChild(node);
        return node;
    }

    private void setLeftChild(Vertex node) {
        Vertex leftChild = aVertex()
                .withMesh(node.mesh)
                .withBeginning(0)
                .withEnding(getElementCount(node.mesh) / 2.0)
                .build();
        node.setLeftChild(leftChild);
        leftChild.setParent(node);
    }

    private void setRightChild(Vertex node) {
        Vertex rightChild = aVertex()
                .withMesh(node.mesh)
                .withBeginning(getElementCount(node.mesh) / 2.0)
                .withEnding(getElementCount(node.mesh))
                .build();
        node.setRightChild(rightChild);
        rightChild.setParent(node);
    }

}
