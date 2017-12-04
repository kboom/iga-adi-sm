package edu.iga.adi.sm.results.visualization;

import org.jzy3d.plot3d.builder.Mapper;

public abstract class ParameterizedMapper extends Mapper {

    private int timeStep;

    @Override
    public final double f(double x, double y) {
        return fAtTimeStep(x, y, timeStep);
    }

    public void setStep(int timeStep) {
        this.timeStep = timeStep;
    }

    protected abstract double fAtTimeStep(double x, double y, int timeStep);

}
