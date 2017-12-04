package edu.iga.adi.sm.problems;

import edu.iga.adi.sm.SolutionSeries;
import edu.iga.adi.sm.core.dimension.SolutionFactory;

public interface ProblemManager {

    IterativeProblem getProblem();

    SolutionFactory getSolutionFactory();

    void setUp();

    void processResults(SolutionSeries solutionSeries);

    void tearDown();

}
