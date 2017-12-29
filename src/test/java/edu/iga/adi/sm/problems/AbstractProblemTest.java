package edu.iga.adi.sm.problems;

import edu.iga.adi.sm.*;
import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.Solution;
import edu.iga.adi.sm.core.dimension.SolutionFactory;
import edu.iga.adi.sm.core.direction.execution.ProductionExecutorFactory;
import edu.iga.adi.sm.loggers.ConsoleSolutionLogger;
import edu.iga.adi.sm.loggers.NoopSolutionLogger;
import edu.iga.adi.sm.results.CsvStringConverter;
import edu.iga.adi.sm.results.storage.CompressResultsStorageProcessor;
import edu.iga.adi.sm.results.storage.FileSolutionStorage;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

class AbstractProblemTest {

    ProblemManagerTestResults launchSolver(SolverConfiguration solverConfiguration) {
        final ProductionExecutorFactory productionExecutorFactory = new ProductionExecutorFactory();
        final TimeLogger timeLogger = new TimeLogger();

        productionExecutorFactory.setAvailableThreads(solverConfiguration.getMaxThreads());

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
            results = solutionSeries;
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

    }

}
