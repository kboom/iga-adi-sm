package edu.iga.adi.sm.core.direction.productions;

import edu.iga.adi.sm.core.direction.Vertex;

public interface ProductionFactory {

    Production createRootProduction(Vertex vertex);

    Production createIntermediateProduction(Vertex vertex);

    Production createLeafProductions(Vertex vertex);

    Production mergeLeavesProduction(Vertex vertex);

    Production eliminateLeavesProduction(Vertex vertex);

    Production mergeIntermediateProduction(Vertex vertex);

    Production mergeUpProduction(Vertex vertex);

    Production eliminateIntermediateProduction(Vertex vertex);

    Production rootSolverProduction(Vertex vertex);

    Production backwardSubstituteProduction(Vertex vertex);

    Production backwardSubstituteIntermediateProduction(Vertex vertex);

    Production backwardSubstituteUpProduction(Vertex vertex);

    Production backwardSubstituteLeavesProduction(Vertex vertex);

}
