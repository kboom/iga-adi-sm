package edu.iga.adi.sm.core.direction;

import edu.iga.adi.sm.TestData;
import org.junit.Test;

import static edu.iga.adi.sm.TestData.emptyVertex;
import static edu.iga.adi.sm.core.Mesh.aMesh;
import static org.assertj.core.api.Assertions.assertThat;


public class VertexTest {

    @Test
    public void vertexIsAssignedSquare7x7m_aMatrixWith0Elements() {
        Vertex node = Vertex.aVertex().withBeginning(1).withEnding(2).withMesh(TestData.DUMMY_MESH).build();
        assertThat(node.m_a).isEqualTo(TestData.zeroed2DMatrix(7, 7));
    }

    @Test
    public void vertexIsAssignedBMatrixOfProperSize() {
        Vertex node = Vertex.aVertex().withBeginning(1).withEnding(2).withMesh(aMesh()
                .withElementsX(24)
                .withElementsY(24)
                .withResolutionX(12d)
                .withResolutionY(12d)
                .withOrder(2).build()).build();
        assertThat(node.m_b).isEqualTo(TestData.zeroed2DMatrix(7, 27));
    }

    @Test
    public void orderOfSplinesAffectsB_Matrix() {
        Vertex node = Vertex.aVertex().withBeginning(1).withEnding(2).withMesh(aMesh()
                .withElementsX(12)
                .withElementsY(12)
                .withResolutionX(12d)
                .withResolutionY(12d)
                .withOrder(3).build()).build();
        assertThat(node.m_b).isEqualTo(TestData.zeroed2DMatrix(7, 16));
    }

    @Test
    public void vertexIsAssignedXMatrixOfProperSize() {
        Vertex node = Vertex.aVertex().withBeginning(1).withEnding(2).withMesh(aMesh()
                .withElementsX(24)
                .withElementsY(24)
                .withResolutionX(12d)
                .withResolutionY(12d)
                .withOrder(2).build()).build();
        assertThat(node.m_x).isEqualTo(TestData.zeroed2DMatrix(7, 27));
    }

    @Test
    public void orderOfSplinesAffectsX_Matrix() {
        Vertex node = Vertex.aVertex().withBeginning(1).withEnding(2).withMesh(aMesh()
                .withElementsX(12)
                .withElementsY(12)
                .withResolutionX(12d)
                .withResolutionY(12d)
                .withOrder(3).build()).build();
        assertThat(node.m_x).isEqualTo(TestData.zeroed2DMatrix(7, 16));
    }

    @Test
    public void presentsChildrenInOrder() {
        Vertex node = emptyVertex();
        Vertex leftChild = emptyVertex();
        Vertex middleChild = emptyVertex();
        Vertex rightChild = emptyVertex();
        node.setLeftChild(leftChild);
        node.setMiddleChild(middleChild);
        node.setRightChild(rightChild);
        assertThat(node.getChildren()).containsExactly(leftChild, middleChild, rightChild);
    }

    @Test
    public void presentsParent() {
        Vertex parent = emptyVertex();
        Vertex child = emptyVertex();
        child.setParent(parent);
        assertThat(child.getParent()).isEqualTo(parent);
    }

}