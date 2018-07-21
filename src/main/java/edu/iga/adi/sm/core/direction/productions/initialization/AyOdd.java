package edu.iga.adi.sm.core.direction.productions.initialization;

import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.direction.Vertex;
import edu.iga.adi.sm.core.direction.productions.Production;

public class AyOdd extends Production {

    private final double[][] solution;
    private final double[] partition;
    private final int idx;

    public AyOdd(Vertex node, double[][] solution, double[] partition, int idx, Mesh mesh) {
        super(node, mesh);
        this.solution = solution;
        this.partition = partition;
        this.idx = idx;
    }

    public Vertex apply(Vertex node) {
        initializeCoefficientsMatrix(node);
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

    protected void initializeCoefficientsMatrix(Vertex node) {
        SampleCoefficients.useOddCoefficients(node);
    }

}
