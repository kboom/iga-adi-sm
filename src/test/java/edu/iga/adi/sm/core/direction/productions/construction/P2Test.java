package edu.iga.adi.sm.core.direction.productions.construction;

import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.direction.Vertex;
import org.junit.Test;

import static edu.iga.adi.sm.core.Mesh.aMesh;
import static org.assertj.core.api.Assertions.assertThat;

public class P2Test {

    private static final int GRID_SIZE = 12;

    private Mesh DUMMY_MESH = aMesh()
            .withElementsX(GRID_SIZE)
            .withElementsY(GRID_SIZE)
            .withResolutionX(12d)
            .withResolutionY(12d)
            .withOrder(2).build();

    @Test
    public void attachesLeftChild() {
        Vertex root = createRoot(1, 2);
        p2At(root).apply(root);
        assertThat(root.leftChild).isNotNull();
    }

    @Test
    public void attachesRightChild() {
        Vertex root = createRoot(1, 2);
        p2At(root).apply(root);
        assertThat(root.rightChild).isNotNull();
    }

    @Test
    public void leftBoundaryOfLeftChildIsAtTheLeftBoundaryOfTheParentNode() {
        Vertex root = createRoot(1, 2);
        p2At(root).apply(root);
        assertThat(root.leftChild.beginning).isEqualTo(root.beginning);
    }

    @Test
    public void rightBoundaryOfRightChildIsAtTheRightBoundaryOfTheParentNode() {
        Vertex root = createRoot(1, 99);
        p2At(root).apply(root);
        assertThat(root.rightChild.ending).isEqualTo(99);
    }

    @Test
    public void rightBoundaryOfTheLeftChildIsAtTheMiddleOfTheParentBoundary() {
        Vertex root = createRoot(1, 10);
        p2At(root).apply(root);
        assertThat(root.leftChild.ending).isEqualTo(5.5);
    }

    @Test
    public void leftBoundaryOfTheRightChildIsAtTheMiddleOfTheParentBoundary() {
        Vertex root = createRoot(1, 25);
        p2At(root).apply(root);
        assertThat(root.rightChild.beginning).isEqualTo(13);
    }

    private Vertex createRoot(double begin, double end) {
        return Vertex.aVertex()
                .withMesh(DUMMY_MESH)
                .withBeginning(begin)
                .withEnding(end)
                .build();
    }

    private P2 p2At(Vertex root) {
        return new P2(root);
    }

}