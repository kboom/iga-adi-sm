package edu.iga.adi.sm.problems;

import edu.iga.adi.sm.TestData;
import edu.iga.adi.sm.core.Problem;
import org.junit.Test;

import static edu.iga.adi.sm.problems.OneStepProblem.justOne;
import static org.assertj.core.api.Assertions.assertThat;

public class OneStepProblemTest {

    @Test
    public void oneProblemReturnsThatProblemAsInitial() {
        Problem problem = TestData.dummyProblem();
        assertThat(justOne(problem).getInitialProblem()).isEqualTo(problem);
    }

    @Test
    public void oneProblemDoesNotHaveNextProblem() {
        assertThat(justOne(TestData.dummyProblem()).getNextProblem(TestData.dummySolution())).isEmpty();
    }

}