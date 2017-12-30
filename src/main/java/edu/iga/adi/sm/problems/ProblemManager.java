package edu.iga.adi.sm.problems;

import edu.iga.adi.sm.core.dimension.SolutionFactory;
import edu.iga.adi.sm.results.series.SolutionSeries;

public interface ProblemManager {

    IterativeProblem getProblem();

    SolutionFactory getSolutionFactory();

    void setUp();

    void processResults(SolutionSeries solutionSeries);

    void tearDown();

}
