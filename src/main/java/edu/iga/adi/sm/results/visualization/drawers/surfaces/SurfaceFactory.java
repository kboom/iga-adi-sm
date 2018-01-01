package edu.iga.adi.sm.results.visualization.drawers.surfaces;

import edu.iga.adi.sm.core.Mesh;
import lombok.Builder;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapGrayscale;
import org.jzy3d.colors.colormaps.ColorMapWhiteBlue;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid;
import org.jzy3d.plot3d.primitives.Shape;

@Builder
public class SurfaceFactory {

    private Mesh mesh;

    Shape createSolidSurface(Mapper mapper) {
        final int steps = mesh.getElementsX();
        final Range range = new Range(0, mesh.getElementsX() - 1);

        Shape surface = org.jzy3d.plot3d.builder.Builder.buildOrthonormal(new OrthonormalGrid(range, steps, range, steps), mapper);
        surface.setColorMapper(new ColorMapper(new ColorMapGrayscale(), surface.getBounds().getZmin(), surface.getBounds().getZmax(), new org.jzy3d.colors.Color(1, 1, 1, 1f)));
        surface.setWireframeDisplayed(false);
        surface.setWireframeColor(Color.WHITE);
        surface.setFaceDisplayed(true);
        return surface;
    }

    Shape createTransparentSurface(Mapper mapper) {
        final int steps = mesh.getElementsX();
        final Range range = new Range(0, mesh.getElementsX() - 1);

        Shape surface = org.jzy3d.plot3d.builder.Builder.buildOrthonormal(new OrthonormalGrid(range, steps, range, steps), mapper);
        surface.setColorMapper(new ColorMapper(new ColorMapWhiteBlue(), surface.getBounds().getZmin(), surface.getBounds().getZmax(), new org.jzy3d.colors.Color(1, 1, 1, .3f)));
        surface.setWireframeDisplayed(false);
        surface.setWireframeColor(Color.WHITE);
        surface.setFaceDisplayed(true);
        return surface;
    }

}
