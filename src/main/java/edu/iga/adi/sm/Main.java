package edu.iga.adi.sm;

import com.beust.jcommander.JCommander;
import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.Solution;
import edu.iga.adi.sm.core.direction.execution.ProductionExecutorFactory;
import edu.iga.adi.sm.loggers.ConsoleSolutionLogger;
import edu.iga.adi.sm.loggers.NoopSolutionLogger;
import edu.iga.adi.sm.problems.LocalProblemManagerFactory;
import edu.iga.adi.sm.results.storage.CompressResultsStorageProcessor;
import edu.iga.adi.sm.results.storage.FileSolutionStorage;

import java.io.File;

public class Main {

    private static final ProductionExecutorFactory productionExecutorFactory = new ProductionExecutorFactory();
    private static final TimeLogger timeLogger = new TimeLogger();
    private static String[] PROGRAM_ARGUMENTS;

    public static void main(String[] args) {
        PROGRAM_ARGUMENTS = args;

        SolverConfiguration solverConfiguration = withInjectedProgramArguments(SolverConfiguration.builder().build());

        productionExecutorFactory.setAvailableThreads(solverConfiguration.getMaxThreads());
        final Mesh mesh = solverConfiguration.getMesh();

        final SolverFactory solverFactory = new SolverFactory(
                mesh,
                solverConfiguration.isLogging() ? new ConsoleSolutionLogger(mesh) : new NoopSolutionLogger(),
                timeLogger
        );

        final ProblemManagerFactory problemManagerFactory = LocalProblemManagerFactory.builder()
                .mesh(mesh)
                .solverConfiguration(solverConfiguration)
                .solverFactory(solverFactory)
                .build();

        SolverLauncher.builder()
                .solverConfiguration(solverConfiguration)
                .problemManagerFactory(problemManagerFactory)
                .productionExecutorFactory(productionExecutorFactory)
                .timeLogger(timeLogger)
                .solverFactory(solverFactory)
                .solutionStorage(initializeStorage(solverConfiguration))
                .build()
                .launch();
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

    private static <T> T withInjectedProgramArguments(T o) {
        JCommander.newBuilder().addObject(o).build().parse(PROGRAM_ARGUMENTS);
        return o;
    }

}