package edu.iga.adi.sm.core;

import edu.iga.adi.sm.core.splines.BSpline1;
import edu.iga.adi.sm.core.splines.BSpline2;
import edu.iga.adi.sm.core.splines.BSpline3;
import edu.iga.adi.sm.support.Point;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.function.ToDoubleBiFunction;

import static edu.iga.adi.sm.core.SolutionGrid.solutionGrid;
import static edu.iga.adi.sm.support.Point.solutionPoint;

public abstract class Solution implements Serializable {

    protected static final BSpline1 b1 = new BSpline1();
    protected static final BSpline2 b2 = new BSpline2();
    protected static final BSpline3 b3 = new BSpline3();

    protected final Mesh mesh;
    protected final double[][] mRHS;

    private transient ToDoubleBiFunction<Double, Double> modifier = (x, y) -> 0;

    public Solution(Mesh mesh, double[][] rhs) {
        this.mesh = mesh;
        mRHS = rhs;
    }

    public final double[][] getRhs() {
        return mRHS;
    }

    public final SolutionGrid getSolutionGrid() {
        double Dx = mesh.getDx();
        double Dy = mesh.getDy();
        double x = -Dx / 2;
        double y;
        SolutionGrid solutionGrid = solutionGrid();
        for (int i = 1; i <= mesh.getElementsX(); ++i) {
            x += Dx;
            y = -Dy / 2;
            for (int j = 1; j <= mesh.getElementsY(); ++j) {
                y += Dy;
                Point point = solutionPoint(x, y, getValue(x, y));
                solutionGrid.addPoint(point);
            }
        }
        return solutionGrid;
    }

    public final double getValue(double x, double y) {
        return getValue(mRHS, x, y);
    }

    protected final double getValue(double[][] c, double x, double y) {
        int ielemx = (int) (x / mesh.getDx()) + 1;
        int ielemy = (int) (y / mesh.getDy()) + 1;
        double localx = x - mesh.getDx() * (ielemx - 1);
        double localy = y - mesh.getDy() * (ielemy - 1);
        return modifier.applyAsDouble(x, y) + b1.getValue(localx) * b1.getValue(localy) * c[ielemx][ielemy]
                + b1.getValue(localx) * b2.getValue(localy) * c[ielemx][ielemy + 1]
                + b1.getValue(localx) * b3.getValue(localy) * c[ielemx][ielemy + 2]
                + b2.getValue(localx) * b1.getValue(localy) * c[ielemx + 1][ielemy]
                + b2.getValue(localx) * b2.getValue(localy) * c[ielemx + 1][ielemy + 1]
                + b2.getValue(localx) * b3.getValue(localy) * c[ielemx + 1][ielemy + 2]
                + b3.getValue(localx) * b1.getValue(localy) * c[ielemx + 2][ielemy]
                + b3.getValue(localx) * b2.getValue(localy) * c[ielemx + 2][ielemy + 1]
                + b3.getValue(localx) * b3.getValue(localy) * c[ielemx + 2][ielemy + 2];
    }

    public abstract double getModifiedValue(double x, double y);

    public Mesh getMesh() {
        return mesh;
    }

    public double squaredDifference(Solution otherSolution) {
        double difference = 0;
        for (int y = 0; y < mesh.getElementsY(); y++) {
            for (int x = 0; x < mesh.getElementsX(); x++) {
                double thisValue = getValue(x, y);
                double otherValue = otherSolution.getValue(x, y);
                difference += Math.abs(thisValue - otherValue);
            }
        }
        return Math.sqrt(difference);
    }

    public Solution withModifier(ToDoubleBiFunction<Double, Double> fn) {
        this.modifier = fn;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Solution solution = (Solution) o;

        if (!mesh.equals(solution.mesh)) return false;
        return Arrays.deepEquals(mRHS, solution.mRHS);
    }

    @Override
    public int hashCode() {
        int result = mesh.hashCode();
        result = 31 * result + Arrays.deepHashCode(mRHS);
        return result;
    }

    @Override
    public String toString() {
        return "Solution{" +
                "mesh=" + mesh +
                ", mRHS=" + Arrays.toString(mRHS) +
                '}';
    }

    /*
     * This is to ensure transient fields with default values are initialized.
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();

        modifier = (x, y) -> 0;
    }

}
