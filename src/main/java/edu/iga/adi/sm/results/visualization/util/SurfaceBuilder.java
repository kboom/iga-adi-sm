package edu.iga.adi.sm.results.visualization.util;

import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.contour.MapperContourPictureGenerator;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Builder;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.primitives.axes.ContourAxeBox;
import org.jzy3d.plot3d.primitives.axes.IAxe;
import org.jzy3d.plot3d.primitives.axes.layout.providers.RegularTickProvider;

public class SurfaceBuilder {

    private Mapper mapper;
    private Range range;
    private int steps;

    private SurfaceBuilder() {

    }

    public static SurfaceBuilder aSurface() {
        return new SurfaceBuilder();
    }

    public SurfaceBuilder withMapper(Mapper mapper) {
        this.mapper = mapper;
        return this;
    }

    public SurfaceBuilder withSquareRange(Range range) {
        this.range = range;
        return this;
    }

    public SurfaceBuilder withSteps(int steps) {
        this.steps = steps;
        return this;
    }

    public Shape build() {
        Shape surface = Builder.buildOrthonormal(new OrthonormalGrid(range, steps, range, steps), mapper);
        surface.setColorMapper(new ColorMapper(new ColorMapRainbow(), surface.getBounds().getZmin(), surface.getBounds().getZmax(), new Color(1, 1, 1, .5f)));
        surface.setWireframeDisplayed(false);
        surface.setWireframeColor(Color.WHITE);
        surface.setFaceDisplayed(true);
        return surface;
    }

}
