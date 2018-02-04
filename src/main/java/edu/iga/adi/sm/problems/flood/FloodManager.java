package edu.iga.adi.sm.problems.flood;

import com.sun.org.apache.xpath.internal.operations.Bool;
import edu.iga.adi.sm.Solver;
import edu.iga.adi.sm.SolverConfiguration;
import edu.iga.adi.sm.SolverFactory;
import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.Problem;
import edu.iga.adi.sm.core.Solution;
import edu.iga.adi.sm.core.dimension.SolutionFactory;
import edu.iga.adi.sm.core.direction.execution.ProductionExecutorFactory;
import edu.iga.adi.sm.problems.IterativeProblem;
import edu.iga.adi.sm.problems.ProblemManager;
import edu.iga.adi.sm.results.CsvStringConverter;
import edu.iga.adi.sm.results.ImageStorage;
import edu.iga.adi.sm.results.SnapshotSaver;
import edu.iga.adi.sm.results.series.SolutionSeries;
import edu.iga.adi.sm.results.visualization.drawers.FlatSolutionDrawer;
import edu.iga.adi.sm.results.visualization.drawers.jzy3d.Jzy3dChangesOverStaticSurfaceProvider;
import edu.iga.adi.sm.results.visualization.drawers.jzy3d.Jzy3dSolutionMapper;
import edu.iga.adi.sm.results.visualization.drawers.jzy3d.Jzy3dSurfaceFactory;
import edu.iga.adi.sm.results.visualization.drawers.jzy3d.Jzy3dSurfaceSolutionDrawer;
import edu.iga.adi.sm.results.visualization.images.FloodImageFactory;
import edu.iga.adi.sm.results.visualization.images.GreyscaleImageFactory;
import edu.iga.adi.sm.results.visualization.viewers.StaticViewer;
import edu.iga.adi.sm.results.visualization.viewers.TimeLapseViewer;
import edu.iga.adi.sm.support.terrain.FunctionTerrainBuilder;
import edu.iga.adi.sm.support.terrain.Terraformer;
import edu.iga.adi.sm.support.terrain.TerrainProjectionProblem;
import edu.iga.adi.sm.support.terrain.processors.ChainedTerrainProcessor;
import edu.iga.adi.sm.support.terrain.processors.ToClosestTerrainProcessor;
import edu.iga.adi.sm.support.terrain.processors.TranslationTerrainProcessor;
import edu.iga.adi.sm.support.terrain.processors.ZeroWaterLevelProcessor;
import edu.iga.adi.sm.support.terrain.storage.FileTerrainStorage;
import edu.iga.adi.sm.support.terrain.storage.MapTerrainStorage;
import edu.iga.adi.sm.support.terrain.storage.TerrainStorage;
import edu.iga.adi.sm.support.terrain.support.Point2D;
import lombok.RequiredArgsConstructor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.function.BiFunction;

@RequiredArgsConstructor
public class FloodManager implements ProblemManager {

    private final SolverFactory solverFactory;
    private final ProductionExecutorFactory productionExecutorFactory;
    private final SolverConfiguration config;

    private Solution terrainSolution;

    @Override
    public SolutionFactory getSolutionFactory() {
        return solution -> new FloodSolution(config.getMesh(), solution.getRhs(), terrainSolution.getRhs(), solution.metadata);
    }

