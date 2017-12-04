package edu.iga.adi.sm.results.visualization;

import edu.iga.adi.sm.SolutionSeries;

public class TransientSolutionMapper extends ParameterizedMapper {

    private final SolutionSeries solutionSeries;

    public TransientSolutionMapper(SolutionSeries solutionSeries) {
        this.solutionSeries = solutionSeries;
    }

    public static TransientSolutionMapper fromSolution(SolutionSeries solutionSeries) {
        return new TransientSolutionMapper(solutionSeries);
    }

    @Override
    protected double fAtTimeStep(double x, double y, int timeStep) {
        return solutionSeries.getSolutionAt(timeStep).getValue(x, y);
    }

}
