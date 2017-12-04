package edu.iga.adi.sm.core.dimension;

import edu.iga.adi.sm.Solver;
import edu.iga.adi.sm.TimeLogger;
import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.Problem;
import edu.iga.adi.sm.core.Solution;
import edu.iga.adi.sm.core.direction.DirectionSolver;
import edu.iga.adi.sm.core.direction.SolutionLogger;
import edu.iga.adi.sm.core.direction.execution.ProductionExecutorFactory;
import edu.iga.adi.sm.core.direction.initialization.HorizontalLeafInitializer;
import edu.iga.adi.sm.core.direction.initialization.LeafInitializer;
import edu.iga.adi.sm.core.direction.initialization.VerticalLeafInitializer;
import edu.iga.adi.sm.core.direction.productions.HorizontalProductionFactory;
import edu.iga.adi.sm.core.direction.productions.ProductionFactory;
import edu.iga.adi.sm.core.direction.productions.VerticalProductionFactory;

public final class TwoDimensionalProblemSolver implements Solver {

    private final Mesh mesh;

    private final ProductionExecutorFactory launcherFactory;

    private final SolutionFactory solutionFactory;

    private final SolutionLogger solutionLogger;

    private final TimeLogger timeLogger;

    public TwoDimensionalProblemSolver(ProductionExecutorFactory launcherFactory,
                                       Mesh meshData,
                                       SolutionFactory solutionFactory,
                                       SolutionLogger solutionLogger,
                                       TimeLogger timeLogger) {
        this.launcherFactory = launcherFactory;
        this.mesh = meshData;
        this.timeLogger = timeLogger;
        this.solutionLogger = solutionLogger;
        this.solutionFactory = solutionFactory;
    }

    @Override
    public Solution solveProblem(Problem rhs) {
        ProductionFactory horizontalProductionFactory = new HorizontalProductionFactory(mesh);
        LeafInitializer horizontalLeafInitializer = new HorizontalLeafInitializer(mesh, rhs);

        DirectionSolver horizontalProblemSolver = new DirectionSolver(
                horizontalProductionFactory,
                launcherFactory,
                horizontalLeafInitializer,
                mesh,
                solutionLogger,
                timeLogger
        );
        Solution horizontalSolution = horizontalProblemSolver.solveProblem(rhs);

        ProductionFactory verticalProductionFactory = new VerticalProductionFactory(mesh);
        LeafInitializer verticalLeafInitializer = new VerticalLeafInitializer(mesh, horizontalSolution);
        DirectionSolver verticalProblemSolver = new DirectionSolver(
                verticalProductionFactory,
                launcherFactory,
                verticalLeafInitializer,
                mesh,
                solutionLogger,
                timeLogger
        );

        Solution verticalSolution = verticalProblemSolver.solveProblem(rhs);
        return solutionFactory.createFinalSolution(verticalSolution);
    }

}