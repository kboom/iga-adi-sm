package edu.iga.adi.sm.problems;

import edu.iga.adi.sm.SolverConfiguration;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class HeatTransferProblemTest extends AbstractProblemTest {

    @Test
    public void solvesHeatTransfer() throws Exception {
        ProblemManagerTestResults testResults = launchSolver(SolverConfiguration.builder()
                .problemSize(12)
                .problemType("heat")
                .build());
        assertThat(testResults.getResultAsCsvString()).isEqualTo(readTestFile("heat.csv"));
    }

}
