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
    public List<Jzy3dSurface> provideSurfacesFor(Jzy3dSolutionMapper mapper) {
        return Lists.newArrayList(createStaticSurface(), createChangingSurface(mapper));
    }

    private Jzy3dSurface createStaticSurface() {
        return Jzy3dSurface.builder().shape(surfaceFactory.createSolidSurface(Jzy3dSolutionMapper.builder()
                .solution(staticSolution)
                .build())).solutionDependent(false).build();
    }

    private Jzy3dSurface createChangingSurface(Jzy3dSolutionMapper mapper) {
        return Jzy3dSurface.builder().shape(surfaceFactory.createTransparentSurface(mapper)).build();
    }

}
