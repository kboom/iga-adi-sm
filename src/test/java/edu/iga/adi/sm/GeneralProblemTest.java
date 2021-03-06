package edu.iga.adi.sm;

import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.Problem;
import edu.iga.adi.sm.core.Solution;
import edu.iga.adi.sm.core.SolutionGrid;
import edu.iga.adi.sm.core.dimension.SolutionFactory;
import edu.iga.adi.sm.core.dimension.TwoDimensionalProblemSolver;
import edu.iga.adi.sm.core.direction.RunInformation;
import edu.iga.adi.sm.core.direction.SolutionLogger;
import edu.iga.adi.sm.core.direction.execution.ProductionExecutorFactory;
import edu.iga.adi.sm.loggers.NoopSolutionLogger;
import org.junit.Test;

import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static edu.iga.adi.sm.core.SolutionGrid.solutionGrid;
import static edu.iga.adi.sm.problems.OneStepProblem.justOne;
import static edu.iga.adi.sm.problems.ProblemUtils.createRectangularMesh;
import static edu.iga.adi.sm.support.Point.solutionPoint;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GeneralProblemTest {

    private static final SolutionLogger DUMMY_SOLUTION_LOGGER = new NoopSolutionLogger();
    private static final TimeLogger DUMMY_TIME_LOGGER = new TimeLogger();
    private static final SolutionFactory DUMMY_SOLUTION_FACTORY = (solution, runInformation) -> solution;
    private static final int REQUIRED_PRECISION = 6;
    private static final ProductionExecutorFactory productionExecutorFactory = new ProductionExecutorFactory(SolverConfiguration.builder().build());
    private static final int TEST_PROBLEM_SIZE = 12;
    private static final int BIG_PROBLEM_SIZE = 24;

    @Test
    public void solvesConstProblem() {
        canSolveProblem((x, y) -> 1d);
    }

    @Test
    public void solvesXProblem() {
        canSolveProblem((x, y) -> x);
    }

    @Test
    public void solvesYProblem() {
        canSolveProblem((x, y) -> y);
    }

    @Test
    public void solvesXPlusYProblem() {
        canSolveProblem((x, y) -> x + y);
    }

    @Test
    public void solvesXMinusYProblem() {
        canSolveProblem((x, y) -> x - y);
    }

    @Test
    public void solvesYMinusXProblem() {
        canSolveProblem((x, y) -> y - x);
    }

    @Test
    public void solvesMinusXProblem() {
        canSolveProblem((x, y) -> -x);
    }

    @Test
    public void solvesMinusYProblem() {
        canSolveProblem((x, y) -> -y);
    }

    @Test
    public void solvesSumOfSquaresProblem() {
        canSolveProblem((x, y) -> Math.pow(x, 2) + Math.pow(y, 2));
    }

    @Test
    public void solvesDifferenceOfSquaresProblem() {
        canSolveProblem((x, y) -> Math.pow(x, 2) - Math.pow(y, 2));
    }

    @Test
    public void solvesBigProblem() {
        canSolveProblemOfSize((x, y) -> Math.pow(x, 2) + Math.pow(y, 2), BIG_PROBLEM_SIZE);
    }

    private void canSolveProblem(BiFunction<Double, Double, Double> fn) {
        canSolveProblemOfSize(fn, TEST_PROBLEM_SIZE);
    }

    private void canSolveProblemOfSize(BiFunction<Double, Double, Double> fn, int size) {
        final Mesh mesh = createRectangularMesh(size);
        TwoDimensionalProblemSolver problemSolver = createSolver(fn::apply, mesh);
        Solution solution = problemSolver.solveProblem(fn::apply, RunInformation.initialInformation());
        assertThat(solution.getSolutionGrid().withPrecisionTo(REQUIRED_PRECISION)).isEqualTo(

                generateSolutionGridFor(mesh, fn)

        );
    }

    private TwoDimensionalProblemSolver createSolver(Problem problem, Mesh mesh) {
        return TwoDimensionalProblemSolver.builder()
                .task(Task.builder().timeMethodType(TimeMethodType.EXPLICIT)
                        .solutionFactory(DUMMY_SOLUTION_FACTORY)
                        .problem(justOne(problem)).build()
                )
                .timeLogger(DUMMY_TIME_LOGGER)
                .solutionLogger(DUMMY_SOLUTION_LOGGER)
                .mesh(mesh)
                .launcherFactory(productionExecutorFactory)
                .build();
    }

    private SolutionGrid generateSolutionGridFor(Mesh mesh, BiFunction<Double, Double, Double> fn) {
        return solutionGrid(IntStream.range(0, mesh.getElementsX()).boxed().parallel().flatMap(
                y -> IntStream.range(0, mesh.getElementsY()).mapToObj(x -> solutionPoint(x + 0.5, y + 0.5, fn.apply(x + 0.5, y + 0.5)))
        ).collect(Collectors.toList()));
    }

}
