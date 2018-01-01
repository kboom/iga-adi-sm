package edu.iga.adi.sm.results.visualization.drawers.surfaces;

import edu.iga.adi.sm.core.Solution;
import org.jzy3d.plot3d.primitives.Shape;

import java.util.List;

public interface SurfaceProvider {

    List<Shape> provideSurfacesFor(Solution solution);

}
