package edu.iga.adi.sm.results.visualization.drawers.jzy3d;

import edu.iga.adi.sm.core.Mesh;
import lombok.Builder;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapHotCold;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.colors.colormaps.ColorMapWhiteBlue;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid;
import org.jzy3d.plot3d.primitives.Shape;

@Builder
public class Jzy3dSurfaceFactory {

    private Mesh mesh;

    public final Shape createSolidSurface(Jzy3dSolutionMapper mapper) {
        final int steps = mesh.getElementsX();
        final Range range = new Range(0, mesh.getElementsX() - 1);

        Shape surface = org.jzy3d.plot3d.builder.Builder.buildOrthonormal(new OrthonormalGrid(range, steps, range, steps), mapper);
        surface.setColorMapper(new ColorMapper(new ColorMapRainbow(), surface.getBounds().getZmin(), surface.getBounds().getZmax(), new org.jzy3d.colors.Color(1, 1, 1, 1f)));
        surface.setWireframeDisplayed(false);
        surface.setFaceDisplayed(true);
        return surface;
    }

    public final Shape createHotColdSurface(Jzy3dSolutionMapper mapper) {
        final int steps = mesh.getElementsX();
        final Range range = new Range(0, mesh.getElementsX() - 1);

        Shape surface = org.jzy3d.plot3d.builder.Builder.buildOrthonormal(new OrthonormalGrid(range, steps, range, steps), mapper);
        surface.setColorMapper(new ColorMapper(new ColorMapHotCold(), surface.getBounds().getZmin(), surface.getBounds().getZmax(), new org.jzy3d.colors.Color(1, 1, 1, 1f)));
        surface.setWireframeDisplayed(false);
        surface.setFaceDisplayed(true);
        return surface;
    }


    public final Shape createTransparentSurface(Jzy3dSolutionMapper mapper) {
        final int steps = mesh.getElementsX();
        final Range range = new Range(0, mesh.getElementsX() - 1);

        Shape surface = org.jzy3d.plot3d.builder.Builder.buildOrthonormal(new OrthonormalGrid(range, steps, range, steps), mapper);
        surface.setColorMapper(new ColorMapper(new ColorMapWhiteBlue(), surface.getBounds().getZmin(), surface.getBounds().getZmax(), new org.jzy3d.colors.Color(1, 1, 1, 0.7f)));
        surface.setWireframeDisplayed(false);
        surface.setFaceDisplayed(true);
        return surface;
    }

}
