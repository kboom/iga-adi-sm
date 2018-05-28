package edu.iga.adi.sm.core.direction.productions.initialization;

import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.direction.Vertex;

public class AyEven extends AyOdd {

    public AyEven(Vertex node, double[][] solution, double[] partition, int idx, Mesh mesh) {
        super(node, solution, partition, idx, mesh);
    }

}
