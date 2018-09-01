package edu.iga.adi.sm.problems;

import edu.iga.adi.sm.AcceptanceTest;
import edu.iga.adi.sm.SolverConfiguration;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class HeatTransferProblemKnownSurfaceTest extends AbstractProblemTest {

    @Test
    public void solvesHeatTransferFromCone() throws Exception {
        AbstractProblemTest.ProblemManagerTestResults testResults = launchSolver(SolverConfiguration.builder()
                .problemSize(12)
                .problemType("heat")
                .initialSurface("cone")
                .build());
        assertThat(testResults.getResultAsCsvString()).isEqualTo(readTestFile("heat/heat.cone.csv"));
    }

}
