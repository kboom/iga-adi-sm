package edu.iga.adi.sm.problems;

import edu.iga.adi.sm.SolutionSeries;
import edu.iga.adi.sm.SolverConfiguration;
import edu.iga.adi.sm.core.dimension.SolutionFactory;
import edu.iga.adi.sm.core.direction.IntermediateSolution;
import edu.iga.adi.sm.results.visualization.ResultsSnapshot;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AbstractProblemManager implements ProblemManager {

    protected final SolverConfiguration config;

    @Override
    public abstract IterativeProblem getProblem();

    @Override
    public SolutionFactory getSolutionFactory() {
        return solution -> new IntermediateSolution(config.getMesh(), solution.getRhs());
    }

    @Override
    public void processResults(SolutionSeries solutionSeries) {
        if (config.isPlotting()) {
            plotResults(solutionSeries);
        }
    }

    private void plotResults(SolutionSeries solutionSeries) {
        final String title = String.format("%s results", config.getProblemType());
        ResultsSnapshot results = new ResultsSnapshot(title, solutionSeries.getFinalSolution());
        results.setVisible(true);
    }

    @Override
    public void setUp() {

    }

    @Override
    public void tearDown() {

    }

}
