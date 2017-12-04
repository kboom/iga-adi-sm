package edu.iga.adi.sm.results.visualization;

import edu.iga.adi.sm.SolutionSeries;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static edu.iga.adi.sm.results.visualization.TransientSolutionMapper.fromSolution;

public class TimeLapseViewer extends JFrame {

    private final SolutionSeries solutionSeries;
    private final TransientSolutionMapper transientSolutionMapper;
    private SurfaceFactory surfaceFactory;

    private JSlider frameSlider;
    private ChartFrame chartView;

    private volatile DisplayState displayState = DisplayState.SNAPSHOT;

    private Thread animationThread;

    public TimeLapseViewer(SolutionSeries solutionSeries) {
        this.solutionSeries = solutionSeries;
        transientSolutionMapper = fromSolution(solutionSeries);
        surfaceFactory = new SurfaceFactory(transientSolutionMapper, solutionSeries.getMesh());
        initialize();
        initializeSlider();
        animate();
        handleClose();
    }

    private void handleClose() {
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(TimeLapseViewer.this,
                        "Are you sure to close this window?", "Really Closing?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    close();
                }
            }
        });
    }

    private void initializeSlider() {
        frameSlider = new JSlider(JSlider.HORIZONTAL, 0, solutionSeries.getTimeStepCount(), 0);
        frameSlider.setMajorTickSpacing(10);
        frameSlider.setMinorTickSpacing(1);
        frameSlider.setPaintTicks(true);
        frameSlider.setPaintLabels(true);
        frameSlider.setVisible(true);
        frameSlider.addChangeListener((e) -> {
            if (displayState == DisplayState.SNAPSHOT) {
                JSlider source = (JSlider) e.getSource();
                if (!source.getValueIsAdjusting()) {
                    transientSolutionMapper.setStep(source.getValue());
                    redrawFrame();
                }
            }
        });
        getContentPane().add(frameSlider, BorderLayout.SOUTH);
    }

    private void redrawFrame() {
        chartView.redraw();
    }

    private void initialize() {
        setTitle("Simple example");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 800);
        setLocationRelativeTo(null);
        setTitle("test");
        setLayout(new BorderLayout());
        buildChart();
        redrawFrame();
    }

    private void buildChart() {
        chartView = new ChartFrame(surfaceFactory);
        getContentPane().add(chartView, BorderLayout.CENTER);
        getContentPane().add(addButton("toggle animation", this::toggleAnimation), BorderLayout.NORTH);
        pack();
    }

    private void toggleAnimation(ActionEvent e) {
        JToggleButton button = (JToggleButton) e.getSource();
        boolean selected = button.isSelected();
        if (selected) {
            startAnimation();
        } else {
            stopAnimation();
        }
    }

    private void startAnimation() {
        displayState = DisplayState.ANIMATION_DISPLAY;
        animationThread.start();
    }

    private void stopAnimation() {
        displayState = DisplayState.STOPPING;
        try {
            animationThread.join();
            displayState = DisplayState.SNAPSHOT;
        } catch (InterruptedException e1) {
            throw new IllegalStateException(e1);
        }
    }

    private JToggleButton addButton(String text, ActionListener changeListener) {
        JToggleButton button = new JToggleButton(text);
        button.addActionListener(changeListener);
        return button;
    }

    private void animate() {
        animationThread = new Thread(() -> {
            final int timeStepCount = solutionSeries.getTimeStepCount();
            int timeStep = 0;
            while (displayState == DisplayState.ANIMATION_DISPLAY) {
                if (timeStep >= timeStepCount) {
                    timeStep = 0;
                }
                transientSolutionMapper.setStep(timeStep++);
                frameSlider.setValue(timeStep);
                redrawFrame();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {

                }
            }
        });
    }

    public void close() {
        stopAnimation();
        System.exit(0);
    }

}
