package edu.iga.adi.sm.core.direction.productions.solution.backsubstitution;

import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.direction.Vertex;

public class E2_1_5 extends PFEProduction {

    private final Mesh mesh;

    public E2_1_5(Vertex node, Mesh mesh) {
        super(node);
        this.mesh = mesh;
    }

    public Vertex apply(Vertex T) {
        T = partial_forward_elimination(T, 1, 5, mesh.getDofsY());
        return T;
    }
}