package edu.iga.adi.sm.core.direction.productions.construction;

import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.direction.Vertex;

public class P1y extends P1 {

    public P1y(Vertex node) {
        super(node);
    }

    @Override
    protected int getElementCount(Mesh mesh) {
        return mesh.getElementsY();
    }

}

