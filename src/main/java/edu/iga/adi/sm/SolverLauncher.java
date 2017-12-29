package edu.iga.adi.sm;

import edu.iga.adi.sm.core.Solution;
import edu.iga.adi.sm.core.direction.execution.ProductionExecutorFactory;
import edu.iga.adi.sm.problems.ProblemManager;
import edu.iga.adi.sm.results.storage.FileSolutionStorage;
import lombok.Builder;

import java.io.IOException;

@Builder
public final class SolverLauncher {

    private final ProductionExecutorFactory productionExecutorFactory;
    private final TimeLogger timeLogger;
    private final SolverFactory solverFactory;
    private final ProblemManagerFactory problemManagerFactory;
    private final SolverConfiguration solverConfiguration;
    private final FileSolutionStorage<Solution> solutionStorage;

    public void launch() {
        final ProblemManager problemManager = problemManagerFactory.createProblemManager();

        problemManager.setUp();

        final Solver solver = solverFactory.createSolver(problemManager.getSolutionFactory());

        SolutionSeries solutionSeries = !solverConfiguration.isRetrieve()
                ? solve(problemManager, solver)
                : null;

        productionExecutorFactory.joinAll();

        logExecutionTimes();

        problemManager.processResults(solutionSeries);
        storeResults(solutionSeries);

        problemManager.tearDown();
    }

    private SolutionSeries solve(ProblemManager problemManager, Solver solver) {
        return IterativeSolver.builder()
                .mesh(solverConfiguration.getMesh())
                .solver(solver)
                .build()
                .solveIteratively(problemManager.getProblem());
    }

    private void storeResults(SolutionSeries solutionSeries) {
        try {
            solutionStorage.setUp();
            solutionStorage.storeAll(solutionSeries.getSubsequentSolutions().stream());
            solutionStorage.tearDown();
        } catch (IOException e) {
            throw new IllegalStateException("Could not store solutions", e);
        }
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
