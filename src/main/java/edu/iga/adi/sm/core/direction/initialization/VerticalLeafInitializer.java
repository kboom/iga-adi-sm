package edu.iga.adi.sm.core.direction.initialization;

import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.Solution;
import edu.iga.adi.sm.core.direction.Vertex;
import edu.iga.adi.sm.core.direction.productions.Production;
import edu.iga.adi.sm.core.direction.productions.initialization.Ay;
import edu.iga.adi.sm.core.direction.productions.initialization.MethodCoefficients;
import lombok.Builder;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Builder
public class VerticalLeafInitializer implements LeafInitializer {

    public static final double[] FIRST_PARTITION = {1, 1. / 2, 1. / 3};
    public static final double[] SECOND_PARTITION = {1. / 2, 1. / 3, 1. / 3};
    public static final double[] THIRD_PARTITION = {1. / 3, 1. / 3, 1. / 3};

    public static final double[] MIDDLE_LEFT_PARTITION = {1. / 3, 1. / 3, 1. / 3};
    public static final double[] MIDDLE_CENTER_PARTITION = {1. / 3, 1. / 3, 1. / 3};
    public static final double[] MIDDLE_RIGHT_PARTITION = {1. / 3, 1. / 3, 1. / 3};

    public static final double[] THIRD_TO_LAST_PARTITION = {1. / 3, 1. / 3, 1. / 3};
    public static final double[] SECOND_TO_LAST_PARTITION = {1. / 3, 1. / 3, 1. / 2};
    public static final double[] LAST_PARTITION = {1. / 3, 1. / 2, 1};

    @NonNull
    private final Solution horizontalSolution;

    @NonNull
    private final Mesh mesh;

    @NonNull
    private final MethodCoefficients methodCoefficients;

    @Override
    public List<Production> initializeLeaves(List<Vertex> leafLevelVertices) {
        final int leafCount = leafLevelVertices.size();
        double[][] rhs = horizontalSolution.getRhs();
        List<Production> initializationProductions = new ArrayList<>(leafLevelVertices.size());

        Vertex firstVertex = leafLevelVertices.get(0);
        initializationProductions.add(initializeFrom(rhs).node(firstVertex.leftChild).idx(0).partition(FIRST_PARTITION).build());
        initializationProductions.add(initializeFrom(rhs).node(firstVertex.middleChild).idx(1).partition(SECOND_PARTITION).build());
        initializationProductions.add(initializeFrom(rhs).node(firstVertex.rightChild).idx(2).partition(THIRD_PARTITION).build());

        for (int i = 1; i < leafCount - 1; i++) {
            Vertex vertex = leafLevelVertices.get(i);
            initializationProductions.add(initializeFrom(rhs).node(vertex.leftChild).idx(3 * i).partition(MIDDLE_LEFT_PARTITION).build());
            initializationProductions.add(initializeFrom(rhs).node(vertex.middleChild).idx(3 * i + 1).partition(MIDDLE_CENTER_PARTITION).build());
            initializationProductions.add(initializeFrom(rhs).node(vertex.rightChild).idx(3 * i + 2).partition(MIDDLE_RIGHT_PARTITION).build());
        }

        Vertex lastVertex = leafLevelVertices.get(leafCount - 1);
        initializationProductions.add(initializeFrom(rhs).node(lastVertex.leftChild).idx(leafCount * 3 - 3).partition(THIRD_TO_LAST_PARTITION).build());
        initializationProductions.add(initializeFrom(rhs).node(lastVertex.middleChild).idx(leafCount * 3 - 2).partition(SECOND_TO_LAST_PARTITION).build());
        initializationProductions.add(initializeFrom(rhs).node(lastVertex.rightChild).idx(leafCount * 3 - 1).partition(LAST_PARTITION).build());

        return initializationProductions;
    }

    private Ay.AyBuilder initializeFrom(double[][] rhs) {
        return Ay.builder().solution(rhs).coefficients(methodCoefficients);
    }

}
