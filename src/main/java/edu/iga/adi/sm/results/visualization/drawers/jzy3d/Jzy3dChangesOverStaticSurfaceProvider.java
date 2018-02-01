package edu.iga.adi.sm.results.visualization.drawers.jzy3d;

import com.beust.jcommander.internal.Lists;
import edu.iga.adi.sm.core.Solution;
import lombok.Builder;
import lombok.NonNull;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.primitives.Shape;

import java.util.List;

@Builder
public class Jzy3dChangesOverStaticSurfaceProvider implements Jzy3dSurfaceProvider {

    @NonNull
    private final Jzy3dSurfaceFactory surfaceFactory;

    @NonNull
    private final Solution staticSolution;

    @Override
    public List<Shape> provideSurfacesFor(Jzy3dSolutionMapper mapper) {
        return Lists.newArrayList(createStaticSurface(), createChangingSurface(mapper));
    }

    private Shape createStaticSurface() {
        return surfaceFactory.createSolidSurface(Jzy3dSolutionMapper.builder()
                .solution(staticSolution)
                .build());
    }

    private Shape createChangingSurface(Jzy3dSolutionMapper mapper) {
        return surfaceFactory.createTransparentSurface(mapper);
    }

}
