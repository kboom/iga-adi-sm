package edu.iga.adi.sm.results.visualization.drawers.surfaces;

import com.beust.jcommander.internal.Lists;
import edu.iga.adi.sm.core.Solution;
import lombok.Builder;
import org.jzy3d.plot3d.primitives.Shape;

import java.util.List;

@Builder
public class HeatSurfaceProvider implements SurfaceProvider {

    private final SurfaceFactory surfaceFactory;

    @Override
    public List<Shape> provideSurfacesFor(Solution solution) {
        return Lists.newArrayList(surfaceFactory.createHotColdSurface(Jzy3dSolutionMapper.builder()
                .solution(solution)
                .build()));
    }

}
