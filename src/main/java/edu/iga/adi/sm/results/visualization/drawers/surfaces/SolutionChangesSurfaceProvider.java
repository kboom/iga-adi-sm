package edu.iga.adi.sm.results.visualization.drawers.surfaces;

import com.beust.jcommander.internal.Lists;
import edu.iga.adi.sm.core.Solution;
import lombok.Builder;
import lombok.NonNull;
import org.jzy3d.plot3d.primitives.Shape;

import java.util.List;

@Builder
public class SolutionChangesSurfaceProvider implements SurfaceProvider {

    @NonNull
    private final SurfaceFactory surfaceFactory;

    @NonNull
    private final Solution staticSolution;

    @Override
    public List<Shape> provideSurfacesFor(Solution solution) {
        return Lists.newArrayList(createStaticSurface(), createChangingSurface(solution));
    }

    private Shape createStaticSurface() {
        return surfaceFactory.createSolidSurface(Jzy3dSolutionMapper.builder()
                .solution(staticSolution)
                .build());
    }

    private Shape createChangingSurface(Solution solution) {
        return surfaceFactory.createTransparentSurface(Jzy3dSolutionMapper.builder()
                .solution(solution)
                .build());
    }

}
