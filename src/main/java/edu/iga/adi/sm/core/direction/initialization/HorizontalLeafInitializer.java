package edu.iga.adi.sm.core.direction.initialization;

import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.Problem;
import edu.iga.adi.sm.core.direction.RunInformation;
import edu.iga.adi.sm.core.direction.Vertex;
import edu.iga.adi.sm.core.direction.productions.Production;
import edu.iga.adi.sm.core.direction.productions.initialization.AxEven;
import edu.iga.adi.sm.core.direction.productions.initialization.AxOdd;

import java.util.ArrayList;
import java.util.List;

public class HorizontalLeafInitializer implements LeafInitializer {

    private Mesh mesh;

    private Problem rhs;

    public HorizontalLeafInitializer(Mesh mesh, Problem rhs) {
        this.mesh = mesh;
        this.rhs = rhs;
    }

    @Override
    public List<Production> initializeLeaves(List<Vertex> leafLevelVertices, RunInformation runInformation) {
        List<Production> initializationProductions = new ArrayList<>(leafLevelVertices.size());

        final ProductionSupplier productionSupplier = new ProductionSupplier(runInformation);

        for (int i = 0; i < leafLevelVertices.size(); i++) {
            Vertex vertex = leafLevelVertices.get(i);
            initializationProductions.add(productionSupplier.create(vertex.leftChild));
            initializationProductions.add(productionSupplier.create(vertex.middleChild));
            initializationProductions.add(productionSupplier.create(vertex.rightChild));
        }

        return initializationProductions;
    }

    private final class ProductionSupplier {

        private final RunInformation runInformation;

        private ProductionSupplier(RunInformation runInformation) {
            this.runInformation = runInformation;
        }

        private Production create(Vertex vertex) {
            if(runInformation.getRunNumber() % 2 == 0) {
                return new AxEven(vertex, mesh, rhs);
            } else {
                return new AxOdd(vertex, mesh, rhs);
            }
        }

    }


}