    @Override
    public IterativeProblem getProblem() {
        final Mesh mesh = config.getMesh();
        return new FloodingProblem(config.getDelta(), terrainSolution, (x, y, time) ->
                (double) (((x > mesh.getElementsX() - 32) && (x < mesh.getElementsX() - 16)
                        && (y > mesh.getElementsY() - 32) && (y < mesh.getElementsY() - 16)
                ) ? 0 : 0), config.getSteps()) {

            @Override
            public Problem getInitialProblem() {
                final double elementsX = mesh.getElementsX();
                final double elementsY = mesh.getElementsY();

                final double centerX = mesh.getElementsX() / 2;
                final double centerY = mesh.getElementsY() / 2;

                final double radius = elementsX / 5;

                final int rainVolume = 1000;

                return (x, y) -> super.getInitialProblem().getValue(x, y) + (double) (((Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2)) <= Math.pow(radius, 2)) ? rainVolume : 0);


                // working fine for 768
//                final double centerX = 4.0 / 5.0 * mesh.getElementsX();
//                final double centerY = 0;
//
//
//                final double radius = elementsX / 5;
//
//                final int rainVolume = 1000;
//
//                return (x, y) -> super.getInitialProblem().getValue(x, y) + (double) (((Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2)) <= Math.pow(radius, 2)) ? rainVolume : 0);

//                final double centerX = mesh.getElementsX();
//                final double centerY = 0;
//
//                final BiFunction<Double, Double, Boolean> border = (x, y) -> {
//                    final double x1 = mesh.getElementsX();
//                    final double x2 = mesh.getElementsX() / 2;
//                    final double y1 = mesh.getElementsY() / 2;
//                    final double y2 = 0;
//
//                    double yl = (y2 - y1) / (x2 - x1) * (x - x1) + y1;
//
//                    return y < yl;
//                };
//
//                final int rainVolume = 0;
//
//                return (x, y) -> super.getInitialProblem().getValue(x, y) + (double) (border.apply(x,y) ? rainVolume : 0);



//                return (x, y) -> super.getInitialProblem().getValue(x, y) +
//                        (double) (((x > centerX - rainAreaX / 2) && (x < centerX + rainAreaX / 2)
//                                && (y > centerY - rainAreaY / 2) && (y < centerY + rainAreaY / 2)) ? rainVolume : 0);
            }

        };
    }

    @Override
    public void processResults(SolutionSeries solutionSeries) {
        if (config.isLogging()) {
            logResults(solutionSeries);
        }
        if (config.isPlotting()) {
            plotResults(solutionSeries);
        }
        if (config.isStoringImages()) {
            storeImages(solutionSeries);
        }
    }

    private void logResults(SolutionSeries solutionSeries) {
        Solution finalSolution = solutionSeries.getFinalSolution();
        CsvStringConverter csvStringConverter = CsvStringConverter.builder().build();
        System.out.println("------------------- TERRAIN SOLUTION --------------------");
        System.out.println(csvStringConverter.convertToCsv(terrainSolution.getSolutionGrid()));
        System.out.println("------------------- FINAL SIM SOLUTION --------------------");
        System.out.println(csvStringConverter.convertToCsv(finalSolution.getSolutionGrid()));
    }

    private void plotResults(SolutionSeries solutionSeries) {
        drawTimelapses(solutionSeries);
        drawImages(solutionSeries);
    }

    private void drawTimelapses(SolutionSeries solutionSeries) {
        final Jzy3dSurfaceFactory surfaceFactory = Jzy3dSurfaceFactory.builder().mesh(solutionSeries.getMesh()).build();
        Jzy3dChangesOverStaticSurfaceProvider rainAndTerrainSurfaces = Jzy3dChangesOverStaticSurfaceProvider.builder()
                .surfaceFactory(surfaceFactory)
                .staticSolution(terrainSolution)
                .build(); // this causes the problem

        TimeLapseViewer timeLapsViewer = TimeLapseViewer.builder()
                .name("Flood visual simulation")
                .downSampleRatio(config.getDownSampleRatio())
                .solutionDrawer(Jzy3dSurfaceSolutionDrawer.builder()
                        .jzy3dSurfaceProvider(rainAndTerrainSurfaces)
                        .build())
                .solutionSeries(solutionSeries)
                .build();
        timeLapsViewer.setVisible(true);
    }

    private void drawImages(SolutionSeries solutionSeries) {
        StaticViewer.builder()
                .name("Terrain bitmap")
                .solution(terrainSolution)
                .solutionDrawer(FlatSolutionDrawer.builder().build())
                .build().display();

        StaticViewer.builder()
                .name("Initial bitmap")
                .solution(solutionSeries.getSolutionAt(0))
                .solutionDrawer(FlatSolutionDrawer.builder().build())
                .build().display();

        StaticViewer.builder()
                .name("Mid bitmap")
                .solution(solutionSeries.getSolutionAt(solutionSeries.getTimeStepCount() / 2))
                .solutionDrawer(FlatSolutionDrawer.builder().build())
                .build().display();

        StaticViewer.builder()
                .name("Final bitmap")
                .solution(solutionSeries.getFinalSolution())
                .solutionDrawer(FlatSolutionDrawer.builder()
                        .imageFactory(
                                GreyscaleImageFactory.builder().mapper((x, y, z) -> z - terrainSolution.getValue(x, y)).build()
                        )
                        .build())
                .build().display();

        TimeLapseViewer bitmapAnimationViewer = TimeLapseViewer.builder()
                .name("Bitmap solution in time")
                .solutionDrawer(FlatSolutionDrawer.builder()
                        .imageFactory(
                                GreyscaleImageFactory.builder().mapper((x, y, z) -> z - terrainSolution.getValue(x, y)).build()
                        )
                        .build())
                .solutionSeries(solutionSeries)
                .downSampleRatio(config.getDownSampleRatio())
                .build();
        bitmapAnimationViewer.setVisible(true);
    }

    private void storeImages(SolutionSeries solutionSeries) {
        ImageStorage greyscaleImageStorage = ImageStorage.builder().baseDir(
                new File(config.getImagesDir())
        ).imageType(BufferedImage.TYPE_BYTE_GRAY).build();

        ImageStorage colorImageStorage = ImageStorage.builder().baseDir(
                new File(config.getImagesDir())
        ).imageType(BufferedImage.TYPE_INT_ARGB).build();

        SnapshotSaver.builder()
                .imageFactory(GreyscaleImageFactory.builder().build())
                .imageStorage(greyscaleImageStorage)
                .frequencyPercentage(config.getImagesFrequencyPercentage())
                .nameTemplate("flood-bitmap-%s.tiff")
                .build()
                .storeSnapshots(solutionSeries);

        SnapshotSaver.builder()
                .imageFactory(FloodImageFactory.builder().terrainSolution(terrainSolution).build())
                .imageStorage(colorImageStorage)
                .frequencyPercentage(config.getImagesFrequencyPercentage())
                .nameTemplate("flood-3d-%s.tiff")
                .build()
                .storeSnapshots(solutionSeries);

        SnapshotSaver.builder()
                .imageFactory(GreyscaleImageFactory.builder()
                        .mapper((x, y, z) -> z - terrainSolution.getValue(x, y)).build()
                )
                .imageStorage(greyscaleImageStorage)
                .frequencyPercentage(config.getImagesFrequencyPercentage())
                .nameTemplate("flood-depth-%s.tiff")
                .build()
                .storeSnapshots(solutionSeries);
    }

    @Override
    public void setUp() {
        TerrainStorage inputTerrain = createTerrainInput();
        MapTerrainStorage outputTerrain = new MapTerrainStorage();

        TranslationTerrainProcessor translator = TranslationTerrainProcessor.builder()
                .center(new Point2D(config.getXOffset(), config.getYOffset())).scale(config.getScale()).build();

        Terraformer.builder()
                .inputStorage(inputTerrain)
                .outputStorage(outputTerrain)
                .terrainProcessor(
                        ChainedTerrainProcessor.startingFrom(translator)
                                .withNext(new ToClosestTerrainProcessor())
                                .withNext(translator.reverse())
                                .withNext(new ZeroWaterLevelProcessor())
                )
                .build()
                .terraform(config.getMesh());

        Solver solver = solverFactory.createSolver(solution -> solution, productionExecutorFactory);
        terrainSolution = solver.solveProblem(new TerrainProjectionProblem(outputTerrain));
    }

    @Override
    public void tearDown() {

    }

    private TerrainStorage createTerrainInput() {
        if (config.getTerrainFile() != null) {
            return FileTerrainStorage.builder().inFilePath(config.getTerrainFile()).build();
        } else {
            Mesh mesh = config.getMesh();
            return new MapTerrainStorage(FunctionTerrainBuilder.get()
                    .withMesh(mesh)
                    .withFunction((x, y) -> {
                        if(x < mesh.getElementsX() / 4 & y < mesh.getElementsY() / 4) return 100d;
                        else return (double) (x + y);
                    })
                    .build());
        }
    }

}
