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

import static java.lang.System.exit;

public class Main {

    private static final TimeLogger timeLogger = new TimeLogger();
    private static String[] PROGRAM_ARGUMENTS;

    public static void main(String[] args) {
        PROGRAM_ARGUMENTS = args;

        SolverConfiguration solverConfiguration = withInjectedProgramArguments(SolverConfiguration.builder().build());
        solve(solverConfiguration);
    }

    private static void solve(SolverConfiguration solverConfiguration) {
        final ProductionExecutorFactory productionExecutorFactory = new ProductionExecutorFactory(solverConfiguration);

        final Mesh mesh = solverConfiguration.getMesh();

        final SolverFactory solverFactory = new SolverFactory(
                mesh,
                solverConfiguration.isLogging() ? new ConsoleSolutionLogger(mesh) : new NoopSolutionLogger(),
                timeLogger
        );

        final ProblemManagerFactory problemManagerFactory = LocalProblemManagerFactory.builder()
                .mesh(mesh)
                .productionExecutorFactory(productionExecutorFactory)
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

        if (!solverConfiguration.isPlotting()) {
            exit(0);
        }
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