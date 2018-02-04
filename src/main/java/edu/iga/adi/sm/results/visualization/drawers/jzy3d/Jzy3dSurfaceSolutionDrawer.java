package edu.iga.adi.sm.results.visualization.drawers.jzy3d;

import edu.iga.adi.sm.core.Solution;
import edu.iga.adi.sm.results.visualization.SolutionDrawer;
import lombok.Builder;
import org.jzy3d.chart.Chart;
import org.jzy3d.chart.controllers.mouse.camera.AWTCameraMouseController;
import org.jzy3d.chart.factories.AWTChartComponentFactory;
import org.jzy3d.chart.factories.IChartComponentFactory;
import org.jzy3d.plot3d.rendering.canvas.Quality;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Jzy3dSurfaceSolutionDrawer extends JPanel implements SolutionDrawer {

    private static final Dimension PREFERRED_SIZE = new Dimension(600, 600);

    private Chart chart;

    private final Jzy3dSurfaceProvider jzy3dSurfaceProvider;

    @Builder.Default
    private Jzy3dSolutionMapper jzy3dSettableSolutionMapper = Jzy3dSolutionMapper.builder().build();

    @Builder
    public Jzy3dSurfaceSolutionDrawer(Jzy3dSurfaceProvider jzy3dSurfaceProvider, Jzy3dSolutionMapper solutionMapper) {
        this.jzy3dSurfaceProvider = jzy3dSurfaceProvider;
        this.jzy3dSettableSolutionMapper = solutionMapper;
    }

    private List<Jzy3dSurface> surfaces = new ArrayList<>();

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
        jzy3dSettableSolutionMapper.setSolution(solution);
        if(surfaces.isEmpty()) {
            surfaces.addAll(jzy3dSurfaceProvider.provideSurfacesFor(jzy3dSettableSolutionMapper));
            chart.getScene().getGraph().getAll().clear();
            chart.getScene().getGraph().add(surfaces.stream().map(Jzy3dSurface::getShape).collect(Collectors.toList()));
        }
        surfaces.stream().filter(Jzy3dSurface::isSolutionDependent).map(Jzy3dSurface::getShape).forEach(jzy3dSettableSolutionMapper::remap);
    }

    @Override
    public void detachFrom(JComponent component) {

    }

}
