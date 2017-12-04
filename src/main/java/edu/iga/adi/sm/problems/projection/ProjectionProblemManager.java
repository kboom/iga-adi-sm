package edu.iga.adi.sm.problems.projection;

import edu.iga.adi.sm.SolverConfiguration;
import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.Problem;
import edu.iga.adi.sm.problems.AbstractProblemManager;
import edu.iga.adi.sm.problems.IterativeProblem;
import edu.iga.adi.sm.problems.SingleRunProblem;

public class ProjectionProblemManager extends AbstractProblemManager {

    public ProjectionProblemManager(SolverConfiguration config) {
        super(config);
    }

    @Override
    public IterativeProblem getProblem() {
        return new SingleRunProblem() {

            @Override
            protected Problem getProblem() {
                final Mesh mesh = config.getMesh();
                return (x, y) -> Math.pow(x - mesh.getElementsX() / 2, 2) + Math.pow(y - mesh.getElementsY() / 2, 2);
            }

        };
    }

}
