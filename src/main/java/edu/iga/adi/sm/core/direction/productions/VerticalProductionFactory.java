package edu.iga.adi.sm.core.direction.productions;

import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.direction.Vertex;
import edu.iga.adi.sm.core.direction.productions.construction.P1y;

public class VerticalProductionFactory extends HorizontalProductionFactory {

    private Mesh mesh;

    public VerticalProductionFactory(Mesh mesh) {
        super(mesh);
        this.mesh = mesh;
    }


    @Override
    public Production createRootProduction(Vertex vertex) {
        return new P1y(vertex, mesh);
    }

}
