package edu.iga.adi.sm.problems;

import edu.iga.adi.sm.core.Problem;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OneStepProblem extends SingleRunProblem {

    private final Problem problem;

    @Override
    protected Problem getProblem() {
        return problem;
    }

    public static IterativeProblem justOne(Problem problem) {
        return new OneStepProblem(problem);
    }
}
