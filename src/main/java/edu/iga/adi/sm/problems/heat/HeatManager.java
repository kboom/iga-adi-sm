package edu.iga.adi.sm.problems.heat;

import edu.iga.adi.sm.SolverConfiguration;
import edu.iga.adi.sm.core.Solution;
import edu.iga.adi.sm.core.dimension.SolutionFactory;
import edu.iga.adi.sm.problems.IterativeProblem;
import edu.iga.adi.sm.problems.ProblemManager;
import edu.iga.adi.sm.results.CsvStringConverter;
import edu.iga.adi.sm.results.series.SolutionSeries;
import edu.iga.adi.sm.results.visualization.drawers.SurfaceSolutionDrawer;
import edu.iga.adi.sm.results.visualization.drawers.surfaces.SingleSurfaceProvider;
import edu.iga.adi.sm.results.visualization.drawers.surfaces.SurfaceFactory;
import edu.iga.adi.sm.results.visualization.viewers.TimeLapseViewer;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class HeatManager implements ProblemManager {

    private final SolverConfiguration config;

    @Override
    public IterativeProblem getProblem() {
        return new HeatTransferProblem(
                config.getDelta(),
                config.getMesh(),
                config.getProblemSize(),
                config.getSteps()
        );
    }

    @Override
    public SolutionFactory getSolutionFactory() {
        return solution -> new HeatSolution(config.getMesh(), solution.getRhs(), solution.metadata);
    }

    @Override
    public void processResults(SolutionSeries solutionSeries) {
        if (config.isLogging()) {
            Solution finalSolution = solutionSeries.getFinalSolution();
            CsvStringConverter csvStringConverter = CsvStringConverter.builder().build();
            System.out.println(csvStringConverter.convertToCsv(finalSolution.getSolutionGrid()));
        }
        if (config.isPlotting()) {
            displayResults(solutionSeries);
        }
    }

    private void displayResults(SolutionSeries solutionSeries) {
        final SurfaceFactory surfaceFactory = SurfaceFactory.builder().mesh(solutionSeries.getMesh()).build();
        TimeLapseViewer timeLapseViewer = TimeLapseViewer.builder()
                .solutionDrawer(SurfaceSolutionDrawer.builder()
                        .surfaceProvider(SingleSurfaceProvider.builder()
                                .surfaceFactory(surfaceFactory)
                                .build())
                        .build())
                .solutionSeries(solutionSeries)
                .build();
        timeLapseViewer.setVisible(true);
    }

    @Override
    public void setUp() {

    }

    @Override
    public void tearDown() {

    }

}
