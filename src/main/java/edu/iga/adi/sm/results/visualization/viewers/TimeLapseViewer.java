package edu.iga.adi.sm.results.visualization.viewers;

import edu.iga.adi.sm.results.series.SolutionSeries;
import edu.iga.adi.sm.core.Solution;
import edu.iga.adi.sm.results.visualization.SolutionDrawer;
import lombok.Builder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TimeLapseViewer extends JFrame {

    private static final Dimension PREFERRED_SIZE = new Dimension(600, 700);

    @Builder.Default
    private final String name = "Transient solution";

    private final SolutionSeries solutionSeries;
    private final SolutionDrawer solutionDrawer;

    private JSlider frameSlider;
    private JPanel framePanel;

    private volatile DisplayState displayState = DisplayState.SNAPSHOT;

    private Thread animationThread;

    @Builder
    public TimeLapseViewer(
            SolutionSeries solutionSeries,
            SolutionDrawer solutionDrawer
    ) {
        this.solutionSeries = solutionSeries;
        this.solutionDrawer = solutionDrawer;
        initialize();
        animate();
        initializeSlider();
        handleClose();
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

    private void drawStep(int step) {
        Solution solutionAt = solutionSeries.getSolutionAt(step);
        solutionDrawer.update(framePanel, solutionAt);
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

    private void animate() {
        animationThread = new Thread(() -> {
            final int timeStepCount = solutionSeries.getTimeStepCount();
            int timeStep = 0;
            while (displayState == DisplayState.ANIMATION_DISPLAY) {
                if (timeStep >= timeStepCount) {
                    timeStep = 0;
                }
                drawStep(timeStep++);
                frameSlider.setValue(timeStep);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    return;
                }
            }
        });
    }

    private void close() {
        stopAnimation();
        solutionDrawer.detachFrom(framePanel);
        System.exit(0);
    }

    private void initialize() {
        setTitle(name);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(PREFERRED_SIZE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        createFramePanel();
        createToggleButton();
        solutionDrawer.attachTo(framePanel);
        drawStep(0);
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
                    drawStep(source.getValue());
                }
            }
        });
        getContentPane().add(frameSlider, BorderLayout.SOUTH);
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

    private void createFramePanel() {
        framePanel = new JPanel();
        getContentPane().add(framePanel, BorderLayout.CENTER);
    }

    private void createToggleButton() {
        JToggleButton button = new JToggleButton("toggle animation");
        button.addActionListener(this::toggleAnimation);
        getContentPane().add(button, BorderLayout.NORTH);
        pack();
    }

    private enum DisplayState {

        ANIMATION_DISPLAY,
        STOPPING, SNAPSHOT

    }

}
