package edu.iga.adi.sm.core.direction.productions.construction;

import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.direction.Vertex;
import org.junit.Test;

import static edu.iga.adi.sm.core.Mesh.aMesh;
import static org.assertj.core.api.Assertions.assertThat;

public class P3Test {

    private static final int GRID_SIZE = 12;

    private Mesh DUMMY_MESH = aMesh()
            .withElementsX(GRID_SIZE)
            .withElementsY(GRID_SIZE)
            .withResolutionX(12d)
            .withResolutionY(12d)
            .withOrder(2).build();


    @Test
    public void attachesLeftChild() {
        Vertex root = anyRoot();
        p3At(root).apply(root);
        assertThat(root.leftChild).isNotNull();
    }

    @Test
    public void attachesMiddleChild() {
        Vertex root = anyRoot();
        p3At(root).apply(root);
        assertThat(root.middleChild).isNotNull();
    }

    @Test
    public void attachesRightChild() {
        Vertex root = anyRoot();
        p3At(root).apply(root);
        assertThat(root.rightChild).isNotNull();
    }

    @Test
    public void leftBoundaryOfLeftChildIsAtTheLeftBoundaryOfTheParentNode() {
        Vertex root = createRoot(5, 10);
        p3At(root).apply(root);
        assertThat(root.leftChild.beginning).isEqualTo(5);
    }

    @Test
    public void rightBoundaryOfRightChildIsAtTheRightBoundaryOfTheParentNode() {
        Vertex root = createRoot(1, 99);
        p3At(root).apply(root);
        assertThat(root.rightChild.ending).isEqualTo(99);
    }

    @Test
    public void rightBoundaryOfTheLeftChildIsAtOneThirdOfTheParentBoundary() {
        Vertex root = createRoot(1, 10);
        p3At(root).apply(root);
        assertThat(root.leftChild.ending).isEqualTo(4.0);
    }

    @Test
    public void leftBoundaryOfTheMiddleChildIsAtTheOneThirdOfTheParentBoundary() {
        Vertex root = createRoot(1, 25);
        p3At(root).apply(root);
        assertThat(root.middleChild.beginning).isEqualTo(9.0);
    }

    @Test
    public void rightBoundaryOfTheMiddleChildIsAtTwoThirdsOfTheParentBoundary() {
        Vertex root = createRoot(1, 10);
        p3At(root).apply(root);
        assertThat(root.middleChild.ending).isEqualTo(7.0);
    }

    @Test
    public void leftBoundaryOfTheRightChildIsAtTwoThirdsOfTheParentBoundary() {
        Vertex root = createRoot(3, 27);
        p3At(root).apply(root);
        assertThat(root.rightChild.beginning).isEqualTo(19.0);
    }

    private Vertex createRoot(double begin, double end) {
        return Vertex.aVertex()
                .withMesh(DUMMY_MESH)
                .withBeginning(begin)
                .withEnding(end)
                .build();
    }

    private Vertex anyRoot() {
        return createRoot(1, 2);
    }

    private P3 p3At(Vertex root) {
        return new P3(root);
    }

}