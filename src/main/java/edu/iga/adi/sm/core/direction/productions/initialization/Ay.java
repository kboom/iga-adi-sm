package edu.iga.adi.sm.core.direction.productions.initialization;

import edu.iga.adi.sm.core.direction.Vertex;
import edu.iga.adi.sm.core.direction.productions.Production;
import lombok.Builder;

public class Ay extends Production {

    private final MethodCoefficients coefficients;
    private final double[][] solution;
    private final double[] partition;
    private final int idx;

    @Builder
    public Ay(MethodCoefficients coefficients, Vertex node, double[][] solution, double[] partition, int idx) {
        super(node);
        this.solution = solution;
        this.partition = partition;
        this.idx = idx;
        this.coefficients = coefficients;
    }

    public Vertex apply(Vertex node) {
        coefficients.bindMethodCoefficients(node);
        initializeRightHandSides(node);
        return node;
    }

    private void initializeRightHandSides(Vertex node) {
        for (int i = 1; i <= node.mesh.getDofsX(); i++) {
            node.m_b[1][i] = partition[0] * solution[i][idx + 1];
            node.m_b[2][i] = partition[1] * solution[i][idx + 2];
            node.m_b[3][i] = partition[2] * solution[i][idx + 3];
        }
    }
}
