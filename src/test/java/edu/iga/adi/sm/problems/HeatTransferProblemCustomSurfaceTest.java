package edu.iga.adi.sm.problems;

import edu.iga.adi.sm.SolverConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(Parameterized.class)
public class HeatTransferProblemCustomSurfaceTest extends AbstractProblemTest {

    private final int problemSize;
    private final String initialSurfaceFormula;
    private final int stepCount;
    private final double delta;
    private final String resultFileName;

    public HeatTransferProblemCustomSurfaceTest(
            String initialSurfaceFormula,
            int problemSize,
            int stepCount,
            double delta,
            String resultFileName) {
        this.problemSize = problemSize;
        this.initialSurfaceFormula = initialSurfaceFormula;
        this.stepCount = stepCount;
        this.delta = delta;
        this.resultFileName = resultFileName;
    }

    @Test
    public void solvesHeatTransferFromFunction() throws Exception {
        ProblemManagerTestResults testResults = launchSolver(SolverConfiguration.builder()
                .problemSize(problemSize)
                .problemType("heat")
                .initialSurfaceFormula(initialSurfaceFormula)
                .steps(stepCount)
                .delta(delta)
                .build());
        assertThat(testResults.getResultAsCsvString()).isEqualTo(readTestFile(resultFileName));
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"x + y", 12, 1, 0.00001, "heat/heat.func001.csv"},
                {"x + y", 12, 2, 0.00001, "heat/heat.func002.csv"},
                {"x + y", 12, 2, 0.01, "heat/heat.func003.csv"},
                {"x*x + y*y", 12, 2, 0.01, "heat/heat.func004.csv"},
                {"x*x + y*y", 12, 10, 0.01, "heat/heat.func005.csv"},
                {"x*x + y*y", 24, 1, 0.01, "heat/heat.func006.csv"},
                {"(x - 6)*(x - 6) + (y - 6) * (y - 6)", 192, 1, 0.01, "heat/heat.func007.csv"},
                {"x + y", 48, 10, 0.1, "heat/heat.stability.csv"},
        });
    }

}
