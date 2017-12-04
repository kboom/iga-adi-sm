package edu.iga.adi.sm.results.visualization;

import org.jzy3d.chart.Chart;
import org.jzy3d.chart.controllers.mouse.camera.AWTCameraMouseController;
import org.jzy3d.chart.factories.AWTChartComponentFactory;
import org.jzy3d.chart.factories.IChartComponentFactory;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.rendering.canvas.Quality;

import javax.swing.*;
import java.awt.*;

class ChartFrame extends JPanel {

    private static final Dimension PREFERRED_SIZE = new Dimension(600, 600);

    private final SurfaceFactory surfaceFactory;

    private Chart chart;

    ChartFrame(SurfaceFactory surfaceFactory) {
        this.surfaceFactory = surfaceFactory;
        initialize();
    }

    private void initialize() {
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
    }

    void redraw() {
        Shape newSurface = surfaceFactory.createSurface();
        chart.getScene().getGraph().getAll().clear();
        chart.getScene().getGraph().add(newSurface);
    }

}
