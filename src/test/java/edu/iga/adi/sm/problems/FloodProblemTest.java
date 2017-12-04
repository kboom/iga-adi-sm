package edu.iga.adi.sm.problems;

import edu.iga.adi.sm.SolverConfiguration;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FloodProblemTest extends AbstractProblemTest {

    @Test
    public void solvesFloodProblem() throws Exception {
        ProblemManagerTestResults testResults = launchSolver(SolverConfiguration.builder()
                .problemSize(12)
                .problemType("flood")
                .build());
        assertThat(testResults.getResultAsCsvString()).isEqualTo(readTestFile("flood.csv"));
    }

}
