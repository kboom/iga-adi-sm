package edu.iga.adi.sm.results.visualization;

import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.Solution;
import edu.iga.adi.sm.support.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;

public class SolutionAsBitmapSnapshot extends JFrame {

    public SolutionAsBitmapSnapshot(String title, Solution solution) throws HeadlessException {
        initialize(title, solution);
    }

    public static BufferedImage getGrayscale(int width, byte[] buffer) {
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

    private void initialize(String title, Solution solution) {
        setTitle(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 820);
        setPreferredSize(new Dimension(800, 820));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        final Mesh mesh = solution.getMesh();
        final int elementsY = mesh.getElementsY();
        final int elementsX = mesh.getElementsX();

        byte[] buffer = new byte[elementsX * elementsY];

        double maxValue = solution.getSolutionGrid().getPoints().stream().mapToDouble(Point::getValue).max().getAsDouble(); //maxValueOf(rhs);
        double minValue = solution.getSolutionGrid().getPoints().stream().mapToDouble(Point::getValue).min().getAsDouble();
        double offset = minValue < 0 ? Math.abs(minValue) : 0;

        for (int i = 0; i < elementsY; i++) {
            for (int j = 0; j < elementsX; j++) {
                double value = solution.getValue(i, j);
                buffer[(i * elementsY) + j] = (byte) (255 - (((value + offset) / (maxValue + offset)) * 255));
            }
        }

        BufferedImage image = getGrayscale(elementsY, buffer); // image.getScaledInstance(1600, 900, Image.SCALE_FAST);

        add(new ImagePanel(image), BorderLayout.CENTER);
    }

//    @Override
//    public void paint(Graphics g) {
//        g.drawImage(image, 0, 0, null);
//    }

    public class ImagePanel extends JComponent {

        private final BufferedImage image;

        ImagePanel(BufferedImage image) {
            this.image = image;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D tGraphics2D = (Graphics2D) g;
            tGraphics2D.setBackground(Color.WHITE);
            tGraphics2D.setPaint(Color.WHITE);
            tGraphics2D.fillRect(0, 0, 800, 800);
            tGraphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
            tGraphics2D.drawImage(image, 0, 0, 800, 800, null);
        }

    }

}
