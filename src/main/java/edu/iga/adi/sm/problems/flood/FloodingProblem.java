package edu.iga.adi.sm.problems.flood;

import edu.iga.adi.sm.core.Problem;
import edu.iga.adi.sm.core.Solution;
import edu.iga.adi.sm.problems.IterativeProblem;
import edu.iga.adi.sm.problems.TimeModifier;

import java.util.Optional;

public class FloodingProblem implements IterativeProblem {

    private final Solution terrain;
    private final double delta;
    private final TimeModifier rainF;
    private final int steps;

    private int currentStep = 0;
    private double currentTime = 0;

    FloodingProblem(double delta, Solution terrain, TimeModifier rainF, int steps) {
        this.terrain = terrain;
        this.delta = delta;
        this.rainF = rainF;
        this.steps = steps;
    }

    @Override
    public Problem getInitialProblem() {
        return terrain::getValue;
    }

    @Override
    public Optional<Problem> getNextProblem(Solution solution) {
        currentTime += delta;
        currentStep++;
        return currentStep < steps ? Optional.of(getProblem(solution)) : Optional.empty();
    }

    private Problem getProblem(Solution solution) {
        return (x, y) -> solution.getValue(x, y) + delta * solution.getModifiedValue(x, y) + rainF.getAddedValue(x, y, currentTime);
    }

}
