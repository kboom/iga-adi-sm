package edu.iga.adi.sm.core.direction.initialization;

import edu.iga.adi.sm.TestData;
import edu.iga.adi.sm.core.direction.Vertex;
import edu.iga.adi.sm.core.direction.productions.Production;
import edu.iga.adi.sm.core.direction.productions.initialization.Ax;
import edu.iga.adi.sm.core.direction.productions.initialization.ExplicitMethodCoefficients;
import org.junit.Test;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static edu.iga.adi.sm.TestData.nodeWithChildren;
import static org.assertj.core.api.Assertions.assertThat;

public class HorizontalLeafInitializerTest {

    private HorizontalLeafInitializer horizontalLeafInitializer = HorizontalLeafInitializer.builder()
            .methodCoefficients(ExplicitMethodCoefficients.EXPLICIT_METHOD_COEFFICIENTS)
            .mesh(TestData.DUMMY_MESH)
            .problem((x, y) -> x + y)
            .build();

    @Test
    public void assignsOnlyAxProductions() {
        // when
        List<Production> productions = horizontalLeafInitializer.initializeLeaves(newArrayList(nodeWithChildren(), nodeWithChildren(), nodeWithChildren()));

        // then
        assertThat(productions).hasOnlyElementsOfType(Ax.class);
    }

    @Test
    public void assignsProductionsToSubsequentChildrenOfLeafNodesProvided() {
        // given
        Vertex firstNode = nodeWithChildren();
        Vertex middleNode = nodeWithChildren();
        Vertex lastNode = nodeWithChildren();

        // when
        List<Production> productions = horizontalLeafInitializer.initializeLeaves(newArrayList(firstNode, middleNode, lastNode));

        // then
        assertThat(productions).extracting("node").containsExactly(
                firstNode.leftChild, firstNode.middleChild, firstNode.rightChild,
                middleNode.leftChild, middleNode.middleChild, middleNode.rightChild,
                lastNode.leftChild, lastNode.middleChild, lastNode.rightChild
        );
    }

}