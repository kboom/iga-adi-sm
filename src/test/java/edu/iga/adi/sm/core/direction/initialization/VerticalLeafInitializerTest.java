package edu.iga.adi.sm.core.direction.initialization;

import edu.iga.adi.sm.TestData;
import edu.iga.adi.sm.core.direction.IntermediateSolution;
import edu.iga.adi.sm.core.direction.Vertex;
import edu.iga.adi.sm.core.direction.productions.Production;
import edu.iga.adi.sm.core.direction.productions.initialization.Ay;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static edu.iga.adi.sm.TestData.DUMMY_MESH;
import static edu.iga.adi.sm.TestData.nodeWithChildren;
import static edu.iga.adi.sm.core.direction.productions.initialization.ExplicitMethodCoefficients.EXPLICIT_METHOD_COEFFICIENTS;
import static org.assertj.core.api.Assertions.assertThat;

public class VerticalLeafInitializerTest {

    private final IntermediateSolution intermediateSolution = new IntermediateSolution(DUMMY_MESH, TestData.generate2DMatrixFor(DUMMY_MESH));
    private final VerticalLeafInitializer initializer = initializer(intermediateSolution);

    @Test
    public void providesThreeProductionsPerLeafNode() {
        final IntermediateSolution intermediateSolution = new IntermediateSolution(DUMMY_MESH, TestData.generate2DMatrixFor(DUMMY_MESH));
        final VerticalLeafInitializer initializer = initializer(intermediateSolution);

        ArrayList<Vertex> leaves = threeLeaves();

        assertThat(initializer.initializeLeaves(leaves)).hasSize(9);
    }

    @Test
    public void assignsSubsequentIndicesToProductions() {
        // when
        List<Production> productions = initializer.initializeLeaves(threeLeaves());

        // then
        assertThat(productions).extracting("idx").containsExactly(0, 1, 2, 3, 4, 5, 6, 7, 8);
    }

    @Test
    public void producesSequenceOfProductionsIntendedForSubsequentParentsChildren() {
        // given
        Vertex firstNode = nodeWithChildren();
        Vertex middleNode = nodeWithChildren();
        Vertex lastNode = nodeWithChildren();

        // when
        List<Production> productions = initializer.initializeLeaves(newArrayList(firstNode, middleNode, lastNode));

        // then
        assertThat(productions).extracting("node").containsExactly(
                firstNode.leftChild, firstNode.middleChild, firstNode.rightChild,
                middleNode.leftChild, middleNode.middleChild, middleNode.rightChild,
                lastNode.leftChild, lastNode.middleChild, lastNode.rightChild
        );
    }

    @Test
    public void assignsOnlyAyProductions() {
        // when
        List<Production> productions = initializer.initializeLeaves(threeLeaves());

        // then
        assertThat(productions).hasOnlyElementsOfType(Ay.class);
    }

    @Test
    public void producesSequenceOfValidPartitionsForSubsequentParentsChildren() {
        // when
        List<Production> productions = initializer.initializeLeaves(threeLeaves());

        // then
        assertThat(productions).extracting("partition").containsExactly(
                new double[]{1.0, 0.5, 0.3333333333333333}, new double[]{0.5, 0.3333333333333333, 0.3333333333333333}, new double[]{0.3333333333333333, 0.3333333333333333, 0.3333333333333333},
                new double[]{0.3333333333333333, 0.3333333333333333, 0.3333333333333333}, new double[]{0.3333333333333333, 0.3333333333333333, 0.3333333333333333}, new double[]{0.3333333333333333, 0.3333333333333333, 0.3333333333333333},
                new double[]{0.3333333333333333, 0.3333333333333333, 0.3333333333333333}, new double[]{0.3333333333333333, 0.3333333333333333, 0.5}, new double[]{0.3333333333333333, 0.5, 1.0}
        );
    }

    private VerticalLeafInitializer initializer(IntermediateSolution intermediateSolution) {
        return VerticalLeafInitializer.builder()
                .horizontalSolution(intermediateSolution)
                .methodCoefficients(EXPLICIT_METHOD_COEFFICIENTS)
                .mesh(DUMMY_MESH)
                .build();
    }

    private ArrayList<Vertex> threeLeaves() {
        return newArrayList(nodeWithChildren(), nodeWithChildren(), nodeWithChildren());
    }

}