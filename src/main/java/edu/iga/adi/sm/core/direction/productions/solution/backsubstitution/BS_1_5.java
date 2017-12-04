package edu.iga.adi.sm.core.direction.productions.solution.backsubstitution;

import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.direction.Vertex;

public class BS_1_5 extends PFEProduction {

    public BS_1_5(Vertex Vert, Mesh Mesh) {
        super(Vert, Mesh);
    }

    public Vertex apply(Vertex T) {
        T = partial_backward_substitution(T, 1, 5, m_mesh.getDofsY());
        swapDofs(1, 2, 5, m_mesh.getDofsY());
        swapDofs(2, 3, 5, m_mesh.getDofsY());
        return T;
    }
}