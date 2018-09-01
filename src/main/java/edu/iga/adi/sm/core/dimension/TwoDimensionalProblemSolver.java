package edu.iga.adi.sm.core.dimension;

import com.sun.istack.internal.NotNull;
import edu.iga.adi.sm.Solver;
import edu.iga.adi.sm.Task;
import edu.iga.adi.sm.TimeLogger;
import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.Problem;
import edu.iga.adi.sm.core.Solution;
import edu.iga.adi.sm.core.direction.DirectionSolver;
import edu.iga.adi.sm.core.direction.RunInformation;
import edu.iga.adi.sm.core.direction.SolutionLogger;
import edu.iga.adi.sm.core.direction.execution.ProductionExecutorFactory;
import edu.iga.adi.sm.core.direction.initialization.HorizontalLeafInitializer;
import edu.iga.adi.sm.core.direction.initialization.LeafInitializer;
import edu.iga.adi.sm.core.direction.initialization.VerticalLeafInitializer;
import edu.iga.adi.sm.core.direction.productions.HorizontalProductionFactory;
import edu.iga.adi.sm.core.direction.productions.ProductionFactory;
import edu.iga.adi.sm.core.direction.productions.VerticalProductionFactory;
import edu.iga.adi.sm.core.direction.productions.initialization.MethodCoefficients;
import lombok.Builder;

import static edu.iga.adi.sm.core.direction.productions.initialization.ExplicitMethodCoefficients.EXPLICIT_METHOD_COEFFICIENTS;
import static edu.iga.adi.sm.core.direction.productions.initialization.ImplicitMethodCoefficients.IMPLICIT_METHOD_COEFFICIENTS;

@Builder
public final class TwoDimensionalProblemSolver implements Solver {

    @NotNull
    private Task task;

    @NotNull
    private Mesh mesh;

    @NotNull
    private ProductionExecutorFactory launcherFactory;

    @NotNull
    private SolutionLogger solutionLogger;

    @NotNull
    private TimeLogger timeLogger;

    @Override
    public Solution solveProblem(Problem problem, RunInformation runInformation) {
        final ProductionFactory horizontalProductionFactory = new HorizontalProductionFactory(mesh);
        final LeafInitializer horizontalLeafInitializer = HorizontalLeafInitializer.builder()
                .mesh(mesh)
                .problem(problem)
                .methodCoefficients(methodCoefficients())
                .build();

        DirectionSolver horizontalProblemSolver = DirectionSolver.builder()
                .launcherFactory(launcherFactory)
                .productionFactory(horizontalProductionFactory)
                .leafInitializer(horizontalLeafInitializer)
                .meshData(mesh)
                .solutionLogger(solutionLogger)
                .timeLogger(timeLogger)
                .build();

        final Solution horizontalSolution = horizontalProblemSolver.solveProblem(problem, runInformation);

        final ProductionFactory verticalProductionFactory = new VerticalProductionFactory(mesh);
        final LeafInitializer verticalLeafInitializer = VerticalLeafInitializer.builder()
                .mesh(mesh)
                .horizontalSolution(horizontalSolution)
                .methodCoefficients(methodCoefficients())
                .build();

        DirectionSolver verticalProblemSolver = DirectionSolver.builder()
                .launcherFactory(launcherFactory)
                .productionFactory(verticalProductionFactory)
                .leafInitializer(verticalLeafInitializer)
                .meshData(mesh)
                .solutionLogger(solutionLogger)
                .timeLogger(timeLogger)
                .build();

        Solution verticalSolution = verticalProblemSolver.solveProblem(problem, runInformation);
        return task.getSolutionFactory().createFinalSolution(verticalSolution, runInformation);
    }

    private MethodCoefficients methodCoefficients() {
        switch (task.getTimeMethodType()) {
            case EXPLICIT:
                return EXPLICIT_METHOD_COEFFICIENTS;
            case IMPLICIT:
                return IMPLICIT_METHOD_COEFFICIENTS;
        }
        throw new IllegalStateException("This should not happen");
    }

}