package edu.iga.adi.sm.results.visualization.drawers.jzy3d;

import com.beust.jcommander.internal.Lists;
import lombok.Builder;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.primitives.Shape;

import java.util.List;

@Builder
public class Jzy3dSingleSurfaceProvider implements Jzy3dSurfaceProvider {

    private final Jzy3dSurfaceFactory surfaceFactory;

    @Override
    public List<Shape> provideSurfacesFor(Jzy3dSolutionMapper mapper) {
        return Lists.newArrayList(surfaceFactory.createHotColdSurface(mapper));
    }

}
