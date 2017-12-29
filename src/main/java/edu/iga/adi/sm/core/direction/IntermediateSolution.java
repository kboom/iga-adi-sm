package edu.iga.adi.sm.core.direction;

import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.Solution;

public final class IntermediateSolution extends Solution {

    public IntermediateSolution(Mesh mesh, double[][] rhs) {
        super(mesh, rhs);
    }

    @Override
    public double getModifiedValue(double x, double y) {
        return getValue(x, y);
    }


}
