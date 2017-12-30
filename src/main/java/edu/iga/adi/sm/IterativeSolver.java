package edu.iga.adi.sm;

import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.Problem;
import edu.iga.adi.sm.core.Solution;
import edu.iga.adi.sm.problems.IterativeProblem;
import edu.iga.adi.sm.results.series.CachedSolutionSeries;
import edu.iga.adi.sm.results.series.FromStorageSolutionSeries;
import edu.iga.adi.sm.results.series.SolutionSeries;
import edu.iga.adi.sm.results.storage.InMemorySolutionStorage;
import edu.iga.adi.sm.results.storage.SolutionStorage;
import lombok.Builder;

import java.io.IOException;
import java.util.Optional;
import java.util.WeakHashMap;

@Builder
class IterativeSolver {

    private static final String SOLUTION_NUMBER = "solutionNumber";

    private final Solver solver;

    private final Mesh mesh;

    @Builder.Default
    private SolutionStorage<Solution> solutionStorage = new InMemorySolutionStorage<>();

    private final WeakHashMap<Integer, Solution> solutionCache = new WeakHashMap<>();

    SolutionSeries solveIteratively(IterativeProblem iterativeProblem) {
        Optional<Problem> currentProblem = Optional.of(iterativeProblem.getInitialProblem());
        int solutionNumber = 0;
        while (currentProblem.isPresent()) {
            Solution solution = solver.solveProblem(currentProblem.get());
            storeAndCacheSolution(solutionNumber++, solution);
            currentProblem = iterativeProblem.getNextProblem(solution);
        }
        return new CachedSolutionSeries(FromStorageSolutionSeries.builder()
                .mesh(mesh)
                .solutionCount(solutionNumber)
                .solutionStorage(solutionStorage)
                .build());
    }

    private void storeAndCacheSolution(int solutionNumber, Solution solution) {
        solution.metadata.put("solutionNumber", solutionNumber);
        solutionCache.put(solutionNumber, solution);
        storeSolution(solution);
    }

    private void storeSolution(Solution solution) {
        try {
            solutionStorage.store((int) solution.metadata.get(SOLUTION_NUMBER), solution);
        } catch (IOException e) {
            throw new IllegalStateException("Could not store intermediate solution", e);
        }
    }

}
