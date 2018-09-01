package edu.iga.adi.sm.core.direction.productions.solution.backsubstitution;

import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.direction.Vertex;

public class Eroot extends PFEProduction {

    private final Mesh mesh;

    public Eroot(Vertex node, Mesh mesh) {
        super(node);
        this.mesh = mesh;
    }

    public Vertex apply(Vertex T) {
        T = partial_forward_elimination(T, 6, 6, mesh.getDofsY());
        T = partial_backward_substitution(T, 6, 6, mesh.getDofsY());

        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= T.mesh.getDofsY(); j++) {
                T.leftChild.m_x[i + 2][j] = T.m_x[i][j];
                T.rightChild.m_x[i + 2][j] = T.m_x[i + 2][j];
            }
        }

        return T;
    }

}