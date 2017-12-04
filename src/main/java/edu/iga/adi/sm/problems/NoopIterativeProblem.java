package edu.iga.adi.sm.problems;

import edu.iga.adi.sm.core.Problem;
import edu.iga.adi.sm.core.Solution;

import java.util.Optional;

public class NoopIterativeProblem implements IterativeProblem {

    @Override
    public Problem getInitialProblem() {
        return (x, y) -> 1;
    }

    @Override
    public Optional<Problem> getNextProblem(Solution solution) {
        return Optional.empty();
    }

}
