package edu.iga.adi.sm.results.series;

import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.Solution;
import lombok.AllArgsConstructor;

import java.util.stream.Stream;

@AllArgsConstructor
public abstract class SolutionSeriesAdapter implements SolutionSeries {

    private final SolutionSeries delegate;

    @Override
    public Solution getFinalSolution() {
        return delegate.getFinalSolution();
    }

    @Override
    public int getTimeStepCount() {
        return delegate.getTimeStepCount();
    }

    @Override
    public Solution getSolutionAt(int timeStep) {
        return delegate.getSolutionAt(timeStep);
    }

    @Override
    public Mesh getMesh() {
        return delegate.getMesh();
    }

    @Override
    public Stream<Solution> getSubsequentSolutions() {
        return delegate.getSubsequentSolutions();
    }

}
