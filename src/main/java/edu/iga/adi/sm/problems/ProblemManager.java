package edu.iga.adi.sm.problems;

import edu.iga.adi.sm.Task;
import edu.iga.adi.sm.results.series.SolutionSeries;

public interface ProblemManager {

    Task solverTask();

    void setUp();

    void processResults(SolutionSeries solutionSeries);

    void tearDown();

}
