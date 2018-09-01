package edu.iga.adi.sm.core.direction.productions.solution.backsubstitution;

import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.direction.Vertex;

public class E2_2_6 extends PFEProduction {

    private final Mesh mesh;

    public E2_2_6(Vertex Vert, Mesh mesh) {
        super(Vert);
        this.mesh = mesh;
    }

    public Vertex apply(Vertex T) {
        T = partial_forward_elimination(T, 2, 6, mesh.getDofsY());
        return T;
    }
}