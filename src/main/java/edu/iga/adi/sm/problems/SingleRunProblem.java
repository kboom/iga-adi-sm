package edu.iga.adi.sm.problems;

import edu.iga.adi.sm.core.Problem;
import edu.iga.adi.sm.core.Solution;

import java.util.Optional;

public abstract class SingleRunProblem implements IterativeProblem {

    @Override
    public final Problem getInitialProblem() {
        return getProblem();
    }

    @Override
    public Optional<Problem> getNextProblem(Solution solution) {
        return Optional.empty();
    }

    protected abstract Problem getProblem();

}
