package edu.iga.adi.sm.core.direction.productions.initialization;

import edu.iga.adi.sm.core.direction.Vertex;

/**
 * B1,B1 1/3
 * B1,B2 -1/6
 * B1,B3 -1/6
 * B2,B1 -1/6
 * B2,B2 1/3
 * B2,B3 -1/6
 * B3,B1 -1/6
 * B3,B2 -1/6
 * B3,B3 1/3
 */
class SampleCoefficients {

    static void useEvenCoefficients(Vertex node) {
        node.m_a[1][1] = 1.0 / 20.0 + 1.0/3.0;
        node.m_a[1][2] = 13.0 / 120 - 1.0/6.0;
        node.m_a[1][3] = 1.0 / 120 - 1.0/6.0;
        node.m_a[2][1] = 13.0 / 120.0 - 1.0/6.0;
        node.m_a[2][2] = 45.0 / 100.0 + 1.0/3.0;
        node.m_a[2][3] = 13.0 / 120.0 - 1.0/6.0;
        node.m_a[3][1] = 1.0 / 120.0 - 1.0/6.0;
        node.m_a[3][2] = 13.0 / 120.0 - 1.0/6.0;
        node.m_a[3][3] = 1.0 / 20.0 + 1.0/3.0;
    }

    static void useOddCoefficients(Vertex node) {
        node.m_a[1][1] = 1.0 / 20.0 + 1.0/3.0;
        node.m_a[1][2] = 13.0 / 120 - 1.0/6.0;
        node.m_a[1][3] = 1.0 / 120 - 1.0/6.0;
        node.m_a[2][1] = 13.0 / 120.0 - 1.0/6.0;
        node.m_a[2][2] = 45.0 / 100.0 + 1.0/3.0;
        node.m_a[2][3] = 13.0 / 120.0 - 1.0/6.0;
        node.m_a[3][1] = 1.0 / 120.0 - 1.0/6.0;
        node.m_a[3][2] = 13.0 / 120.0 - 1.0/6.0;
        node.m_a[3][3] = 1.0 / 20.0 + 1.0/3.0;
    }



}
