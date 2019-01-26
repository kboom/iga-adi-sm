package edu.iga.adi.sm.problems.projection;

import edu.iga.adi.sm.SolverConfiguration;
import edu.iga.adi.sm.Task;
import edu.iga.adi.sm.TimeMethodType;
import edu.iga.adi.sm.core.Problem;
import edu.iga.adi.sm.problems.AbstractProblemManager;
import edu.iga.adi.sm.problems.IterativeProblem;
import edu.iga.adi.sm.problems.SingleRunProblem;
import edu.iga.adi.sm.results.series.SolutionSeries;
import edu.iga.adi.sm.results.visualization.drawers.FlatSolutionDrawer;
import edu.iga.adi.sm.results.visualization.images.HeatImageFactory;
import edu.iga.adi.sm.results.visualization.viewers.StaticViewer;

public final class ProjectionProblemManager extends AbstractProblemManager {

    public ProjectionProblemManager(SolverConfiguration config) {
        super(config);
    }

    @Override
    protected Task.TaskBuilder configure(Task.TaskBuilder taskBuilder) {
        return taskBuilder.problem(problem()).timeMethodType(TimeMethodType.EXPLICIT);
    }

    @Override
    public void processResults(SolutionSeries solutionSeries) {
        if(config.isPlotting()) {
            drawImages(solutionSeries);
        }
    }

    private void drawImages(SolutionSeries solutionSeries) {
        StaticViewer.builder()
                .name("Projection")
                .solution(solutionSeries.getSolutionAt(0))
                .solutionDrawer(FlatSolutionDrawer.builder()
                        .imageFactory(new HeatImageFactory())
                        .build())
                .build().display();
    }

        private IterativeProblem problem() {
        return new SingleRunProblem() {

            @Override
            protected Problem getProblem() {
                return (x, y) -> x + y;
            }

        };
    }

}
