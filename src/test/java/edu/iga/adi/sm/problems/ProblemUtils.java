package edu.iga.adi.sm.problems;

import edu.iga.adi.sm.core.Mesh;

public class ProblemUtils {

    public static Mesh createRectangularMesh(int size) {
        return Mesh.aMesh()
                .withElementsX(size)
                .withElementsY(size)
                .withResolutionX(size)
                .withResolutionY(size)
                .withOrder(2).build();
    }

}
