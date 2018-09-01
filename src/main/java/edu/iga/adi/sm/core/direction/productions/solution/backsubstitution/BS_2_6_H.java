package edu.iga.adi.sm.core.direction.productions.solution.backsubstitution;

import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.direction.Vertex;

public class BS_2_6_H extends PFEProduction {

    private final Mesh mesh;

    public BS_2_6_H(Vertex node, Mesh mesh) {
        super(node);
        this.mesh = mesh;
    }

    public Vertex apply(Vertex T) {
        T = partial_backward_substitution(T, 2, 6, mesh.getDofsY());
        swapDofs(1, 3, 6, T.mesh.getDofsY());
        swapDofs(2, 4, 6, T.mesh.getDofsY());

        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= T.mesh.getDofsY(); j++) {
                T.leftChild.m_x[i + 2][j] = T.m_x[i][j];
                T.rightChild.m_x[i + 2][j] = T.m_x[i + 2][j];
            }
        }

        return T;
    }

}
