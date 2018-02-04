package edu.iga.adi.sm.results.visualization.drawers.jzy3d;

import lombok.Builder;
import lombok.Getter;
import org.jzy3d.plot3d.primitives.Shape;

@Builder
@Getter
public class Jzy3dSurface {

    private final Shape shape;

    @Builder.Default
    private boolean solutionDependent = true;

}
