package edu.iga.adi.sm.core.direction.productions.construction;

import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.direction.Vertex;
import edu.iga.adi.sm.core.direction.productions.Production;

import static edu.iga.adi.sm.core.direction.Vertex.aVertex;

public class P1y extends Production {

    public P1y(Vertex node, Mesh mesh) {
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
                .withBeggining(0)
                .withEnding(node.mesh.getElementsY() / 2.0)
                .build();
        node.setLeftChild(leftChild);
        leftChild.setParent(node);
    }

    private void setRightChild(Vertex node) {
        Vertex rightChild = aVertex()
                .withMesh(node.mesh)
                .withBeggining(node.mesh.getElementsY() / 2.0)
                .withEnding(node.mesh.getElementsY())
                .build();
        node.setRightChild(rightChild);
        rightChild.setParent(node);
    }

}
