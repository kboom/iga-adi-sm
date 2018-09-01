package edu.iga.adi.sm.core.direction.productions.solution.backsubstitution;

import edu.iga.adi.sm.TestData;
import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.direction.Vertex;
import org.junit.Test;

import static edu.iga.adi.sm.core.Mesh.aMesh;
import static edu.iga.adi.sm.core.direction.Vertex.COLS_BOUND_TO_NODE;
import static edu.iga.adi.sm.core.direction.Vertex.ROWS_BOUND_TO_NODE;
import static java.util.Arrays.deepToString;
import static org.assertj.core.api.Assertions.assertThat;

public class BS_2_6Test {

    private static final int GRID_SIZE = 12;

    private Mesh DUMMY_MESH = aMesh()
            .withElementsX(GRID_SIZE)
            .withElementsY(GRID_SIZE)
            .withResolutionX(12d)
            .withResolutionY(12d)
            .withOrder(2).build();


    @Test
    public void backwardsSubstitutesToLeftChild() {
        final Vertex parent = node();
        final Vertex leftChild = node();
        final Vertex rightChild = node();

        join(parent, leftChild, rightChild);

        fillMatrices(parent);

        bs2_6At(parent).apply(parent);

        assertThat(deepToString(leftChild.m_x)).isEqualTo(
                "[[0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], [0.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0, 17.0], [0.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0, 17.0, 18.0], [0.0, -7.625, -8.75, -9.875, -11.0, -12.125, -13.25, -14.375, -15.5, -16.625, -17.75, -18.875, -20.0, -21.125, -22.25], [0.0, -36.25, -42.5, -48.75, -55.0, -61.25, -67.5, -73.75, -80.0, -86.25, -92.5, -98.75, -105.0, -111.25, -117.5], [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0]]");
    }

    @Test
    public void backwardsSubstitutesToRightChild() {
        final Vertex parent = node();
        final Vertex leftChild = node();
        final Vertex rightChild = node();

        join(parent, leftChild, rightChild);

        fillMatrices(parent);

        bs2_6At(parent).apply(parent);

        assertThat(deepToString(rightChild.m_x)).isEqualTo(
                "[[0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], [0.0, -7.625, -8.75, -9.875, -11.0, -12.125, -13.25, -14.375, -15.5, -16.625, -17.75, -18.875, -20.0, -21.125, -22.25], [0.0, -36.25, -42.5, -48.75, -55.0, -61.25, -67.5, -73.75, -80.0, -86.25, -92.5, -98.75, -105.0, -111.25, -117.5], [0.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0, 17.0, 18.0, 19.0], [0.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0, 17.0, 18.0, 19.0, 20.0], [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0]]");
    }

    private void fillMatrices(Vertex node) {
        node.m_a = TestData.generate2DMatrix(ROWS_BOUND_TO_NODE, COLS_BOUND_TO_NODE);
        node.m_b = TestData.generate2DMatrixFor(DUMMY_MESH);
        node.m_x = TestData.generate2DMatrixFor(DUMMY_MESH);
    }

    private Vertex node() {
        return Vertex.aVertex()
                .withMesh(DUMMY_MESH)
                .build();
    }

    private BS_2_6 bs2_6At(Vertex leafNode) {
        return new BS_2_6(leafNode, DUMMY_MESH);
    }

    private void join(Vertex parent, Vertex leftChild, Vertex rightChild) {
        parent.leftChild = leftChild;
        parent.rightChild = rightChild;
    }

}