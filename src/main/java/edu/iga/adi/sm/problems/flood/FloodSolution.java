package edu.iga.adi.sm.problems.flood;

import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.Solution;

import java.util.Properties;

public class FloodSolution extends Solution {

    private static final long serialVersionUID = 1243569388117L;

    private double meanValue;
    private double[][] terrain;

    public FloodSolution(Mesh mesh, double[][] rhs, double[][] terrain, Properties metadata) {
        super(mesh, rhs, metadata);
        this.terrain = terrain;
//        meanValue = computeMeanOf(rhs);
    }

    @Override
    public final double getModifiedValue(double x, double y) {
        int ielemx = (int) (x / (mesh.getDx())) + 1;
        int ielemy = (int) (y / (mesh.getDy())) + 1;
        double localx = x - (mesh.getDx()) * (ielemx - 1);
        double localy = y - (mesh.getDy()) * (ielemy - 1);

        double Ut = getValue(mRHS, x, y);
        double Z = getValue(terrain, x, y);

        double diffUtZ = Math.max(Ut - Z, 0);

        double K = - Math.pow(diffUtZ, 5.0 / 3.0) / Math.sqrt(getMeanGradient(x, y));

        return K * (b1.getSecondDerivativeValueAt(localx) * b1.getValue(localy) * mRHS[ielemx][ielemy]
                + b1.getSecondDerivativeValueAt(localx) * b2.getValue(localy) * mRHS[ielemx][ielemy + 1]
                + b1.getSecondDerivativeValueAt(localx) * b3.getValue(localy) * mRHS[ielemx][ielemy + 2]
                + b2.getSecondDerivativeValueAt(localx) * b1.getValue(localy) * mRHS[ielemx + 1][ielemy]
                + b2.getSecondDerivativeValueAt(localx) * b2.getValue(localy) * mRHS[ielemx + 1][ielemy + 1]
                + b2.getSecondDerivativeValueAt(localx) * b3.getValue(localy) * mRHS[ielemx + 1][ielemy + 2]
                + b3.getSecondDerivativeValueAt(localx) * b1.getValue(localy) * mRHS[ielemx + 2][ielemy]
                + b3.getSecondDerivativeValueAt(localx) * b2.getValue(localy) * mRHS[ielemx + 2][ielemy + 1]
                + b3.getSecondDerivativeValueAt(localx) * b3.getValue(localy) * mRHS[ielemx + 2][ielemy + 2]);
    }

    private double getMeanGradient(double x, double y) {
        int ielemx = (int) (x / (mesh.getDx())) + 1;
        int ielemy = (int) (y / (mesh.getDy())) + 1;
        double localx = x - (mesh.getDx()) * (ielemx - 1);
        double localy = y - (mesh.getDy()) * (ielemy - 1);

        double der_x_Ut = b1.getFirstDerivativeValueAt(localx) * b1.getValue(localy) * mRHS[ielemx][ielemy]
                + b1.getFirstDerivativeValueAt(localx) * b2.getValue(localy) * mRHS[ielemx][ielemy + 1]
                + b1.getFirstDerivativeValueAt(localx) * b3.getValue(localy) * mRHS[ielemx][ielemy + 2]
                + b2.getFirstDerivativeValueAt(localx) * b1.getValue(localy) * mRHS[ielemx + 1][ielemy]
                + b2.getFirstDerivativeValueAt(localx) * b2.getValue(localy) * mRHS[ielemx + 1][ielemy + 1]
                + b2.getFirstDerivativeValueAt(localx) * b3.getValue(localy) * mRHS[ielemx + 1][ielemy + 2]
                + b3.getFirstDerivativeValueAt(localx) * b1.getValue(localy) * mRHS[ielemx + 2][ielemy]
                + b3.getFirstDerivativeValueAt(localx) * b2.getValue(localy) * mRHS[ielemx + 2][ielemy + 1]
                + b3.getFirstDerivativeValueAt(localx) * b3.getValue(localy) * mRHS[ielemx + 2][ielemy + 2];

        double der_y_Ut = b1.getValue(localx) * b1.getFirstDerivativeValueAt(localy) * mRHS[ielemx][ielemy]
                + b1.getValue(localx) * b2.getFirstDerivativeValueAt(localy) * mRHS[ielemx][ielemy + 1]
                + b1.getValue(localx) * b3.getFirstDerivativeValueAt(localy) * mRHS[ielemx][ielemy + 2]
                + b2.getValue(localx) * b1.getFirstDerivativeValueAt(localy) * mRHS[ielemx + 1][ielemy]
                + b2.getValue(localx) * b2.getFirstDerivativeValueAt(localy) * mRHS[ielemx + 1][ielemy + 1]
                + b2.getValue(localx) * b3.getFirstDerivativeValueAt(localy) * mRHS[ielemx + 1][ielemy + 2]
                + b3.getValue(localx) * b1.getFirstDerivativeValueAt(localy) * mRHS[ielemx + 2][ielemy]
                + b3.getValue(localx) * b2.getFirstDerivativeValueAt(localy) * mRHS[ielemx + 2][ielemy + 1]
                + b3.getValue(localx) * b3.getFirstDerivativeValueAt(localy) * mRHS[ielemx + 2][ielemy + 2];

        return Math.sqrt(Math.pow(der_x_Ut, 2) + Math.pow(der_y_Ut, 2));
    }

}
