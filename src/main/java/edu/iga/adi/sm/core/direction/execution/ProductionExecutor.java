package edu.iga.adi.sm.core.direction.execution;

import edu.iga.adi.sm.core.direction.productions.Production;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ProductionExecutor {

    private Logger logger = Logger.getLogger(ProductionExecutor.class);

    private List<Production> productionsToExecute = new ArrayList<>();

    private ExecutorService threadPoolExecutor;

    ProductionExecutor(ExecutorService threadPoolExecutor, Collection<Production> productionsToExecute) {
        this.threadPoolExecutor = threadPoolExecutor;
        this.productionsToExecute.addAll(productionsToExecute);
    }

    public void launchProductions() {
        try {
            threadPoolExecutor.invokeAll(productionsToExecute.stream().map(
                    (Function<Production, Callable<Void>>) ProductionCallable::new
            ).collect(Collectors.toList()));
        } catch (InterruptedException e) {
            logger.error("Could not run all productions in a set", e);
        }
    }

    private static class ProductionCallable implements Callable<Void> {

        private Logger logger = Logger.getLogger(ProductionCallable.class);

        private Production production;

        private ProductionCallable(Production production) {
            this.production = production;
        }

        @Override
        public Void call() {
            production.run();
            return null;
        }

    }

}
