package edu.iga.adi.sm.results.visualization;

import edu.iga.adi.sm.core.Mesh;
import lombok.AllArgsConstructor;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.primitives.Shape;

@AllArgsConstructor
class SurfaceFactory {

    private Mapper solutionMapper;
    private Mesh mesh;

    Shape createSurface() {
        return SurfaceBuilder.aSurface()
                .withMapper(solutionMapper)
                .withSquareRange(new Range(0, mesh.getElementsX() - 1))
                .withSteps(mesh.getElementsX()).build();
    }

}
