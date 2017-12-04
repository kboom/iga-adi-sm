package edu.iga.adi.sm.core.splines;

public class BSpline2 extends Spline {

    public BSpline2() {
        super(0, 1);
    }

    protected double getFunctionValue(double x) {
        return (-2 * (x + 1) * (x + 1) + 6 * (x + 1) - 3) * 0.5;
    }

    @Override
    public double getFirstDerivativeValueAt(double x) {
        return belongsToDomain(x) ? 1 - 2 * x : 0;
    }

    @Override
    public double getSecondDerivativeValueAt(double x) {
        return belongsToDomain(x) ? -2.0 : 0;
    }

}
