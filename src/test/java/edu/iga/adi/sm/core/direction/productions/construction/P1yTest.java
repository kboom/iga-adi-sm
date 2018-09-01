package edu.iga.adi.sm.core.direction.productions.construction;

import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.direction.Vertex;
import org.junit.Test;

import static edu.iga.adi.sm.core.Mesh.aMesh;
import static org.assertj.core.api.Assertions.assertThat;

public class P1yTest {

    private static final int GRID_SIZE_X = 12;
    private static final int GRID_SIZE_Y = 24;

    private Mesh DUMMY_MESH = aMesh()
            .withElementsX(GRID_SIZE_X)
            .withElementsY(GRID_SIZE_Y)
            .withResolutionX(12d)
            .withResolutionY(12d)
            .withOrder(2).build();


    @Test
    public void attachesLeftChild() {
        Vertex root = createRoot();
        P1y p1 = new P1y(root);
        p1.apply(root);
        assertThat(root.leftChild).isNotNull();
    }

    @Test
    public void leftBoundaryOfLeftChildIsZero() {
        Vertex root = createRoot();
        P1y p1 = new P1y(root);
        p1.apply(root);
        assertThat(root.leftChild.beginning).isZero();
    }

    @Test
    public void rightBoundaryOfLeftChildIsHalfOfGridLengthAlongY() {
        Vertex root = createRoot();
        P1y p1 = new P1y(root);
        p1.apply(root);
        assertThat(root.leftChild.ending).isEqualTo(GRID_SIZE_Y / 2.0);
    }

    @Test
    public void attachesRightChild() {
        Vertex root = createRoot();
        P1y p1 = new P1y(root);
        p1.apply(root);
        assertThat(root.rightChild).isNotNull();
    }

    @Test
    public void leftBoundaryOfRightChildIsHalfTheGridLengthAlongY() {
        Vertex root = createRoot();
        P1y p1 = new P1y(root);
        p1.apply(root);
        assertThat(root.rightChild.beginning).isEqualTo(GRID_SIZE_Y / 2.0);
    }

    @Test
    public void rightBoundaryOfLeftChildIsTheGridLengthAlongY() {
        Vertex root = createRoot();
        P1y p1 = new P1y(root);
        p1.apply(root);
        assertThat(root.rightChild.ending).isEqualTo(GRID_SIZE_Y);
    }

    private Vertex createRoot() {
        return Vertex.aVertex().withMesh(DUMMY_MESH).build();
    }

}