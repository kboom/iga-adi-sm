package edu.iga.adi.sm.results.visualization;

import edu.iga.adi.sm.core.Solution;

import javax.swing.*;
import java.awt.*;

public class ResultsSnapshot extends JFrame {

    private SurfaceFactory surfaceFactory;
    private ChartFrame chartView;

    public ResultsSnapshot(String name, Solution solution) {
        attachAskToClose();
        SolutionMapper solutionMapper = new SolutionMapper(solution);
        surfaceFactory = new SurfaceFactory(solutionMapper, solution.getMesh());
        initialize(name);
    }

    private void initialize(String name) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1600, 900);
        setLocationRelativeTo(null);
        setTitle(name);
        setLayout(new BorderLayout());
        buildChart();
        redraw();
    }

    private void redraw() {
        chartView.redraw();
    }

    private void buildChart() {
        chartView = new ChartFrame(surfaceFactory);
        getContentPane().add(chartView, BorderLayout.CENTER);
        pack();
    }

    private void attachAskToClose() {
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(ResultsSnapshot.this,
                        "Are you sure to close this window?", "Really Closing?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    close();
                }
            }
        });
    }

    public void close() {
        System.exit(0);
    }

}
