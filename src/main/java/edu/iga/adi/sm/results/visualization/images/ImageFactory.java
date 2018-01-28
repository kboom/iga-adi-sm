package edu.iga.adi.sm.results.visualization.images;

import edu.iga.adi.sm.core.Solution;

import java.awt.image.BufferedImage;

public interface ImageFactory {

    BufferedImage createImageFor(Solution solution);

}
