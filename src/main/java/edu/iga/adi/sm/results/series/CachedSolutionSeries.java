package edu.iga.adi.sm.results.series;

import edu.iga.adi.sm.core.Solution;

import java.util.WeakHashMap;

public class CachedSolutionSeries extends SolutionSeriesAdapter {

    private WeakHashMap<Integer, Solution> solutionCache = new WeakHashMap<>();

    public CachedSolutionSeries(SolutionSeries delegate) {
        super(delegate);
    }

    @Override
    public Solution getFinalSolution() {
        return getSolutionAt(getTimeStepCount() - 1);
    }

    @Override
    public Solution getSolutionAt(int timeStep) {
        return solutionCache.computeIfAbsent(timeStep, super::getSolutionAt);
    }

}
