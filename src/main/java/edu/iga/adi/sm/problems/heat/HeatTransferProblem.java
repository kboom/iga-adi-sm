package edu.iga.adi.sm.problems.heat;

import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.Problem;
import edu.iga.adi.sm.core.Solution;
import edu.iga.adi.sm.problems.IterativeProblem;

import java.util.Optional;

public final class HeatTransferProblem implements IterativeProblem {

    private final Mesh mesh;
    private final int problemSize;
    private final double delta;
    private final int steps;

    private int currentStep = 0;

    HeatTransferProblem(double delta, Mesh mesh, int problemSize, int steps) {
        this.mesh = mesh;
        this.delta = delta;
        this.problemSize = problemSize;
        this.steps = steps;
    }

    @Override
    public Problem getInitialProblem() {
        return (x, y) -> {
            double dist = (x - mesh.getCenterX()) * (x - mesh.getCenterX())
                    + (y - mesh.getCenterY()) * (y - mesh.getCenterY());

            return dist < problemSize ? problemSize - dist : 0;
        };
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