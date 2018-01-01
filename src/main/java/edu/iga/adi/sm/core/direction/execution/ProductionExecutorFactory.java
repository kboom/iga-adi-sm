package edu.iga.adi.sm.core.direction.execution;

import edu.iga.adi.sm.core.direction.productions.Production;
import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.Arrays.asList;
import static java.util.concurrent.Executors.newCachedThreadPool;

public class ProductionExecutorFactory {

    private static final Logger log = Logger.getLogger(ProductionExecutorFactory.class);

    private ExecutorService executorService = newCachedThreadPool();

    public ProductionExecutor createLauncherFor(Production... productions) {
        return createLauncherFor(asList(productions));
    }

    public ProductionExecutor createLauncherFor(Collection<Production> productions) {
        return new ProductionExecutor(executorService, productions);
    }

    public void setAvailableThreads(int availableThreads) {
        log.info(String.format("Using %d threads in pool", availableThreads));
        executorService = Executors.newFixedThreadPool(availableThreads);
    }

    public void joinAll() {
        if (!executorService.isShutdown()) {
            executorService.shutdown();
        }
    }

}
