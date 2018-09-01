package edu.iga.adi.sm.core.direction.productions;

import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.direction.Vertex;
import edu.iga.adi.sm.core.direction.productions.construction.P1x;
import edu.iga.adi.sm.core.direction.productions.construction.P2;
import edu.iga.adi.sm.core.direction.productions.construction.P3;
import edu.iga.adi.sm.core.direction.productions.solution.backsubstitution.*;
import edu.iga.adi.sm.core.direction.productions.solution.factorization.M2_2;
import edu.iga.adi.sm.core.direction.productions.solution.factorization.M2_2_H;
import edu.iga.adi.sm.core.direction.productions.solution.factorization.M2_3;
import edu.iga.adi.sm.core.direction.productions.solution.factorization.Aroot;

public class HorizontalProductionFactory implements ProductionFactory {

    private Mesh mesh;

    public HorizontalProductionFactory(Mesh mesh) {
        this.mesh = mesh;
    }

    @Override
    public Production createRootProduction(Vertex vertex) {
        return new P1x(vertex);
    }

    @Override
    public Production createIntermediateProduction(Vertex vertex) {
        return new P2(vertex);
    }

    @Override
    public Production createLeafProductions(Vertex vertex) {
        return new P3(vertex);
    }

    @Override
    public Production mergeLeavesProduction(Vertex vertex) {
        return new M2_3(vertex);
    }

    @Override
    public Production eliminateLeavesProduction(Vertex vertex) {
        return new E2_1_5(vertex, mesh);
    }

    @Override
    public Production mergeIntermediateProduction(Vertex vertex) {
        return new M2_2(vertex);
    }

    @Override
    public Production eliminateIntermediateProduction(Vertex vertex) {
        return new E2_2_6(vertex, mesh);
    }

    @Override
    public Production rootSolverProduction(Vertex vertex) {
        return new Aroot(vertex);
    }

    @Override
    public Production backwardSubstituteProduction(Vertex vertex) {
        return new Eroot(vertex, mesh);
    }

    @Override
    public Production backwardSubstituteIntermediateProduction(Vertex vertex) {
        return new BS_2_6(vertex, mesh);
    }

    @Override
    public Production backwardSubstituteUpProduction(Vertex vertex) {
        return new BS_2_6_H(vertex, mesh);
    }

    @Override
    public Production backwardSubstituteLeavesProduction(Vertex vertex) {
        return new BS_1_5(vertex, mesh);
    }

    @Override
    public Production mergeUpProduction(Vertex vertex) {
        return new M2_2_H(vertex);
    }

}
