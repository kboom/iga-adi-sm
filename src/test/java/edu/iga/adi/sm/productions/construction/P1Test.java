package edu.iga.adi.sm.productions.construction;

import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.direction.Vertex;
import edu.iga.adi.sm.core.direction.productions.construction.P1;
import org.junit.Test;

import static edu.iga.adi.sm.core.Mesh.aMesh;
import static org.assertj.core.api.Assertions.assertThat;

public class P1Test {

    private static final int GRID_SIZE = 12;

    private Mesh DUMMY_MESH = aMesh()
            .withElementsX(GRID_SIZE)
            .withElementsY(GRID_SIZE)
            .withResolutionX(12d)
            .withResolutionY(12d)
            .withOrder(2).build();


    @Test
    public void attachesLeftChild() {
        Vertex root = createRoot();
        P1 p1 = new P1(root, DUMMY_MESH);
        p1.apply(root);
        assertThat(root.leftChild).isNotNull();
    }

    @Test
    public void leftBoundaryOfLeftChildIsZero() {
        Vertex root = createRoot();
        P1 p1 = new P1(root, DUMMY_MESH);
        p1.apply(root);
        assertThat(root.leftChild.beginning).isZero();
    }

    @Test
    public void rightBoundaryOfLeftChildIsHalfTheSizeOfGrid() {
        Vertex root = createRoot();
        P1 p1 = new P1(root, DUMMY_MESH);
        p1.apply(root);
        assertThat(root.leftChild.ending).isEqualTo(GRID_SIZE / 2);
    }

    @Test
    public void attachesRightChild() {
        Vertex root = createRoot();
        P1 p1 = new P1(root, DUMMY_MESH);
        p1.apply(root);
        assertThat(root.rightChild).isNotNull();
    }

    @Test
    public void leftBoundaryOfRightChildIsHalfTheSizeOfGrid() {
        Vertex root = createRoot();
        P1 p1 = new P1(root, DUMMY_MESH);
        p1.apply(root);
        assertThat(root.rightChild.beginning).isEqualTo(GRID_SIZE / 2);
    }

    @Test
    public void rightBoundaryOfLeftChildIsTheGridSize() {
        Vertex root = createRoot();
        P1 p1 = new P1(root, DUMMY_MESH);
        p1.apply(root);
        assertThat(root.rightChild.ending).isEqualTo(GRID_SIZE);
    }

    private Vertex createRoot() {
        return Vertex.aVertex().withMesh(DUMMY_MESH).build();
    }

}