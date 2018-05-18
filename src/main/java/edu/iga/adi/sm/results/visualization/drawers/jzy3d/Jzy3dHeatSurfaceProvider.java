package edu.iga.adi.sm.results.visualization.drawers.jzy3d;

import lombok.Builder;

import java.util.List;

import static com.beust.jcommander.internal.Lists.newArrayList;

@Builder
public class Jzy3dHeatSurfaceProvider implements Jzy3dSurfaceProvider {

    private final Jzy3dSurfaceFactory surfaceFactory;

    @Override
    public List<Jzy3dSurface> provideSurfacesFor(Jzy3dSolutionMapper mapper) {
        return newArrayList(Jzy3dSurface.builder().shape(surfaceFactory.createHotColdSurface(mapper)).build());
    }

}
