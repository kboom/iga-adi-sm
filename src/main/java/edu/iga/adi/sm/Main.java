package edu.iga.adi.sm;

import com.beust.jcommander.JCommander;
import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.direction.execution.ProductionExecutorFactory;
import edu.iga.adi.sm.loggers.ConsoleSolutionLogger;
import edu.iga.adi.sm.loggers.NoopSolutionLogger;
import edu.iga.adi.sm.problems.LocalProblemManagerFactory;

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
                .build()
                .launch();
    }

    static <T> T withInjectedProgramArguments(T o) {
        JCommander.newBuilder().addObject(o).build().parse(PROGRAM_ARGUMENTS);
        return o;
    }

}