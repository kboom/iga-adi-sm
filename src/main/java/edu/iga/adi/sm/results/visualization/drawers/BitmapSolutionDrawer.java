package edu.iga.adi.sm.results.visualization.drawers;

import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.Solution;
import edu.iga.adi.sm.results.visualization.SolutionDrawer;
import edu.iga.adi.sm.support.Point;
import lombok.Builder;

import javax.swing.*;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;

@Builder
public class BitmapSolutionDrawer extends JPanel implements SolutionDrawer {

    private static final Dimension PREFERRED_SIZE = new Dimension(600, 600);

    private final ImagePanel imagePanel = new ImagePanel();

    @Override
    public void attachTo(JComponent component) {
        setSize(800, 820);
        setPreferredSize(PREFERRED_SIZE);
        setLayout(new BorderLayout());
        add(imagePanel, BorderLayout.CENTER);
        component.add(this);
    }

    @Override
    public void update(JComponent component, Solution solution) {
        final Mesh mesh = solution.getMesh();
        final int elementsY = mesh.getElementsY();
        final int elementsX = mesh.getElementsX();

        byte[] buffer = new byte[elementsX * elementsY];

        double maxValue = solution.getSolutionGrid().getPoints().stream().mapToDouble(edu.iga.adi.sm.support.Point::getValue).max().getAsDouble(); //maxValueOf(rhs);
        double minValue = solution.getSolutionGrid().getPoints().stream().mapToDouble(Point::getValue).min().getAsDouble();
        double offset = minValue < 0 ? Math.abs(minValue) : 0;

        for (int i = 0; i < elementsY; i++) {
            for (int j = 0; j < elementsX; j++) {
                double value = solution.getValue(i, j);
                buffer[(i * elementsY) + j] = (byte) (((value + offset) / (maxValue + offset)) * 255);
            }
        }

        imagePanel.image = getGrayscale(elementsY, buffer); // image.getScaledInstance(1600, 900, Image.SCALE_FAST);
        imagePanel.repaint();
    }

    @Override
    public void detachFrom(JComponent component) {
        component.remove(this);
    }

    private static BufferedImage getGrayscale(int width, byte[] buffer) {
        int height = buffer.length / width;
        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
        int[] nBits = {8};
        ColorModel cm = new ComponentColorModel(cs, nBits, false, true,
                Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
        SampleModel sm = cm.createCompatibleSampleModel(width, height);
        DataBufferByte db = new DataBufferByte(buffer, width * height);
        WritableRaster raster = Raster.createWritableRaster(sm, db, null);
        return new BufferedImage(cm, raster, false, null);
    }

    public class ImagePanel extends JComponent {

        private BufferedImage image;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D tGraphics2D = (Graphics2D) g;
            tGraphics2D.setBackground(Color.WHITE);
            tGraphics2D.setPaint(Color.WHITE);
            tGraphics2D.fillRect(0, 0, 800, 800);
            tGraphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
            if(image != null) {
                tGraphics2D.drawImage(image, 0, 0, 800, 800, null);
            }
        }

    }

}
