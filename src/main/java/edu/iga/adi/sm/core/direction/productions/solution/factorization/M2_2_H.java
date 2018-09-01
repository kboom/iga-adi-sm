package edu.iga.adi.sm.core.direction.productions.solution.factorization;

import edu.iga.adi.sm.core.direction.Vertex;
import edu.iga.adi.sm.core.direction.productions.Production;

public class M2_2_H extends Production {

    public M2_2_H(Vertex node) {
        super(node);
    }

    public Vertex apply(Vertex T) {
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 4; j++) {
                T.m_a[i][j] += T.leftChild.m_a[i + 2][j + 2];
                T.m_a[i + 2][j + 2] += T.rightChild.m_a[i + 2][j + 2];
            }
            for (int j = 1; j <= T.mesh.getDofsY(); j++) {
                T.m_b[i][j] += T.leftChild.m_b[i + 2][j];
                T.m_b[i + 2][j] += T.rightChild.m_b[i + 2][j];
            }
        }

        swapDofs(1, 3, 6, T.mesh.getDofsY());
        swapDofs(2, 4, 6, T.mesh.getDofsY());

        return T;
    }

}
