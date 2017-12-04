package edu.iga.adi.sm;

import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.Problem;
import edu.iga.adi.sm.core.Solution;
import edu.iga.adi.sm.core.dimension.SolutionFactory;
import edu.iga.adi.sm.core.dimension.TwoDimensionalProblemSolver;
import edu.iga.adi.sm.core.direction.SolutionLogger;
import edu.iga.adi.sm.core.direction.execution.ProductionExecutorFactory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SolverFactory {

    private final Mesh mesh;
    private final SolutionLogger solutionLogger;
    private final TimeLogger timeLogger;

    public Solver createSolver(SolutionFactory solutionFactory) {
        ProductionExecutorFactory productionExecutorFactory = new ProductionExecutorFactory();

        TwoDimensionalProblemSolver problemSolver = new TwoDimensionalProblemSolver(
                productionExecutorFactory,
                mesh,
                solutionFactory,
                solutionLogger,
                timeLogger
        );

        return new SolverFacade(problemSolver) {
            /*
             * Place any facade-like methods in here
             */
        };
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public abstract class SolverFacade implements Solver {

        private final Solver solver;

        @Override
        public Solution solveProblem(Problem problem) {
            return solver.solveProblem(problem);
        }

    }

}
