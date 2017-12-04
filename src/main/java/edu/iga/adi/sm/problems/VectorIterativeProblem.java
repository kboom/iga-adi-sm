package edu.iga.adi.sm.problems;

import edu.iga.adi.sm.core.Problem;
import edu.iga.adi.sm.core.Solution;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class VectorIterativeProblem<T> implements IterativeProblem {

    private final List<T> steps;
    private int iteration = 0;

    public VectorIterativeProblem(List<T> steps) {
        this.steps = new ArrayList<>(steps);
    }

    @Override
    public final Problem getInitialProblem() {
        return getProblemForValue(steps.get(iteration++));
    }

    @Override
    public final Optional<Problem> getNextProblem(Solution solution) {
        if (steps.size() > iteration) {
            return Optional.of(getProblemForValue(steps.get(iteration++)));
        } else return Optional.empty();
    }

    protected abstract Problem getProblemForValue(T value);

}
