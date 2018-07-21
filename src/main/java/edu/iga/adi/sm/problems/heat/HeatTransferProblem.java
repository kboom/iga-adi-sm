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
            int heat = 1;
            int radius = Double.valueOf(Math.pow(mesh.getElementsX() / 4, 2)).intValue();

            int aX = mesh.getElementsX() - mesh.getElementsX() / 2;
            int aY = mesh.getElementsY() - mesh.getElementsY() / 2;

            double distA = Math.pow(x - aX, 2) + Math.pow(y - aY, 2);
            if(distA < radius) return heat;

//            int bX = diameterB;
//            int bY = diameterB;
//
//            double distB = Math.pow(x - bX, 2) + Math.pow(y - bY, 2);
//            if(distB < radius) return heat;

            return 0;
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