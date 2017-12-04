package edu.iga.adi.sm;

import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.Problem;
import edu.iga.adi.sm.core.Solution;
import edu.iga.adi.sm.problems.IterativeProblem;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.Optional;

@Builder
@AllArgsConstructor
class IterativeSolver {

    private final Solver solver;

    private final Mesh mesh;

    SolutionSeries solveIteratively(IterativeProblem iterativeProblem) {
        SolutionSeries.SolutionsInTimeBuilder solutionsInTimeBuilder = SolutionSeries.solutionsInTime().withMesh(mesh);
        Optional<Problem> currentProblem = Optional.of(iterativeProblem.getInitialProblem());
        while (currentProblem.isPresent()) {
            Solution solution = solver.solveProblem(currentProblem.get());
            solutionsInTimeBuilder.addSolution(solution);
            currentProblem = iterativeProblem.getNextProblem(solution);
        }
        return solutionsInTimeBuilder.build();
    }

}
