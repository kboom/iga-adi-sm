package edu.iga.adi.sm.problems.flood;

import edu.iga.adi.sm.SolutionSeries;
import edu.iga.adi.sm.Solver;
import edu.iga.adi.sm.SolverConfiguration;
import edu.iga.adi.sm.SolverFactory;
import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.Problem;
import edu.iga.adi.sm.core.Solution;
import edu.iga.adi.sm.core.dimension.SolutionFactory;
import edu.iga.adi.sm.problems.IterativeProblem;
import edu.iga.adi.sm.problems.ProblemManager;
import edu.iga.adi.sm.results.CsvConverter;
import edu.iga.adi.sm.results.visualization.drawers.BitmapSolutionDrawer;
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
        return solution -> new FloodSolution(config.getMesh(), solution.getRhs(), terrainSolution.getRhs());
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
                return (x, y) -> super.getInitialProblem ().getValue(x, y) +
                        (double) (((x > mesh.getElementsX() - 32) && (x < mesh.getElementsX() - 16)
                        && (y > mesh.getElementsY() - 32) && (y < mesh.getElementsY() - 16)) ? 100 : 0);
            }

        };


//                new FloodingProblem(config.getDelta(), terrainSolution, (x, y, time) ->
//                (double) (((x > mesh.getElementsX() - 32) && (x < mesh.getElementsX() - 16)
//                        && (y > mesh.getElementsY() - 32) && (y < mesh.getElementsY() - 16)
//                ) ? 0 : 0), config.getSteps());
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
        CsvConverter csvConverter = CsvConverter.builder().build();
        System.out.println("------------------- TERRAIN SOLUTION --------------------");
        System.out.println(csvConverter.convertToCsv(terrainSolution.getSolutionGrid()));
        System.out.println("------------------- FINAL SIM SOLUTION --------------------");
        System.out.println(csvConverter.convertToCsv(finalSolution.getSolutionGrid()));
    }

    private void plotResults(SolutionSeries solutionSeries) {
        solutionSeries.addModifier((x, y) -> -terrainSolution.getValue(x, y));

        TimeLapseViewer bitmapAnimationViewer = TimeLapseViewer.builder()
                .solutionDrawer(BitmapSolutionDrawer.builder().build())
                .solutionSeries(solutionSeries)
                .build();
        bitmapAnimationViewer.setVisible(true);

//        StaticViewer terrainView = StaticViewer.builder()
//                .name("Original solution")
//                .solution(terrainSolution)
//                .solutionDrawer()
//                .build();
//        terrainView.setVisible(true);


//        TimeLapseViewer surfaceAnimationViewer = TimeLapseViewer.builder()
//                .solutionDrawer(SurfaceSolutionDrawer.builder()
//                        .mesh(solutionSeries.getMesh())
//                        .build())
//                .solutionSeries(solutionSeries)
//                .build();
//        surfaceAnimationViewer.setVisible(true);
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
                    .withFunction((x, y) -> (double) (x + y))
                    .build());
        }
    }

}
