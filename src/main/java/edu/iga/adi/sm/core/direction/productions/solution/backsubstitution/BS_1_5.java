package edu.iga.adi.sm.core.direction.productions.solution.backsubstitution;

import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.direction.Vertex;

public class BS_1_5 extends PFEProduction {

    private final Mesh mesh;

    public BS_1_5(Vertex node, Mesh mesh) {
        super(node);
        this.mesh = mesh;
    }

    public Vertex apply(Vertex node) {
        node = partial_backward_substitution(node, 1, 5, mesh.getDofsY());
        swapDofs(1, 2, 5, mesh.getDofsY());
        swapDofs(2, 3, 5, mesh.getDofsY());
        return node;
    }

}