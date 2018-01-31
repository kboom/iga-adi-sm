package edu.iga.adi.sm.results.visualization.images;

import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.Solution;
import edu.iga.adi.sm.results.visualization.drawers.jzy3d.Jzy3dSolutionMapper;
import org.jzy3d.chart.Chart;
import org.jzy3d.chart.factories.NewtChartComponentFactory;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapHotCold;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.rendering.canvas.Quality;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.stream.Collectors;

public class HeatImageFactory implements ImageFactory {

    @Override
    public BufferedImage createImageFor(Solution solution) {
        Chart chart = NewtChartComponentFactory.chart(Quality.Nicest, "offscreen");
        chart.getScene().getGraph().add(createHotColdSurface(solution));
        chart.render();

        try {
            File tempFile = File.createTempFile("texture-", ".PNG");
            tempFile.deleteOnExit();
            chart.screenshot(tempFile);
            return ImageIO.read(tempFile);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private Shape createHotColdSurface(Solution solution) {
        Mesh mesh = solution.getMesh();
        final int steps = mesh.getElementsX();
        final Range range = new Range(0, mesh.getElementsX() - 1);

//        Shape surface = org.jzy3d.plot3d.builder.Builder.buildOrthonormal(new OrthonormalGrid(range, steps, range, steps), solutionMapper(solution));
        Shape surface = org.jzy3d.plot3d.builder.Builder.buildDelaunay(solution.getSolutionGrid().getPoints().stream().map(p -> new Coord3d(p.getX(), p.getY(), p.getValue())).collect(Collectors.toList()));
        surface.setColorMapper(new ColorMapper(new ColorMapHotCold(), surface.getBounds().getZmin(), surface.getBounds().getZmax(), new org.jzy3d.colors.Color(1, 1, 1, 1f)));
        surface.setWireframeDisplayed(false);
        surface.setWireframeColor(org.jzy3d.colors.Color.WHITE);
        surface.setFaceDisplayed(true);
        return surface;
    }

    private Jzy3dSolutionMapper solutionMapper(Solution solution) {
        return Jzy3dSolutionMapper.builder()
                .solution(solution)
                .build();
    }

}
