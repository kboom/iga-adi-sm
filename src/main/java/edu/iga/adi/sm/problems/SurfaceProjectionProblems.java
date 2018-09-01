package edu.iga.adi.sm.problems;

import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.Problem;

public class SurfaceProjectionProblems {

    public static Problem coneInTheMiddle(Mesh mesh) {
        return (x, y) -> {
            int heat = 1;
            int radius = Double.valueOf(Math.pow(mesh.getElementsX() / 4.0, 2)).intValue();

            int aX = mesh.getElementsX() - mesh.getElementsX() / 2;
            int aY = mesh.getElementsY() - mesh.getElementsY() / 2;

            double distA = Math.pow(x - aX, 2) + Math.pow(y - aY, 2);
            if (distA < radius) return heat;
            return 0;
        };
    }

}
