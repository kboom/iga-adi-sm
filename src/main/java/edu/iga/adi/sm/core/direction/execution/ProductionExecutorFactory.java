package edu.iga.adi.sm.core.direction.execution;

import edu.iga.adi.sm.SolverConfiguration;
import edu.iga.adi.sm.core.direction.productions.Production;
import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.Arrays.asList;

public class ProductionExecutorFactory {

    private static final Logger log = Logger.getLogger(ProductionExecutorFactory.class);

    private final ExecutorService executorService;

    public ProductionExecutorFactory(
            SolverConfiguration solverConfiguration
    ) {
        this.executorService = Executors.newFixedThreadPool(solverConfiguration.getMaxThreads());
    }

    public ProductionExecutor createLauncherFor(Production... productions) {
        return createLauncherFor(asList(productions));
    }

    public ProductionExecutor createLauncherFor(Collection<Production> productions) {
        return new ProductionExecutor(executorService, productions);
    }

    public void joinAll() {
        if (!executorService.isShutdown()) {
            executorService.shutdown();
        }
    }

}
