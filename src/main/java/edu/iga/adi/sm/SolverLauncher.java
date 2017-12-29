package edu.iga.adi.sm;

import edu.iga.adi.sm.core.Solution;
import edu.iga.adi.sm.core.direction.execution.ProductionExecutorFactory;
import edu.iga.adi.sm.problems.ProblemManager;
import edu.iga.adi.sm.results.storage.FileSolutionStorage;
import lombok.Builder;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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

        if(solverConfiguration.isStoring()) {
            solutionStorage.storeAll(solutionSeries.getSubsequentSolutions().stream());
        }

        solutionStorage.tearDown();
        problemManager.tearDown();
    }

    private SolutionSeries solve(ProblemManager problemManager, Solver solver) {
        return IterativeSolver.builder()
                .mesh(solverConfiguration.getMesh())
                .solver(solver)
                .build()
                .solveIteratively(problemManager.getProblem());
    }

    private SolutionSeries retrieve() {
        List<Solution> solutions = solutionStorage.retrieveAll().collect(Collectors.toList());
        return new SolutionSeries(solutions, solverConfiguration.getMesh());
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
