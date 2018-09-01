package edu.iga.adi.sm.core.direction;

import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.Solution;

import java.util.Properties;

public final class IntermediateSolution extends Solution {

    private static final long serialVersionUID = 1443566381117L;

    public IntermediateSolution(Mesh mesh, double[][] rhs) {
        super(mesh, rhs, new Properties());
    }

    @Override
    public double getModifiedValue(double x, double y) {
        return getValue(x, y);
    }

}
