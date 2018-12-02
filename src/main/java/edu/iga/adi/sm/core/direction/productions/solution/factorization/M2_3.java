package edu.iga.adi.sm.core.direction.productions.solution.factorization;

import edu.iga.adi.sm.core.direction.Vertex;
import edu.iga.adi.sm.core.direction.productions.Production;

public class M2_3 extends Production {
    public M2_3(Vertex node) {
        super(node);
    }

    public Vertex apply(Vertex T) {
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                T.m_a[i][j] += T.leftChild.m_a[i][j];
                T.m_a[i + 1][j + 1] += T.middleChild.m_a[i][j];
                T.m_a[i + 2][j + 2] += T.rightChild.m_a[i][j];
            }
            for (int j = 1; j <= T.mesh.getDofsY(); j++) {
                T.m_b[i][j] += T.leftChild.m_b[i][j];
                T.m_b[i + 1][j] += T.middleChild.m_b[i][j];
                T.m_b[i + 2][j] += T.rightChild.m_b[i][j];
            }
        }
        // bring 3rd degree of freedom to the front
        swapDofs(1, 3, 5, T.mesh.getDofsY());
        swapDofs(2, 3, 5, T.mesh.getDofsY());
        return T;
    }
}