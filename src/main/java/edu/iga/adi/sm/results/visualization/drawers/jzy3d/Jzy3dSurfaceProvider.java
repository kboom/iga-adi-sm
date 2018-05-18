package edu.iga.adi.sm.results.visualization.drawers.jzy3d;

import java.util.List;

public interface Jzy3dSurfaceProvider {

    List<Jzy3dSurface> provideSurfacesFor(Jzy3dSolutionMapper mapper);

}
