package edu.iga.adi.sm.core.direction.productions.construction;

import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.direction.Vertex;
import edu.iga.adi.sm.core.direction.productions.construction.P1x;
import org.junit.Test;

import static edu.iga.adi.sm.core.Mesh.aMesh;
import static org.assertj.core.api.Assertions.assertThat;

public class P1xTest {

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
        P1x p1 = new P1x(root);
        p1.apply(root);
        assertThat(root.leftChild).isNotNull();
    }

    @Test
    public void leftBoundaryOfLeftChildIsZero() {
        Vertex root = createRoot();
        P1x p1 = new P1x(root);
        p1.apply(root);
        assertThat(root.leftChild.beginning).isZero();
    }

    @Test
    public void rightBoundaryOfLeftChildIsHalfOfGridLengthAlongX() {
        Vertex root = createRoot();
        P1x p1 = new P1x(root);
        p1.apply(root);
        assertThat(root.leftChild.ending).isEqualTo(GRID_SIZE_X / 2.0);
    }

    @Test
    public void attachesRightChild() {
        Vertex root = createRoot();
        P1x p1 = new P1x(root);
        p1.apply(root);
        assertThat(root.rightChild).isNotNull();
    }

    @Test
    public void leftBoundaryOfRightChildIsHalfTheGridLengthAlongX() {
        Vertex root = createRoot();
        P1x p1 = new P1x(root);
        p1.apply(root);
        assertThat(root.rightChild.beginning).isEqualTo(GRID_SIZE_X / 2.0);
    }

    @Test
    public void rightBoundaryOfLeftChildIsTheGridLengthAlongX() {
        Vertex root = createRoot();
        P1x p1 = new P1x(root);
        p1.apply(root);
        assertThat(root.rightChild.ending).isEqualTo(GRID_SIZE_X);
    }

    private Vertex createRoot() {
        return Vertex.aVertex().withMesh(DUMMY_MESH).build();
    }

}