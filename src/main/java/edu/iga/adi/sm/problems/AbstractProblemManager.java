package edu.iga.adi.sm.problems;

import edu.iga.adi.sm.SolverConfiguration;
import edu.iga.adi.sm.Task;
import edu.iga.adi.sm.core.dimension.SolutionFactory;
import edu.iga.adi.sm.core.direction.IntermediateSolution;
import edu.iga.adi.sm.results.series.SolutionSeries;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AbstractProblemManager implements ProblemManager {

    protected final SolverConfiguration config;

    @Override
    public void processResults(SolutionSeries solutionSeries) {
        if (config.isPlotting()) {
            plotResults(solutionSeries);
        }
    }

    @Override
    public final Task solverTask() {
        return configure(Task.builder().solutionFactory(solutionFactory())).build();
    }

    protected abstract Task.TaskBuilder configure(Task.TaskBuilder taskBuilder);

    private void plotResults(SolutionSeries solutionSeries) {
//        final String title = String.format("%s results", config.getProblemType());
//        StaticViewer results = new StaticViewer(title, solutionSeries.getFinalSolution());
//        results.setVisible(true);
    }

    private SolutionFactory solutionFactory() {
        return (solution, runInformation) -> new IntermediateSolution(config.getMesh(), solution.getRhs());
    }

    @Override
    public void setUp() {

    }

    @Override
    public void tearDown() {

    }

}
