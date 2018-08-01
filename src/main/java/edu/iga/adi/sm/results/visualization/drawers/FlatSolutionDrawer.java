package edu.iga.adi.sm.results.visualization.drawers;

import edu.iga.adi.sm.core.Solution;
import edu.iga.adi.sm.results.visualization.SolutionDrawer;
import edu.iga.adi.sm.results.visualization.images.GreyscaleImageFactory;
import edu.iga.adi.sm.results.visualization.images.ImageFactory;
import lombok.Builder;

import javax.swing.*;
import java.awt.*;

@Builder
public class FlatSolutionDrawer extends JPanel implements SolutionDrawer {

    private static final Dimension PREFERRED_SIZE = new Dimension(600, 600);

    private final ImagePanel imagePanel = new ImagePanel();

    @Builder.Default
    private ImageFactory imageFactory = GreyscaleImageFactory.builder().build();

    @Override
    public void attachTo(JComponent component) {
        setPreferredSize(PREFERRED_SIZE);
        setLayout(new BorderLayout());
        add(imagePanel, BorderLayout.CENTER);
        component.add(this);
    }

    @Override
    public void update(JComponent component, Solution solution) {
        imagePanel.image = imageFactory.createImageFor(solution);
        imagePanel.repaint();
    }

    @Override
    public void detachFrom(JComponent component) {
        component.remove(this);
    }




    public class ImagePanel extends JComponent {

        private Image image;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D tGraphics2D = (Graphics2D) g;
            tGraphics2D.setBackground(Color.WHITE);
            tGraphics2D.setPaint(Color.WHITE);
            tGraphics2D.fillRect(0, 0, PREFERRED_SIZE.width, PREFERRED_SIZE.height);
            tGraphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
            if(image != null) {
                tGraphics2D.drawImage(image, 0, 0, PREFERRED_SIZE.width, PREFERRED_SIZE.height, null);
            }
        }

    }

}
