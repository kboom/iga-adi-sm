package edu.iga.adi.sm.problems;

import edu.iga.adi.sm.SolverConfiguration;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ProjectionProblemTest extends AbstractProblemTest {

    @Test
    public void solvesSphericalProblem() throws Exception {
        ProblemManagerTestResults testResults = launchSolver(SolverConfiguration.builder()
                .problemSize(12)
                .problemType("projection")
                .build());
        assertThat(testResults.getResultAsCsvString()).isEqualTo(readTestFile("projection.csv"));
    }

}
