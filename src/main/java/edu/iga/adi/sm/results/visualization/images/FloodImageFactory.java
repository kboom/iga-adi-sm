package edu.iga.adi.sm.results.visualization.images;

import edu.iga.adi.sm.core.Solution;
import edu.iga.adi.sm.results.visualization.drawers.jzy3d.Jzy3dSolutionMapper;
import edu.iga.adi.sm.results.visualization.drawers.jzy3d.Jzy3dSurfaceFactory;
import lombok.Builder;
import org.jzy3d.chart.Chart;
import org.jzy3d.chart.factories.NewtChartComponentFactory;
import org.jzy3d.plot3d.rendering.canvas.Quality;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Builder
public class FloodImageFactory implements ImageFactory {

    private final Solution terrainSolution;

    @Override
    public BufferedImage createImageFor(Solution solution) {
        Jzy3dSurfaceFactory surfaceFactory = Jzy3dSurfaceFactory.builder().mesh(solution.getMesh()).build();

        Chart chart = NewtChartComponentFactory.chart(Quality.Nicest, "offscreen,1600,1600");

//        chart.setViewPoint(new Coord3d(0, 0, 100));

//        chart.setViewPoint(new Coord3d(0, 0, 10000));
//        chart.addLight(new Coord3d(0, 0, 20000));

        chart.getScene().getGraph().add(surfaceFactory.createSolidSurface(Jzy3dSolutionMapper.builder()
                .solution(terrainSolution).build()));

        chart.getScene().getGraph().add(surfaceFactory.createTransparentSurface(Jzy3dSolutionMapper.builder()
                .solution(solution).solutionMapper((x,y,z) -> Math.max(0, z - 1)).build()));  // ensure the water level is just a bit below the terrain

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

}
