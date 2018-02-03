package edu.iga.adi.sm.results.visualization.drawers.jzy3d;

import edu.iga.adi.sm.core.Solution;
import edu.iga.adi.sm.results.visualization.SolutionDrawer;
import lombok.Builder;
import org.jzy3d.chart.Chart;
import org.jzy3d.chart.controllers.mouse.camera.AWTCameraMouseController;
import org.jzy3d.chart.factories.AWTChartComponentFactory;
import org.jzy3d.chart.factories.IChartComponentFactory;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.primitives.AbstractDrawable;
import org.jzy3d.plot3d.primitives.Point;
import org.jzy3d.plot3d.primitives.Polygon;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.rendering.canvas.Quality;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Jzy3dSurfaceSolutionDrawer extends JPanel implements SolutionDrawer {

    private static final Dimension PREFERRED_SIZE = new Dimension(600, 600);

    private Chart chart;

    private final Jzy3dSurfaceProvider jzy3dSurfaceProvider;

    private final Jzy3dSolutionMapper jzy3dSettableSolutionMapper = Jzy3dSolutionMapper.builder().build();

    @Builder
    public Jzy3dSurfaceSolutionDrawer(Jzy3dSurfaceProvider jzy3dSurfaceProvider) {
        this.jzy3dSurfaceProvider = jzy3dSurfaceProvider;
    }

    private List<Shape> shapes = new ArrayList<>();

    @Override
    public void attachTo(JComponent component) {
        chart = AWTChartComponentFactory.chart(
                Quality.Advanced,
                IChartComponentFactory.Toolkit.awt);
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
        jzy3dSettableSolutionMapper.setSolution(solution);
        if(shapes.isEmpty()) {
            shapes.addAll(jzy3dSurfaceProvider.provideSurfacesFor(jzy3dSettableSolutionMapper));
            chart.getScene().getGraph().getAll().clear();
            chart.getScene().getGraph().add(shapes);
        }
        shapes.forEach(shape -> {
            Jzy3dSolutionMapper.builder().solution(solution).build().remap(shape);
        });
    }

    @Override
    public void detachFrom(JComponent component) {

    }

}
