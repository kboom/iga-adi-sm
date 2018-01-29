package edu.iga.adi.sm.problems.heat;

import edu.iga.adi.sm.SolverConfiguration;
import edu.iga.adi.sm.core.Solution;
import edu.iga.adi.sm.core.dimension.SolutionFactory;
import edu.iga.adi.sm.problems.IterativeProblem;
import edu.iga.adi.sm.problems.ProblemManager;
import edu.iga.adi.sm.results.CsvStringConverter;
import edu.iga.adi.sm.results.ImageStorage;
import edu.iga.adi.sm.results.series.SolutionSeries;
import edu.iga.adi.sm.results.visualization.drawers.FlatSolutionDrawer;
import edu.iga.adi.sm.results.visualization.drawers.SurfaceSolutionDrawer;
import edu.iga.adi.sm.results.visualization.drawers.surfaces.HeatSurfaceProvider;
import edu.iga.adi.sm.results.visualization.drawers.surfaces.SurfaceFactory;
import edu.iga.adi.sm.results.visualization.images.GreyscaleImageFactory;
import edu.iga.adi.sm.results.visualization.images.HeatImageFactory;
import edu.iga.adi.sm.results.visualization.viewers.StaticViewer;
import edu.iga.adi.sm.results.visualization.viewers.TimeLapseViewer;
import lombok.AllArgsConstructor;

import java.io.File;

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
        if (config.isStoringImages()) {
            storeImages(solutionSeries);
        }
    }

    private void displayResults(SolutionSeries solutionSeries) {
        drawTimelapses(solutionSeries);
        drawBitmaps(solutionSeries);
    }

    private void drawTimelapses(SolutionSeries solutionSeries) {
        final SurfaceFactory surfaceFactory = SurfaceFactory.builder().mesh(solutionSeries.getMesh()).build();
        TimeLapseViewer timeLapseViewer = TimeLapseViewer.builder()
                .solutionDrawer(SurfaceSolutionDrawer.builder()
                        .surfaceProvider(HeatSurfaceProvider.builder()
                                .surfaceFactory(surfaceFactory)
                                .build())
                        .build())
                .solutionSeries(solutionSeries)
                .build();
        timeLapseViewer.setVisible(true);
    }

    private void storeImages(SolutionSeries solutionSeries) {
        ImageStorage imageStorage = ImageStorage.builder().baseDir(
                new File(config.getImagesDir())
        ).build();

        imageStorage.saveImageAsTIFF("heat-start.tiff",
                new HeatImageFactory().createImageFor(solutionSeries.getFinalSolution())
        );


//
//
//
//
//
//        BufferedImage floodStart = GreyscaleImageFactory.builder()
//                .build()
//                .createImageFor(solutionSeries.getSolutionAt(0));
//
//        imageStorage.saveImageAsTIFF("heat-start.tiff", floodStart);
//
//        final int framePickingInterval = Math.max(1, config.getSteps() * (100 - config.getImagesFrequencyPercentage()) / 100);
//
//        IntStream.range(0, config.getSteps()).filter(i -> (i + framePickingInterval / 2) % framePickingInterval == 0)
//                .forEach(frame -> {
//                    BufferedImage heatMid = GreyscaleImageFactory.builder()
//                            .build()
//                            .createImageFor(solutionSeries.getSolutionAt(frame));
//                    imageStorage.saveImageAsTIFF(String.format("heat-mid-%d.tiff", frame), heatMid);
//                });
//
//        BufferedImage floodEnd = GreyscaleImageFactory.builder()
//                .build()
//                .createImageFor(solutionSeries.getFinalSolution());
//
//        imageStorage.saveImageAsTIFF("heat-end.tiff", floodEnd);
    }

    private void drawBitmaps(SolutionSeries solutionSeries) {
        StaticViewer.builder()
                .name("Initial image")
                .solution(solutionSeries.getSolutionAt(0))
                .solutionDrawer(FlatSolutionDrawer.builder()
                        .imageFactory(
                                GreyscaleImageFactory.builder().build()
                        )
                        .build())
                .build().display();

        StaticViewer.builder()
                .name("Final image")
                .solution(solutionSeries.getFinalSolution())
                .solutionDrawer(FlatSolutionDrawer.builder()
                        .imageFactory(
                                GreyscaleImageFactory.builder().build()
                        )
                        .build())
                .build().display();
    }

    @Override
    public void setUp() {

    }

    @Override
    public void tearDown() {

    }

}
