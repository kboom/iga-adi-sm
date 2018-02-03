package edu.iga.adi.sm.problems.heat;

import edu.iga.adi.sm.SolverConfiguration;
import edu.iga.adi.sm.core.Solution;
import edu.iga.adi.sm.core.dimension.SolutionFactory;
import edu.iga.adi.sm.problems.IterativeProblem;
import edu.iga.adi.sm.problems.ProblemManager;
import edu.iga.adi.sm.results.CsvStringConverter;
import edu.iga.adi.sm.results.ImageStorage;
import edu.iga.adi.sm.results.SnapshotSaver;
import edu.iga.adi.sm.results.series.SolutionSeries;
import edu.iga.adi.sm.results.visualization.drawers.FlatSolutionDrawer;
import edu.iga.adi.sm.results.visualization.drawers.jzy3d.Jzy3dSurfaceSolutionDrawer;
import edu.iga.adi.sm.results.visualization.drawers.jzy3d.Jzy3dHeatSurfaceProvider;
import edu.iga.adi.sm.results.visualization.drawers.jzy3d.Jzy3dSurfaceFactory;
import edu.iga.adi.sm.results.visualization.images.GreyscaleImageFactory;
import edu.iga.adi.sm.results.visualization.images.HeatImageFactory;
import edu.iga.adi.sm.results.visualization.viewers.StaticViewer;
import edu.iga.adi.sm.results.visualization.viewers.TimeLapseViewer;
import lombok.AllArgsConstructor;

import java.awt.image.BufferedImage;
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
        drawImages(solutionSeries);
    }

    private void drawTimelapses(SolutionSeries solutionSeries) {
        final Jzy3dSurfaceFactory surfaceFactory = Jzy3dSurfaceFactory.builder().mesh(solutionSeries.getMesh()).build();
        TimeLapseViewer timeLapseViewer = TimeLapseViewer.builder()
                .name("Heat visual simulation")
                .downSampleRatio(config.getDownSampleRatio())
                .solutionDrawer(Jzy3dSurfaceSolutionDrawer.builder()
                        .jzy3dSurfaceProvider(Jzy3dHeatSurfaceProvider.builder()
                                .surfaceFactory(surfaceFactory)
                                .build())
                        .build())
                .solutionSeries(solutionSeries)
                .build();
        timeLapseViewer.setVisible(true);
    }

    private void storeImages(SolutionSeries solutionSeries) {
        final ImageStorage rgbImageStorage = ImageStorage.builder().baseDir(
                new File(config.getImagesDir())
        ).imageType(BufferedImage.TYPE_INT_ARGB).build();

        SnapshotSaver.builder()
                .imageFactory(new HeatImageFactory())
                .imageStorage(rgbImageStorage)
                .frequencyPercentage(config.getImagesFrequencyPercentage())
                .nameTemplate("heat-%s.tiff")
                .build()
                .storeSnapshots(solutionSeries);

        SnapshotSaver.builder()
                .imageFactory(GreyscaleImageFactory.builder().build())
                .imageStorage(rgbImageStorage)
                .frequencyPercentage(config.getImagesFrequencyPercentage())
                .nameTemplate("heat-greyscale-%s.tiff")
                .build()
                .storeSnapshots(solutionSeries);
    }

    private void drawImages(SolutionSeries solutionSeries) {
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
