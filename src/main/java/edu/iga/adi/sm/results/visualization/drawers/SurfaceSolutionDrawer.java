package edu.iga.adi.sm.results.visualization.drawers;

import edu.iga.adi.sm.core.Solution;
import edu.iga.adi.sm.results.visualization.SolutionDrawer;
import edu.iga.adi.sm.results.visualization.drawers.surfaces.SurfaceProvider;
import lombok.Builder;
import org.jzy3d.chart.Chart;
import org.jzy3d.chart.controllers.mouse.camera.AWTCameraMouseController;
import org.jzy3d.chart.factories.AWTChartComponentFactory;
import org.jzy3d.chart.factories.IChartComponentFactory;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.rendering.canvas.Quality;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Component;
import java.util.List;

public class SurfaceSolutionDrawer extends JPanel implements SolutionDrawer {

    private static final Dimension PREFERRED_SIZE = new Dimension(600, 600);

    private Chart chart;

    private final SurfaceProvider surfaceProvider;

    @Builder
    public SurfaceSolutionDrawer(SurfaceProvider surfaceProvider) {
        this.surfaceProvider = surfaceProvider;
    }

    @Override
    public void attachTo(JComponent component) {
        chart = AWTChartComponentFactory.chart(
                Quality.Intermediate,
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
        List<Shape> shapes = surfaceProvider.provideSurfacesFor(solution);
        chart.getScene().getGraph().getAll().clear();
        chart.getScene().getGraph().add(shapes);
    }

    @Override
    public void detachFrom(JComponent component) {

    }

}
