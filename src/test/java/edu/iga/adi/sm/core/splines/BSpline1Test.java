package edu.iga.adi.sm.core.splines;

import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BSpline1Test {

    private static final BSpline1 spline1 = new BSpline1();

    @Test
    public void returnsSplineValueInStartPointOfDomain() {
        assertThat(spline1.getFunctionValue(0d)).isEqualTo(0d);
    }

    @Test
    public void returnsSplineValueInEndPointOfDomain() {
        assertThat(spline1.getFunctionValue(1d)).isEqualTo(0.5d);
    }

    @Test
    public void returnsSplineValueInCenterOfDomain() {
        assertThat(spline1.getFunctionValue(0.5d)).isEqualTo(0.125d);
    }

    @Test
    public void returnsValidDerivativeInTheMiddle() {
        assertThat(spline1.getFirstDerivativeValueAt(0.5d)).isEqualTo(0.5d);
    }

    @Test
    public void returnsValidDerivativeAtStart() {
        assertThat(spline1.getFirstDerivativeValueAt(0d)).isEqualTo(0d);
    }

    @Test
    public void returnsValidDerivativeAtEnd() {
        assertThat(spline1.getFirstDerivativeValueAt(1d)).isEqualTo(1d);
    }

    @Test
    public void returnsZeroDerivativeLeftOfDomain() {
        assertThat(spline1.getFirstDerivativeValueAt(-0.1d)).isEqualTo(0);
    }

    @Test
    public void returnsZeroDerivativeRightOfDomain() {
        assertThat(spline1.getFirstDerivativeValueAt(1.1d)).isEqualTo(0);
    }

    @Test
    public void returnsValidSecondDerivativeInsideTheDomain() {
        assertThat(spline1.getSecondDerivativeValueAt(0.5d)).isEqualTo(1);
    }

    @Test
    public void returnsZeroSecondDerivativeLeftToTheDomain() {
        assertThat(spline1.getSecondDerivativeValueAt(-1)).isEqualTo(0);
    }

    @Test
    public void returnsZeroSecondDerivativeRightToTheDomain() {
        assertThat(spline1.getSecondDerivativeValueAt(1.1)).isEqualTo(0);
    }

}
