package edu.iga.adi.sm.problems;

import edu.iga.adi.sm.SolverConfiguration;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TerrainProjectionProblemTest extends AbstractProblemTest {

    @Test
    public void solvesTerrainProjectionProblem() throws Exception {
        AbstractProblemTest.ProblemManagerTestResults testResults = launchSolver(SolverConfiguration.builder()
                .problemSize(12)
                .problemType("terrain-svd")
                .build());
        assertThat(testResults.getResultAsCsvString()).isEqualTo(readTestFile("terrain.csv"));
    }

}
