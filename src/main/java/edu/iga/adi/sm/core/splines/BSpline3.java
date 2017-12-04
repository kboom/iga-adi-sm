package edu.iga.adi.sm.core.splines;

public class BSpline3 extends Spline {

    public BSpline3() {
        super(0, 1);
    }

    protected double getFunctionValue(double x) {
        return 0.5 * (1 - x) * (1 - x);
    }

    @Override
    public double getFirstDerivativeValueAt(double x) {
        return belongsToDomain(x) ? x - 1 : 0;
    }

    @Override
    public double getSecondDerivativeValueAt(double x) {
        return belongsToDomain(x) ? 1.0 : 0;
    }

}
