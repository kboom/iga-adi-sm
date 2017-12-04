package edu.iga.adi.sm.core.direction.productions.initialization;

import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.Problem;
import edu.iga.adi.sm.core.direction.Vertex;
import edu.iga.adi.sm.core.direction.constants.GaussPoints;
import edu.iga.adi.sm.core.direction.productions.Production;
import edu.iga.adi.sm.core.splines.BSpline1;
import edu.iga.adi.sm.core.splines.BSpline2;
import edu.iga.adi.sm.core.splines.BSpline3;
import edu.iga.adi.sm.core.splines.Spline;

public class A extends Production {

    private static final Spline spline1 = new BSpline1();
    private static final Spline spline2 = new BSpline2();
    private static final Spline spline3 = new BSpline3();

    private final Problem rhs;

    public A(Vertex node, Mesh mesh, Problem rhs) {
        super(node, mesh);
        this.rhs = rhs;
    }

    public Vertex apply(Vertex node) {
        initializeCoefficientsMatrix(node);
        initializeRightHandSides(node);
        return node;
    }

    private void initializeCoefficientsMatrix(Vertex node) {
        SampleCoefficients.useArbitraryCoefficients(node);
    }

    private void initializeRightHandSides(Vertex node) {
        for (int i = 1; i <= node.mesh.getDofsY(); i++) {
            fillRightHandSide(node, spline3, 1, i);
            fillRightHandSide(node, spline2, 2, i);
            fillRightHandSide(node, spline1, 3, i);
        }
    }

    private void fillRightHandSide(Vertex node, Spline spline, int r, int i) {
        for (int k = 1; k <= GaussPoints.GAUSS_POINT_COUNT; k++) {
            double x = GaussPoints.GAUSS_POINTS[k] * node.mesh.getDx() + node.beginning;
            for (int l = 1; l <= GaussPoints.GAUSS_POINT_COUNT; l++) {
                if (i > 2) {
                    double y = (GaussPoints.GAUSS_POINTS[l] + (i - 3)) * node.mesh.getDy();
                    node.m_b[r][i] += GaussPoints.GAUSS_POINT_WEIGHTS[k] * spline.getValue(GaussPoints.GAUSS_POINTS[k]) * GaussPoints.GAUSS_POINT_WEIGHTS[l] * spline1.getValue(GaussPoints.GAUSS_POINTS[l]) * rhs.getValue(x, y);
                }
                if (i > 1 && (i - 2) < node.mesh.getElementsY()) {
                    double y = (GaussPoints.GAUSS_POINTS[l] + (i - 2)) * node.mesh.getDy();
                    node.m_b[r][i] += GaussPoints.GAUSS_POINT_WEIGHTS[k] * spline.getValue(GaussPoints.GAUSS_POINTS[k]) * GaussPoints.GAUSS_POINT_WEIGHTS[l] * spline2.getValue(GaussPoints.GAUSS_POINTS[l]) * rhs.getValue(x, y);
                }
                if ((i - 1) < node.mesh.getElementsY()) {
                    double y = (GaussPoints.GAUSS_POINTS[l] + (i - 1)) * node.mesh.getDy();
                    node.m_b[r][i] += GaussPoints.GAUSS_POINT_WEIGHTS[k] * spline.getValue(GaussPoints.GAUSS_POINTS[k]) * GaussPoints.GAUSS_POINT_WEIGHTS[l] * spline3.getValue(GaussPoints.GAUSS_POINTS[l]) * rhs.getValue(x, y);
                }
            }
        }
    }

}
