package edu.iga.adi.sm;

import edu.iga.adi.sm.core.Solution;
import edu.iga.adi.sm.core.direction.execution.ProductionExecutorFactory;
import edu.iga.adi.sm.problems.ProblemManager;
import edu.iga.adi.sm.results.series.FromStorageSolutionSeries;
import edu.iga.adi.sm.results.series.SolutionSeries;
import edu.iga.adi.sm.results.storage.FileSolutionStorage;
import edu.iga.adi.sm.results.storage.InMemorySolutionStorage;
import lombok.Builder;
import lombok.SneakyThrows;

@Builder
public final class SolverLauncher {

    private final ProductionExecutorFactory productionExecutorFactory;
    private final TimeLogger timeLogger;
    private final SolverFactory solverFactory;
    private final ProblemManagerFactory problemManagerFactory;
    private final FileSolutionStorage<Solution> solutionStorage;

    private final SolverConfiguration solverConfiguration;

    @SneakyThrows
    public void launch() {
        final ProblemManager problemManager = problemManagerFactory.createProblemManager();

        problemManager.setUp();
        solutionStorage.setUp();

        final Solver solver = solverFactory.createSolver(problemManager.getSolutionFactory());

        SolutionSeries solutionSeries = !solverConfiguration.isRetrieve()
                ? solve(problemManager, solver)
                : retrieve();

        productionExecutorFactory.joinAll();

        logExecutionTimes();

        problemManager.processResults(solutionSeries);

        solutionStorage.tearDown();
        problemManager.tearDown();
    }

    private SolutionSeries solve(ProblemManager problemManager, Solver solver) {
        return IterativeSolver.builder()
                .mesh(solverConfiguration.getMesh())
                .solutionStorage(solverConfiguration.isStoring() ? solutionStorage : new InMemorySolutionStorage<>())
                .solver(solver)
                .build()
                .solveIteratively(problemManager.getProblem());
    }

    private SolutionSeries retrieve() {
        return FromStorageSolutionSeries.builder()
                .mesh(solverConfiguration.getMesh())
                .solutionStorage(solutionStorage)
                .solutionCount(solverConfiguration.getSteps())
                .build();
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
