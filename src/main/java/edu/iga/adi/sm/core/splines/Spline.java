package edu.iga.adi.sm.core.splines;

public abstract class Spline {

    private static final int VALUE_FOR_OUTSIDE_DOMAIN = 0;

    private int lowerDomainBound;

    private int upperDomainBound;

    public Spline(int lowerDomainBound, int upperDomainBound) {
        this.lowerDomainBound = lowerDomainBound;
        this.upperDomainBound = upperDomainBound;
    }

    public double getValue(double x) {
        if (belongsToDomain(x)) {
            return getFunctionValue(x);
        } else return VALUE_FOR_OUTSIDE_DOMAIN;
    }

    protected abstract double getFunctionValue(double x);

    public abstract double getSecondDerivativeValueAt(double x);

    protected boolean belongsToDomain(double x) {
        return x >= lowerDomainBound && x <= upperDomainBound;
    }

    public abstract double getFirstDerivativeValueAt(double x);

}
