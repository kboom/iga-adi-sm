package edu.iga.adi.sm.results.visualization.viewers;

import edu.iga.adi.sm.core.Solution;
import edu.iga.adi.sm.results.visualization.SolutionDrawer;
import lombok.Builder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;

@Builder
public class StaticViewer extends JFrame {

    private final String name;
    private final SolutionDrawer solutionDrawer;
    private final Solution solution;

    private JPanel framePanel;

    public StaticViewer display() {
        attachAskToClose();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1600, 900);
        setLocationRelativeTo(null);
        setTitle(name);
        setLayout(new BorderLayout());
        draw();
        setVisible(true);
        return this;
    }

    private void draw() {
        framePanel =  new JPanel();
        getContentPane().add(framePanel, BorderLayout.CENTER);
        solutionDrawer.attachTo(framePanel);
        solutionDrawer.update(framePanel, solution);
        pack();
    }

    private void attachAskToClose() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(StaticViewer.this,
                        "Are you sure to close this window?", "Really Closing?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    close();
                }
            }
        });
    }

    private void close() {
        solutionDrawer.detachFrom(framePanel);
        System.exit(0);
    }

}
