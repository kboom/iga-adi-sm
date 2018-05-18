package edu.iga.adi.sm.results;

import edu.iga.adi.sm.core.Solution;
import edu.iga.adi.sm.results.series.SolutionSeries;
import edu.iga.adi.sm.results.visualization.images.HeatImageFactory;
import edu.iga.adi.sm.results.visualization.images.ImageFactory;
import lombok.Builder;
import lombok.NonNull;

import java.awt.image.BufferedImage;
import java.util.stream.IntStream;

@Builder
public class SnapshotSaver {

    @NonNull
    private final ImageStorage imageStorage;
    @NonNull
    private final ImageFactory imageFactory;
    @NonNull
    private final int frequencyPercentage;

    @Builder.Default
    private String nameTemplate = "snapshot-%s.tiff";

    public void storeSnapshots(SolutionSeries solutionSeries) {
        final int steps = solutionSeries.getTimeStepCount();
        final int framePickingInterval = Math.max(1, steps * frequencyPercentage / 100);

        storeOne(solutionSeries.getSolutionAt(0), "start");

        IntStream.range(0, steps).filter(i -> (i + framePickingInterval / 2) % framePickingInterval == 0)
                .forEach(frame -> {
                    Solution solutionAt = solutionSeries.getSolutionAt(frame);
                    BufferedImage imageFor = imageFactory.createImageFor(solutionAt);
                    imageStorage.saveImageAsTIFF(String.format(nameTemplate, frame), imageFor);
                });


        storeOne(solutionSeries.getFinalSolution(), "end");
    }

    public void storeOne(Solution solution, String name) {
        imageStorage.saveImageAsTIFF(String.format(nameTemplate, name),
                imageFactory.createImageFor(solution)
        );
    }

}
