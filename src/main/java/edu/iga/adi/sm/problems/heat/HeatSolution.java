package edu.iga.adi.sm.problems.heat;

import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.Solution;

import java.util.Properties;

public class HeatSolution extends Solution {

    private static final long serialVersionUID = 1243565388611L;

    public HeatSolution(Mesh mesh, double[][] rhs, Properties properties) {
        super(mesh, rhs, properties);
    }

    @Override
    public double getModifiedValue(double x, double y) {
        int ielemx = (int) (x / (mesh.getDx())) + 1;
        int ielemy = (int) (y / (mesh.getDy())) + 1;
        double localx = x - (mesh.getDx()) * (ielemx - 1);
        double localy = y - (mesh.getDy()) * (ielemy - 1);
        double solution = 0.0;
        solution += b1.getSecondDerivativeValueAt(localx) * b1.getValue(localy) * mRHS[ielemx][ielemy];
        solution += b1.getSecondDerivativeValueAt(localx) * b2.getValue(localy) * mRHS[ielemx][ielemy + 1];
        solution += b1.getSecondDerivativeValueAt(localx) * b3.getValue(localy) * mRHS[ielemx][ielemy + 2];
        solution += b2.getSecondDerivativeValueAt(localx) * b1.getValue(localy) * mRHS[ielemx + 1][ielemy];
        solution += b2.getSecondDerivativeValueAt(localx) * b2.getValue(localy) * mRHS[ielemx + 1][ielemy + 1];
        solution += b2.getSecondDerivativeValueAt(localx) * b3.getValue(localy) * mRHS[ielemx + 1][ielemy + 2];
        solution += b3.getSecondDerivativeValueAt(localx) * b1.getValue(localy) * mRHS[ielemx + 2][ielemy];
        solution += b3.getSecondDerivativeValueAt(localx) * b2.getValue(localy) * mRHS[ielemx + 2][ielemy + 1];
        solution += b3.getSecondDerivativeValueAt(localx) * b3.getValue(localy) * mRHS[ielemx + 2][ielemy + 2];

        solution += b1.getValue(localx) * b1.getSecondDerivativeValueAt(localy) * mRHS[ielemx][ielemy];
        solution += b1.getValue(localx) * b2.getSecondDerivativeValueAt(localy) * mRHS[ielemx][ielemy + 1];
        solution += b1.getValue(localx) * b3.getSecondDerivativeValueAt(localy) * mRHS[ielemx][ielemy + 2];
        solution += b2.getValue(localx) * b1.getSecondDerivativeValueAt(localy) * mRHS[ielemx + 1][ielemy];
        solution += b2.getValue(localx) * b2.getSecondDerivativeValueAt(localy) * mRHS[ielemx + 1][ielemy + 1];
        solution += b2.getValue(localx) * b3.getSecondDerivativeValueAt(localy) * mRHS[ielemx + 1][ielemy + 2];
        solution += b3.getValue(localx) * b1.getSecondDerivativeValueAt(localy) * mRHS[ielemx + 2][ielemy];
        solution += b3.getValue(localx) * b2.getSecondDerivativeValueAt(localy) * mRHS[ielemx + 2][ielemy + 1];
        solution += b3.getValue(localx) * b3.getSecondDerivativeValueAt(localy) * mRHS[ielemx + 2][ielemy + 2];

        return solution;
    }

}
