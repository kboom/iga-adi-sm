package edu.iga.adi.sm.results.visualization.images;

import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.Solution;
import edu.iga.adi.sm.results.visualization.drawers.SolutionMapper;
import edu.iga.adi.sm.support.Point;
import lombok.Builder;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.util.function.ToDoubleFunction;

@Builder
public class GreyscaleImageFactory implements ImageFactory {

    @Builder.Default
    private SolutionMapper mapper = (x, y, z) -> z;

    @Override
    public BufferedImage createImageFor(Solution solution) {
        final Mesh mesh = solution.getMesh();
        final int elementsY = mesh.getElementsY();
        final int elementsX = mesh.getElementsX();

        byte[] buffer = new byte[elementsX * elementsY];

        double maxValue = solution.getSolutionGrid().getPoints().stream().mapToDouble(pointToValue()).max().getAsDouble(); //maxValueOf(rhs);
        double minValue = solution.getSolutionGrid().getPoints().stream().mapToDouble(pointToValue()).min().getAsDouble();
        double offset = minValue < 0 ? Math.abs(minValue) : 0;

        for (int i = 0; i < elementsY; i++) {
            for (int j = 0; j < elementsX; j++) {
                double value = mapper.value(i, j, solution.getValue(i, j));
                buffer[(i * elementsY) + j] = (byte) (((value + offset) / (maxValue + offset)) * 255);
            }
        }
        return getGrayscale(elementsX, buffer);
    }

    private ToDoubleFunction<Point> pointToValue() {
        return p -> mapper.value(p.getX(), p.getY(), p.getValue());
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

}
