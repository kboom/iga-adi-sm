package edu.iga.adi.sm.problems;

import edu.iga.adi.sm.ProblemManagerFactory;
import edu.iga.adi.sm.SolverConfiguration;
import edu.iga.adi.sm.SolverFactory;
import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.direction.execution.ProductionExecutorFactory;
import edu.iga.adi.sm.problems.flood.FloodManager;
import edu.iga.adi.sm.problems.heat.HeatManager;
import edu.iga.adi.sm.problems.projection.ProjectionProblemManager;
import edu.iga.adi.sm.problems.terrain.TerrainManager;
import lombok.Builder;

@Builder
public class LocalProblemManagerFactory implements ProblemManagerFactory {

    private SolverConfiguration solverConfiguration;
    private Mesh mesh;
    private SolverFactory solverFactory;
    private ProductionExecutorFactory productionExecutorFactory;

    @Override
    public ProblemManager createProblemManager() {
        switch (solverConfiguration.getProblemType()) {
            case "projection":
                return createProjectionProblemFactory();
            case "heat":
                return createHeatProblemFactory();
            case "flood":
                return createFloodProblemFactory();
            case "terrain-svd":
                return createTerrainSvdProblemFactory();
            default:
                throw new IllegalStateException("Could not identify the problem");
        }
    }

    private ProblemManager createProjectionProblemFactory() {
        return new ProjectionProblemManager(solverConfiguration);
    }

    private ProblemManager createTerrainSvdProblemFactory() {
        return new TerrainManager(solverConfiguration);
    }

    private ProblemManager createFloodProblemFactory() {
        return new FloodManager(solverFactory, productionExecutorFactory, solverConfiguration);
    }

    private ProblemManager createHeatProblemFactory() {
        return new HeatManager(solverConfiguration);
    }

}