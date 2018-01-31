package edu.iga.adi.sm.results;

import com.github.jaiimageio.plugins.tiff.BaselineTIFFTagSet;
import com.github.jaiimageio.plugins.tiff.TIFFDirectory;
import com.github.jaiimageio.plugins.tiff.TIFFField;
import com.github.jaiimageio.plugins.tiff.TIFFTag;
import lombok.Builder;
import lombok.SneakyThrows;

import javax.imageio.*;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageOutputStream;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;
import java.util.function.Consumer;

public class ImageStorage {

    private static final int DPI = 1000; // the minimum for most journals

    private final File baseDir;
    private int width = 800;
    private int height = 800;

    @Builder
    public ImageStorage(File baseDir) {
        if (!baseDir.exists()) {
            if (!baseDir.mkdirs()) {
                throw new IllegalStateException("Could not create directory structure");
            }
        }
        this.baseDir = baseDir;
    }

    @SneakyThrows
    public File saveImageAsTIFF(String name, BufferedImage bufferedImage) {
        File outputFile = new File(baseDir, name);
        if (outputFile.exists()) {
            throw new IllegalArgumentException("File already exists");
        }
        if (outputFile.canWrite()) {
            throw new IllegalArgumentException("Cannot write to file");
        }
        if (!outputFile.createNewFile()) {
            throw new IllegalStateException("Could not create new file");
        }

//        BufferedImage after = getScaledImage(bufferedImage);

        BufferedImage after = bufferedImage;

        writeImage(new Consumer<ImageWriter>() {

            @SneakyThrows
            @Override
            public void accept(ImageWriter writer) {
                ImageWriteParam writeParam = writer.getDefaultWriteParam();
                IIOMetadata metadata = createMetadata(writer, writeParam);
                try (ImageOutputStream stream = ImageIO.createImageOutputStream(outputFile)) {
                    writer.setOutput(stream);
                    writer.write(null, new IIOImage(after, null, metadata), writeParam);
                }

            }

        });
        return outputFile;
    }

    @SneakyThrows
    private IIOMetadata createMetadata(ImageWriter writer, ImageWriteParam writerParams) {
        // Get default metadata from writer
        ImageTypeSpecifier type = ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_BYTE_GRAY);
        IIOMetadata meta = writer.getDefaultImageMetadata(type, writerParams);

        // Convert default metadata to TIFF metadata
        TIFFDirectory dir = TIFFDirectory.createFromMetadata(meta);

        // Get {X,Y} resolution tags
        BaselineTIFFTagSet base = BaselineTIFFTagSet.getInstance();
        TIFFTag tagXRes = base.getTag(BaselineTIFFTagSet.TAG_X_RESOLUTION);
        TIFFTag tagYRes = base.getTag(BaselineTIFFTagSet.TAG_Y_RESOLUTION);

        // Create {X,Y} resolution fields
        TIFFField fieldXRes = new TIFFField(tagXRes, TIFFTag.TIFF_RATIONAL, 1, new long[][] { { (long) DPI, (long) 1 }, { (long) 0, (long) 0 } });
        TIFFField fieldYRes = new TIFFField(tagYRes, TIFFTag.TIFF_RATIONAL, 1, new long[][] { { (long) DPI, (long) 1 }, { (long) 0, (long) 0 } });

        // Add {X,Y} resolution fields to TIFFDirectory
        dir.addTIFFField(fieldXRes);
        dir.addTIFFField(fieldYRes);

        // Return TIFF metadata so it can be picked up by the IIOImage
        return dir.getAsMetadata();
    }


    private void writeImage(Consumer<ImageWriter> processor) {
        for (Iterator<ImageWriter> iw = ImageIO.getImageWritersByFormatName("TIFF"); iw.hasNext(); ) {
            ImageWriter writer = iw.next();
            ImageWriteParam writeParam = writer.getDefaultWriteParam();
            ImageTypeSpecifier typeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_BYTE_GRAY);
            IIOMetadata metadata = writer.getDefaultImageMetadata(typeSpecifier, writeParam);
            if (metadata.isReadOnly() || !metadata.isStandardMetadataFormatSupported()) {
                continue;
            }
            processor.accept(writer);
        }
    }

    private BufferedImage getScaledImage(BufferedImage bufferedImage) {
        final double scaleX = width / bufferedImage.getWidth();
        final double scaleY = height / bufferedImage.getHeight();

        BufferedImage after = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        AffineTransform at = AffineTransform.getScaleInstance(scaleX, scaleY);
        AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        after = scaleOp.filter(bufferedImage, after);
        return after;
    }

}
