package edu.iga.adi.sm.results.visualization.drawers;

import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.Solution;
import edu.iga.adi.sm.results.visualization.SolutionDrawer;
import edu.iga.adi.sm.results.visualization.util.SurfaceBuilder;
import lombok.Builder;
import org.jzy3d.chart.Chart;
import org.jzy3d.chart.controllers.mouse.camera.AWTCameraMouseController;
import org.jzy3d.chart.factories.AWTChartComponentFactory;
import org.jzy3d.chart.factories.IChartComponentFactory;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.rendering.canvas.Quality;

import javax.swing.*;
import java.awt.*;

public class SurfaceSolutionDrawer extends JPanel implements SolutionDrawer {

    private static final Dimension PREFERRED_SIZE = new Dimension(600, 600);

    private final SolutionMapper solutionMapper = new SolutionMapper();

    private Mesh mesh;
    private Chart chart;

    @Builder
    public SurfaceSolutionDrawer(Mesh mesh) {
        this.mesh = mesh;
    }

    @Override
    public void attachTo(JComponent component) {
        chart = AWTChartComponentFactory.chart(
                Quality.Advanced,
                IChartComponentFactory.Toolkit.swing);
        Component canvas = (Component) chart.getCanvas();
        canvas.setPreferredSize(PREFERRED_SIZE);
        add(canvas);
        setVisible(true);
        setPreferredSize(PREFERRED_SIZE);
        canvas.setBounds(getBounds());
        AWTCameraMouseController controller = new AWTCameraMouseController(chart);
        addMouseListener(controller);
        addMouseMotionListener(controller);
        addMouseWheelListener(controller);
        component.add(this);
    }

    @Override
    public void update(JComponent component, Solution solution) {
        solutionMapper.solution = solution;
        Shape newSurface = createSurface();
        chart.getScene().getGraph().getAll().clear();
        chart.getScene().getGraph().add(newSurface);
    }

    @Override
    public void detachFrom(JComponent component) {

    }

    private Shape createSurface() {
        return SurfaceBuilder.aSurface()
                .withMapper(solutionMapper)
                .withSquareRange(new Range(0, mesh.getElementsX() - 1))
                .withSteps(mesh.getElementsX()).build();
    }


    public class SolutionMapper extends Mapper {

        private Solution solution;

        @Override
        public double f(double v, double v1) {
            return solution.getValue(v, v1);
        }

    }

}
