package edu.iga.adi.sm.core.direction.productions.solution.factorization;

import edu.iga.adi.sm.TestData;
import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.direction.Vertex;
import org.junit.Test;

import static edu.iga.adi.sm.core.Mesh.aMesh;
import static edu.iga.adi.sm.core.direction.Vertex.aVertex;
import static java.util.Arrays.deepToString;
import static org.assertj.core.api.Assertions.assertThat;

public class M2_2_HTest {

    private static final int GRID_SIZE = 12;

    private Mesh DUMMY_MESH = aMesh()
            .withElementsX(GRID_SIZE)
            .withElementsY(GRID_SIZE)
            .withResolutionX(12d)
            .withResolutionY(12d)
            .withOrder(2).build();

    @Test
    public void canMerge() {
        Vertex parent = createParent();
        Vertex rightChild = createRightChild(parent);
        Vertex leftChild = createLeftChild(parent);

        leftChild.m_a = TestData.generate2DMatrixFor(DUMMY_MESH);
        rightChild.m_a[1][2] = 5;

        M2_2_H a = new M2_2_H(parent);
        a.apply(parent);
        assertThat(deepToString(parent.m_a)).isEqualTo(
                "[[0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], [0.0, 10.0, 11.0, 8.0, 9.0, 0.0, 0.0], [0.0, 11.0, 12.0, 9.0, 10.0, 0.0, 0.0], [0.0, 8.0, 9.0, 6.0, 7.0, 0.0, 0.0], [0.0, 9.0, 10.0, 7.0, 8.0, 0.0, 0.0], [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0]]"
        );
    }

    @Test
    public void swapsRHS() {
        Vertex parent = createParent();
        Vertex rightChild = createRightChild(parent);
        Vertex leftChild = createLeftChild(parent);

        leftChild.m_b[3][3] = 1;
        rightChild.m_b[3][3] = 1;

        M2_2_H a = new M2_2_H(parent);
        a.apply(parent);
        assertThat(deepToString(parent.m_b)).isEqualTo(
                "[[0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], [0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], [0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0]]"
        );
    }

    private Vertex createParent() {
        return aVertex().withMesh(DUMMY_MESH).build();
    }

    private Vertex createLeftChild(Vertex node) {
        Vertex leftChild = aVertex().withMesh(DUMMY_MESH).build();
        node.setLeftChild(leftChild);
        return leftChild;
    }

    private Vertex createRightChild(Vertex node) {
        Vertex rightChild = aVertex().withMesh(DUMMY_MESH).build();
        node.setRightChild(rightChild);
        return rightChild;
    }

}