package edu.iga.adi.sm;

import edu.iga.adi.sm.core.direction.execution.ProductionExecutorFactory;
import edu.iga.adi.sm.problems.ProblemManager;
import lombok.Builder;

@Builder
public final class SolverLauncher {

    private final ProductionExecutorFactory productionExecutorFactory;
    private final TimeLogger timeLogger;
    private final SolverFactory solverFactory;
    private final ProblemManagerFactory problemManagerFactory;
    private final SolverConfiguration solverConfiguration;

    public void launch() {
        final ProblemManager problemManager = problemManagerFactory.createProblemManager();

        problemManager.setUp();

        final Solver solver = solverFactory.createSolver(problemManager.getSolutionFactory());

        SolutionSeries solutionSeries = IterativeSolver.builder()
                .mesh(solverConfiguration.getMesh())
                .solver(solver)
                .build()
                .solveIteratively(problemManager.getProblem());

        productionExecutorFactory.joinAll();

        logExecutionTimes();

        problemManager.processResults(solutionSeries);
        problemManager.tearDown();
    }

    private void logExecutionTimes() {
        System.out.print(String.format("%d,%d,%d,%d",
                timeLogger.getTotalCreationMs(),
                timeLogger.getTotalInitializationMs(),
                timeLogger.getTotalFactorizationMs(),
                timeLogger.getTotalSolutionMs()
        ));
    }

}
