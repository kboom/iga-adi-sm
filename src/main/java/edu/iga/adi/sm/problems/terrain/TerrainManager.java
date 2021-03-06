package edu.iga.adi.sm.problems.terrain;

import Jama.Matrix;
import Jama.SingularValueDecomposition;
import edu.iga.adi.sm.SolverConfiguration;
import edu.iga.adi.sm.Task;
import edu.iga.adi.sm.TimeMethodType;
import edu.iga.adi.sm.core.Solution;
import edu.iga.adi.sm.core.dimension.SolutionFactory;
import edu.iga.adi.sm.core.direction.IntermediateSolution;
import edu.iga.adi.sm.problems.IterativeProblem;
import edu.iga.adi.sm.problems.ProblemManager;
import edu.iga.adi.sm.results.CsvStringConverter;
import edu.iga.adi.sm.results.series.SolutionSeries;
import edu.iga.adi.sm.results.visualization.drawers.FlatSolutionDrawer;
import edu.iga.adi.sm.results.visualization.images.TerrainImageFactory;
import edu.iga.adi.sm.results.visualization.viewers.StaticViewer;
import edu.iga.adi.sm.support.MatrixUtils;
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

public final class TerrainManager implements ProblemManager {

    private final MapTerrainStorage outputTerrain = new MapTerrainStorage();
    private final SolverConfiguration config;
    private SingularValueDecomposition svd;

    public TerrainManager(SolverConfiguration solverConfiguration) {
        this.config = solverConfiguration;
    }

    @Override
    public void processResults(SolutionSeries solutionSeries) {
        Solution finalSolution = solutionSeries.getFinalSolution();
        this.svd = computeSvd(finalSolution);
        if (config.isLogging()) {
            logResults(finalSolution);
        }
        if (config.isPlotting()) {
            plotResults(finalSolution);
        }
    }

    private void logResults(Solution finalSolution) {
        CsvStringConverter csvStringConverter = CsvStringConverter.builder().build();
        System.out.println("------------------- TERRAIN SOLUTION --------------------");
        System.out.println(csvStringConverter.convertToCsv(finalSolution.getSolutionGrid()));
    }

    private void plotResults(Solution finalSolution) {
        displayOriginalSolution(finalSolution);
        displayOriginalSolutionBitmap(finalSolution);
        displayRankApproximationsBitmaps(finalSolution);
    }

    private void displayOriginalSolution(Solution terrainSolution) {
        StaticViewer.builder()
                .name("Original solution")
                .solution(terrainSolution)
                .solutionDrawer(FlatSolutionDrawer.builder()
                        .imageFactory(TerrainImageFactory.builder().build())
                        .build())
                .build().display();
    }

    private void displayOriginalSolutionBitmap(Solution terrainSolution) {
        StaticViewer.builder()
                .name("Original solution bitmap")
                .solution(terrainSolution)
                .solutionDrawer(FlatSolutionDrawer.builder().build())
                .build().display();
    }

    private void displayRankApproximationsBitmaps(Solution terrainSolution) {
        config.getRanks().forEach(rank -> {
            final Solution svdApproximation = getSvdRankedSolution(rank);
            final double error = svdApproximation.squaredDifference(terrainSolution);
            StaticViewer.builder()
                    .name(String.format("SVD %d solution (error %.5f)", rank, error))
                    .solution(terrainSolution)
                    .solutionDrawer(FlatSolutionDrawer.builder().build())
                    .build().display();
        });
    }

    @Override
    public Task solverTask() {
        return Task.builder()
                .problem(problem())
                .solutionFactory(solutionFactory())
                .timeMethodType(TimeMethodType.EXPLICIT)
                .build();
    }

    private IterativeProblem problem() {
        return new TerrainProjectionProblem(outputTerrain);
    }

    private SolutionFactory solutionFactory() {
        return (solution, runInformation) -> new IntermediateSolution(config.getMesh(), solution.getRhs());
    }

    @Override
    public void setUp() {
        prepareTerrain();
    }

    @Override
    public void tearDown() {

    }

    private void prepareTerrain() {
        TerrainStorage inputTerrain = createTerrainInput();
        Terraformer.builder()
                .inputStorage(inputTerrain)
                .outputStorage(outputTerrain)
                .terrainProcessor(
                        ChainedTerrainProcessor.startingFrom(TranslationTerrainProcessor.builder().center(new Point2D(config.getXOffset(), config.getYOffset())).scale(config.getScale()).build())
                                .withNext(new ToClosestTerrainProcessor())
                                .withNext(TranslationTerrainProcessor.builder().center(new Point2D(-config.getXOffset(), -config.getYOffset())).scale(1d / config.getScale()).build())
                                .withNext(new ZeroWaterLevelProcessor())
                )
                .build()
                .terraform(config.getMesh());
    }

    private SingularValueDecomposition computeSvd(Solution terrainSolution) {
        return new SingularValueDecomposition(new Matrix(
                MatrixUtils.withoutPadding(terrainSolution.getRhs())
        ));
    }

    private Solution getSvdRankedSolution(int rank) {
        Matrix fullApproximation = svd.getU().times(svd.getS()).times(svd.getV().transpose());

        double[] singluarValues = svd.getSingularValues();

        final Matrix rankedMatrix = new Matrix(fullApproximation.getRowDimension(), fullApproximation.getColumnDimension());
        for (int i = 0; i < rank; i++) {
            rankedMatrix.set(i, i, singluarValues[i]);
        }

        Matrix mat = svd.getU().times(rankedMatrix).times(svd.getV().transpose());
        return new IntermediateSolution(config.getMesh(), MatrixUtils.withPadding(mat.getArray()));
    }

    private TerrainStorage createTerrainInput() {
        if (config.getTerrainFile() != null) {
            return FileTerrainStorage.builder().inFilePath(config.getTerrainFile()).build();
        } else {
            return new MapTerrainStorage(FunctionTerrainBuilder.get()
                    .withMesh(config.getMesh())
                    .withFunction((x, y) -> (double) y)
                    .build());
        }
    }

}
