package edu.iga.adi.sm.core.direction.initialization;

import com.sun.istack.internal.NotNull;
import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.Problem;
import edu.iga.adi.sm.core.direction.Vertex;
import edu.iga.adi.sm.core.direction.productions.Production;
import edu.iga.adi.sm.core.direction.productions.initialization.Ax;
import edu.iga.adi.sm.core.direction.productions.initialization.MethodCoefficients;
import lombok.Builder;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Builder
@Value
public class HorizontalLeafInitializer implements LeafInitializer {

    @NotNull
    private Mesh mesh;

    @NotNull
    private Problem problem;

    @NotNull
    private MethodCoefficients methodCoefficients;

    @Override
    public List<Production> initializeLeaves(List<Vertex> leafLevelVertices) {
        List<Production> initializationProductions = new ArrayList<>(leafLevelVertices.size());

        for (Vertex vertex : leafLevelVertices) {
            initializationProductions.add(initialize(vertex.leftChild));
            initializationProductions.add(initialize(vertex.middleChild));
            initializationProductions.add(initialize(vertex.rightChild));
        }

        return initializationProductions;
    }

    private Ax initialize(Vertex node) {
        return Ax.builder().node(node).problem(problem).mesh(mesh).coefficients(methodCoefficients).build();
    }

}
