package edu.iga.adi.sm.results.visualization.drawers.jzy3d;

import org.jzy3d.plot3d.primitives.Shape;

import java.util.List;

public interface Jzy3dSurfaceProvider {

    List<Shape> provideSurfacesFor(Jzy3dSolutionMapper mapper);

}
