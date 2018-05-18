package edu.iga.adi.sm.problems;

import edu.iga.adi.sm.SolverConfiguration;
import edu.iga.adi.sm.SolverFactory;
import edu.iga.adi.sm.SolverLauncher;
import edu.iga.adi.sm.TimeLogger;
import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.Solution;
import edu.iga.adi.sm.core.dimension.SolutionFactory;
import edu.iga.adi.sm.core.direction.execution.ProductionExecutorFactory;
import edu.iga.adi.sm.loggers.ConsoleSolutionLogger;
import edu.iga.adi.sm.loggers.NoopSolutionLogger;
import edu.iga.adi.sm.results.CsvStringConverter;
import edu.iga.adi.sm.results.series.FromStorageSolutionSeries;
import edu.iga.adi.sm.results.series.SolutionSeries;
import edu.iga.adi.sm.results.storage.CompressResultsStorageProcessor;
import edu.iga.adi.sm.results.storage.FileSolutionStorage;
import edu.iga.adi.sm.results.storage.InMemorySolutionStorage;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

class AbstractProblemTest {

    ProblemManagerTestResults launchSolver(SolverConfiguration solverConfiguration) {
        final ProductionExecutorFactory productionExecutorFactory = new ProductionExecutorFactory(solverConfiguration);
        final TimeLogger timeLogger = new TimeLogger();

        final Mesh mesh = solverConfiguration.getMesh();

        final SolverFactory solverFactory = new SolverFactory(
                mesh,
                solverConfiguration.isLogging() ? new ConsoleSolutionLogger(mesh) : new NoopSolutionLogger(),
                timeLogger
        );

        ProblemManagerProxy problemManagerProxy = new ProblemManagerProxy(LocalProblemManagerFactory.builder()
                .mesh(mesh)
                .solverConfiguration(solverConfiguration)
                .solverFactory(solverFactory)
                .productionExecutorFactory(productionExecutorFactory)
                .build()
                .createProblemManager());

        SolverLauncher.builder()
                .solverConfiguration(solverConfiguration)
                .problemManagerFactory(() -> problemManagerProxy)
                .productionExecutorFactory(productionExecutorFactory)
                .timeLogger(timeLogger)
                .solutionStorage(initializeStorage(solverConfiguration))
                .solverFactory(solverFactory)
                .build()
                .launch();

        return problemManagerProxy;
    }

    private static FileSolutionStorage<Solution> initializeStorage(SolverConfiguration solverConfiguration) {
        File solutionDirectory = new File(solverConfiguration.getResultFile());
        File solutionZip = new File(solverConfiguration.getResultFile() + ".zip");
        return FileSolutionStorage.builder()
                .solutionDirectory(solutionDirectory)
                .storageProcessor(
                        CompressResultsStorageProcessor.builder()
                                .archiveFile(solutionZip)
                                .pack(solverConfiguration.isStoring())
                                .unpack(solverConfiguration.isRetrieve())
                                .build()
                )
                .build();
    }

    final String readTestFile(String path) throws Exception {
        return new String(Files.readAllBytes(Paths.get(ClassLoader.getSystemResource(path).toURI())));
    }

    public interface ProblemManagerTestResults {

        String getResultAsCsvString();

        default List<String> getAllResults() {
            return Collections.emptyList();
        }

    }

    public static class ProblemManagerProxy implements ProblemManager, ProblemManagerTestResults {

        private final ProblemManager delegate;

        private SolutionSeries results;

        public ProblemManagerProxy(ProblemManager delegate) {
            this.delegate = delegate;
        }

        @Override
        public IterativeProblem getProblem() {
            return delegate.getProblem();
        }

        @Override
        public SolutionFactory getSolutionFactory() {
            return delegate.getSolutionFactory();
        }

        @Override
        public void processResults(SolutionSeries solutionSeries) {
            // safe copy the results because they may become unavailable before assertions
            InMemorySolutionStorage<Solution> safeStorage = new InMemorySolutionStorage<>(
                    solutionSeries.getSubsequentSolutions()
                            .collect(Collectors.toList()));
            
            results = FromStorageSolutionSeries.builder()
                    .mesh(solutionSeries.getMesh())
                    .solutionCount(solutionSeries.getTimeStepCount())
                    .solutionStorage(safeStorage)
                    .build();
        }

        @Override
        public void setUp() {
            delegate.setUp();
        }

        @Override
        public void tearDown() {
            delegate.tearDown();
        }

        @Override
        public String getResultAsCsvString() {
            return CsvStringConverter.builder().build().convertToCsv(results.getFinalSolution().getSolutionGrid());
        }

        @Override
        public List<String> getAllResults() {
            final CsvStringConverter toCsvPrinter = CsvStringConverter.builder().build();
            return results.getSubsequentSolutions()
                    .map(Solution::getSolutionGrid)
                    .map(toCsvPrinter::convertToCsv)
                    .collect(Collectors.toList());
        }

    }

}
