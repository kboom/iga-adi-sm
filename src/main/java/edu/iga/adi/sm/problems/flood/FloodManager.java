package edu.iga.adi.sm.problems.flood;

import edu.iga.adi.sm.Solver;
import edu.iga.adi.sm.SolverConfiguration;
import edu.iga.adi.sm.SolverFactory;
import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.Problem;
import edu.iga.adi.sm.core.Solution;
import edu.iga.adi.sm.core.dimension.SolutionFactory;
import edu.iga.adi.sm.problems.IterativeProblem;
import edu.iga.adi.sm.problems.ProblemManager;
import edu.iga.adi.sm.results.CsvStringConverter;
import edu.iga.adi.sm.results.series.SolutionSeries;
import edu.iga.adi.sm.results.visualization.drawers.BitmapSolutionDrawer;
import edu.iga.adi.sm.results.visualization.drawers.SurfaceSolutionDrawer;
import edu.iga.adi.sm.results.visualization.drawers.surfaces.SolutionChangesSurfaceProvider;
import edu.iga.adi.sm.results.visualization.drawers.surfaces.SingleSurfaceProvider;
import edu.iga.adi.sm.results.visualization.drawers.surfaces.SurfaceFactory;
import edu.iga.adi.sm.results.visualization.viewers.StaticViewer;
import edu.iga.adi.sm.results.visualization.viewers.TimeLapseViewer;
import edu.iga.adi.sm.support.terrain.FunctionTerrainBuilder;
import edu.iga.adi.sm.support.terrain.Terraformer;
import edu.iga.adi.sm.support.terrain.TerrainProjectionProblem;
import edu.iga.adi.sm.support.terrain.processors.AdjustmentTerrainProcessor;
import edu.iga.adi.sm.support.terrain.processors.ChainedTerrainProcessor;
import edu.iga.adi.sm.support.terrain.processors.ToClosestTerrainProcessor;
import edu.iga.adi.sm.support.terrain.storage.FileTerrainStorage;
import edu.iga.adi.sm.support.terrain.storage.MapTerrainStorage;
import edu.iga.adi.sm.support.terrain.storage.TerrainStorage;
import edu.iga.adi.sm.support.terrain.support.Point2D;

public class FloodManager implements ProblemManager {

    private final SolverFactory solverFactory;

    private Solution terrainSolution;

    private SolverConfiguration config;

    public FloodManager(SolverConfiguration config, SolverFactory solverFactory) {
        this.config = config;
        this.solverFactory = solverFactory;
    }

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

                final double centerX = elementsX / 2;
                final double centerY = elementsY / 2;

                final double rainAreaX = elementsX / 4;
                final double rainAreaY = elementsY / 4;

                final int rainVolume = 500;

                return (x, y) -> super.getInitialProblem().getValue(x, y) +
                        (double) (((x > centerX - rainAreaX / 2) && (x < centerX + rainAreaX / 2)
                                && (y > centerY - rainAreaY / 2) && (y < centerY + rainAreaY / 2)) ? rainVolume : 0);
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
        drawSurfaces(solutionSeries);
//        drawBitmaps(solutionSeries);
    }

    private void drawSurfaces(SolutionSeries solutionSeries) {
        final SurfaceFactory surfaceFactory = SurfaceFactory.builder().mesh(solutionSeries.getMesh()).build();
        SolutionChangesSurfaceProvider rainAndTerrainSurfaces = SolutionChangesSurfaceProvider.builder()
                .surfaceFactory(surfaceFactory)
                .staticSolution(terrainSolution)
                .build();

//        StaticViewer.builder()
//                .name("Plain terrain surface")
//                .solution(terrainSolution)
//                .solutionDrawer(SurfaceSolutionDrawer.builder()
//                        .surfaceProvider(SingleSurfaceProvider.builder()
//                                .surfaceFactory(surfaceFactory)
//                                .build()).build())
//                .build().display();
//
//        StaticViewer.builder()
//                .name("Initial flooded surface")
//                .solution(solutionSeries.getSolutionAt(0))
//                .solutionDrawer(SurfaceSolutionDrawer.builder()
//                        .surfaceProvider(rainAndTerrainSurfaces).build())
//                .build().display();
//
//        StaticViewer.builder()
//                .name("Final flooded surface")
//                .solution(solutionSeries.getFinalSolution())
//                .solutionDrawer(SurfaceSolutionDrawer.builder()
//                        .surfaceProvider(rainAndTerrainSurfaces).build())
//                .build().display();

        TimeLapseViewer surfaceAnimationViewer = TimeLapseViewer.builder()
                .name("Surface flooding in time")
                .solutionDrawer(SurfaceSolutionDrawer.builder()
                        .surfaceProvider(rainAndTerrainSurfaces)
                        .build())
                .solutionSeries(solutionSeries)
                .downSampleRatio(config.getDownSampleRatio())
                .build();
        surfaceAnimationViewer.setVisible(true);
    }

    private void drawBitmaps(SolutionSeries solutionSeries) {
        StaticViewer.builder()
                .name("Terrain bitmap")
                .solution(terrainSolution)
                .solutionDrawer(BitmapSolutionDrawer.builder().build())
                .build().display();

        StaticViewer.builder()
                .name("Initial bitmap")
                .solution(solutionSeries.getSolutionAt(0))
                .solutionDrawer(BitmapSolutionDrawer.builder().build())
                .build().display();

        StaticViewer.builder()
                .name("Final bitmap")
                .solution(solutionSeries.getFinalSolution())
                .solutionDrawer(BitmapSolutionDrawer.builder().build())
                .build().display();

        TimeLapseViewer bitmapAnimationViewer = TimeLapseViewer.builder()
                .name("Bitmap solution in time")
                .solutionDrawer(BitmapSolutionDrawer.builder().build())
                .solutionSeries(solutionSeries)
                .downSampleRatio(config.getDownSampleRatio())
                .build();
        bitmapAnimationViewer.setVisible(true);
    }

    @Override
    public void setUp() {
        TerrainStorage inputTerrain = createTerrainInput();
        MapTerrainStorage outputTerrain = new MapTerrainStorage();

        Terraformer.builder()
                .inputStorage(inputTerrain)
                .outputStorage(outputTerrain)
                .terrainProcessor(
                        ChainedTerrainProcessor.startingFrom(AdjustmentTerrainProcessor.builder().center(new Point2D(config.getXOffset(), config.getYOffset())).scale(config.getScale()).build())
                                .withNext(new ToClosestTerrainProcessor())
                                .withNext(AdjustmentTerrainProcessor.builder().center(new Point2D(-config.getXOffset(), -config.getYOffset())).scale(1d / config.getScale()).build())
                )
                .build()
                .terraform(config.getMesh());

        Solver solver = solverFactory.createSolver(solution -> solution);
        terrainSolution = solver.solveProblem(new TerrainProjectionProblem(outputTerrain));
    }

    @Override
    public void tearDown() {

    }

    private TerrainStorage createTerrainInput() {
        if (config.getTerrainFile() != null) {
            return FileTerrainStorage.builder().inFilePath(config.getTerrainFile()).build();
        } else {
            return new MapTerrainStorage(FunctionTerrainBuilder.get()
                    .withMesh(config.getMesh())
                    .withFunction((x, y) -> {
                        if(x < 10 & y < 10) return 200d;
                        if(x >= 86 & y < 10) return 500d;
                        if(x < 10 & y >= 86) return 400d;
                        else return (double) (x + y);
                    })
                    .build());
        }
    }

}
