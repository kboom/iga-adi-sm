package edu.iga.adi.sm.results.visualization;

import edu.iga.adi.sm.core.Solution;

import javax.swing.*;

public interface SolutionDrawer {

    void attachTo(JComponent component);

    void update(JComponent component, Solution step);

    void detachFrom(JComponent component);

}
