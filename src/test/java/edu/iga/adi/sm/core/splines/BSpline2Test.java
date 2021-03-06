package edu.iga.adi.sm.core.splines;


import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BSpline2Test {

    private static final BSpline2 spline2 = new BSpline2();

    @Test
    public void returnsSplineValueInStartPointOfDomain() {
        assertThat(spline2.getFunctionValue(0d)).isEqualTo(0.5d);
    }

    @Test
    public void returnsSplineValueInEndPointOfDomain() {
        assertThat(spline2.getFunctionValue(1d)).isEqualTo(0.5d);
    }

    @Test
    public void returnsSplineValueInCenterOfDomain() {
        assertThat(spline2.getFunctionValue(0.5d)).isEqualTo(0.75d);
    }

    @Test
    public void returnsValidDerivativeInTheMiddle() {
        assertThat(spline2.getFirstDerivativeValueAt(0.5d)).isEqualTo(0);
    }

    @Test
    public void returnsValidDerivativeAtStart() {
        assertThat(spline2.getFirstDerivativeValueAt(0d)).isEqualTo(1d);
    }

    @Test
    public void returnsValidDerivativeAtEnd() {
        assertThat(spline2.getFirstDerivativeValueAt(1d)).isEqualTo(-1d);
    }

    @Test
    public void returnsZeroDerivativeLeftOfDomain() {
        assertThat(spline2.getFirstDerivativeValueAt(-0.1d)).isEqualTo(0);
    }

    @Test
    public void returnsZeroDerivativeRightOfDomain() {
        assertThat(spline2.getFirstDerivativeValueAt(1.1d)).isEqualTo(0);
    }

    @Test
    public void returnsValidSecondDerivativeInsideTheDomain() {
        assertThat(spline2.getSecondDerivativeValueAt(0.5d)).isEqualTo(-2);
    }

    @Test
    public void returnsZeroSecondDerivativeLeftToTheDomain() {
        assertThat(spline2.getSecondDerivativeValueAt(-1)).isEqualTo(0);
    }

    @Test
    public void returnsZeroSecondDerivativeRightToTheDomain() {
        assertThat(spline2.getSecondDerivativeValueAt(1.1)).isEqualTo(0);
    }

}