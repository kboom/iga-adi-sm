package edu.iga.adi.sm.core.direction.initialization;

import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.Solution;
import edu.iga.adi.sm.core.direction.RunInformation;
import edu.iga.adi.sm.core.direction.Vertex;
import edu.iga.adi.sm.core.direction.productions.Production;
import edu.iga.adi.sm.core.direction.productions.initialization.AyEven;
import edu.iga.adi.sm.core.direction.productions.initialization.AyOdd;

import java.util.ArrayList;
import java.util.List;

public class VerticalLeafInitializer implements LeafInitializer {

    private final Mesh mesh;

    private final Solution horizontalSolution;

    public VerticalLeafInitializer(Mesh mesh, Solution horizontalSolution) {
        this.mesh = mesh;
        this.horizontalSolution = horizontalSolution;
    }

    @Override
    public List<Production> initializeLeaves(List<Vertex> leafLevelVertices, RunInformation runInformation) {
        final int leafCount = leafLevelVertices.size();
        double[][] rhs = horizontalSolution.getRhs();
        List<Production> initializationProductions = new ArrayList<>(leafLevelVertices.size());

        final ProductionSupplier productionSupplier = new ProductionSupplier(runInformation, rhs);

        Vertex firstVertex = leafLevelVertices.get(0);
        initializationProductions.add(productionSupplier.create(firstVertex.leftChild, new double[]{1, 1. / 2, 1. / 3}, 0));
        initializationProductions.add(productionSupplier.create(firstVertex.middleChild, new double[]{1. / 2, 1. / 3, 1. / 3}, 1));
        initializationProductions.add(productionSupplier.create(firstVertex.rightChild, new double[]{1. / 3, 1. / 3, 1. / 3}, 2));

        for (int i = 1; i < leafCount - 1; i++) {
            Vertex vertex = leafLevelVertices.get(i);
            initializationProductions.add(productionSupplier.create(vertex.leftChild, new double[]{1. / 3, 1. / 3, 1. / 3}, 3 * i));
            initializationProductions.add(productionSupplier.create(vertex.middleChild, new double[]{1. / 3, 1. / 3, 1. / 3}, 3 * i + 1));
            initializationProductions.add(productionSupplier.create(vertex.rightChild, new double[]{1. / 3, 1. / 3, 1. / 3}, 3 * i + 2));
        }


        Vertex lastVertex = leafLevelVertices.get(leafCount - 1);
        initializationProductions.add(productionSupplier.create(lastVertex.leftChild, new double[]{1. / 3, 1. / 3, 1. / 3}, leafCount * 3 - 3));
        initializationProductions.add(productionSupplier.create(lastVertex.middleChild, new double[]{1. / 3, 1. / 3, 1. / 2}, leafCount * 3 - 2));
        initializationProductions.add(productionSupplier.create(lastVertex.rightChild, new double[]{1. / 3, 1. / 2, 1}, leafCount * 3 - 1));

        return initializationProductions;
    }

    private final class ProductionSupplier {

        private final double[][] rhs;
        private final RunInformation runInformation;

        private ProductionSupplier(RunInformation runInformation, double[][] rhs) {
            this.runInformation = runInformation;
            this.rhs = rhs;
        }

        private Production create(Vertex vertex, double[] portion, int idx) {
            if(runInformation.getRunNumber() % 2 == 0) {
                return new AyEven(vertex, rhs, portion, idx, mesh);
            } else {
                return new AyOdd(vertex, rhs, portion, idx, mesh);
            }
        }

    }

}
