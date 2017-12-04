package edu.iga.adi.sm.core.direction.initialization;

import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.Problem;
import edu.iga.adi.sm.core.direction.Vertex;
import edu.iga.adi.sm.core.direction.productions.Production;
import edu.iga.adi.sm.core.direction.productions.initialization.A;
import edu.iga.adi.sm.core.direction.productions.initialization.A1;
import edu.iga.adi.sm.core.direction.productions.initialization.AN;

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
    public List<Production> initializeLeaves(List<Vertex> leafLevelVertices) {
        List<Production> initializationProductions = new ArrayList<>(leafLevelVertices.size());

        Vertex firstVertex = leafLevelVertices.get(0);
        initializationProductions.add(new A1(firstVertex.leftChild, mesh, rhs));
        initializationProductions.add(new A(firstVertex.middleChild, mesh, rhs));
        initializationProductions.add(new A(firstVertex.rightChild, mesh, rhs));


        for (int i = 1; i < leafLevelVertices.size() - 1; i++) {
            Vertex vertex = leafLevelVertices.get(i);
            initializationProductions.add(new A(vertex.leftChild, mesh, rhs));
            initializationProductions.add(new A(vertex.middleChild, mesh, rhs));
            initializationProductions.add(new A(vertex.rightChild, mesh, rhs));
        }


        Vertex lastVertex = leafLevelVertices.get(leafLevelVertices.size() - 1);
        initializationProductions.add(new A(lastVertex.leftChild, mesh, rhs));
        initializationProductions.add(new A(lastVertex.middleChild, mesh, rhs));
        initializationProductions.add(new AN(lastVertex.rightChild, mesh, rhs));

        return initializationProductions;
    }


}
