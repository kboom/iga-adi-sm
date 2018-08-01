package edu.iga.adi.sm.core.splines;


import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BSpline2Test {

    private static final BSpline2 spline2 = new BSpline2();

    @Test
    public void returnsValidDerivative() {
        assertThat(spline2.getFirstDerivativeValueAt(0.5d)).isEqualTo(0);
    }

}