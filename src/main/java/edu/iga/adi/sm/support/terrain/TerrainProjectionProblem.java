package edu.iga.adi.sm.support.terrain;

import edu.iga.adi.sm.core.Problem;
import edu.iga.adi.sm.problems.SingleRunProblem;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TerrainProjectionProblem extends SingleRunProblem implements Problem {

    private TerrainPointFinder terrainPointFinder;

    @Override
    public double getValue(double x, double y) {
        return terrainPointFinder.get(x - 0.5, y - 0.5).z;
    }

    @Override
    protected Problem getProblem() {
        return this;
    }

}
