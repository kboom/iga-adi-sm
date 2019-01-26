package edu.iga.adi.sm.problems.heat;

import edu.iga.adi.sm.core.Problem;
import edu.iga.adi.sm.core.Solution;
import edu.iga.adi.sm.problems.IterativeProblem;
import lombok.Builder;
import lombok.NonNull;

import java.util.Optional;

public final class HeatTransferProblem implements IterativeProblem {

    private final double delta;

    private final int steps;

    @NonNull
    private Problem initialSurface;

    private int currentStep = 0;

    @Builder
    HeatTransferProblem(Problem initialSurface, double delta, int steps) {
        this.initialSurface = initialSurface;
        this.delta = delta;
        this.steps = steps;
    }

    @Override
    public Problem getInitialProblem() {
        return initialSurface;
    }

    @Override
    public Optional<Problem> getNextProblem(Solution solution) {
        currentStep++;
        return currentStep < steps ? Optional.of((x, y) -> {
            double value = solution.getValue(x, y);
                return value + delta * solution.getModifiedValue(x, y);
        }) : Optional.empty();
    }

}