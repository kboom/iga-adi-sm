package edu.iga.adi.sm.support.terrain.processors;

import edu.iga.adi.sm.support.terrain.TerrainPoint;
import edu.iga.adi.sm.support.terrain.support.Point2D;
import org.junit.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class TranslationTerrainProcessorTest {

    @Test
    public void addsOffset() {
        final TranslationTerrainProcessor translationTerrainProcessor = TranslationTerrainProcessor
                .builder().center(new Point2D(1, 1)).build();

        assertThat(translationTerrainProcessor.apply(Stream.of(
                new TerrainPoint(0, 0, 0), new TerrainPoint(1, 3, 0)
        )).collect(Collectors.toList())).containsExactlyInAnyOrder(
                new TerrainPoint(1, 1, 0), new TerrainPoint(2, 4, 0)
        );
    }

    @Test
    public void scales() {
        final TranslationTerrainProcessor translationTerrainProcessor = TranslationTerrainProcessor
                .builder().scale(2).build();

        assertThat(translationTerrainProcessor.apply(Stream.of(
                new TerrainPoint(0, 0, 0), new TerrainPoint(5, 3, 0)
        )).collect(Collectors.toList())).containsExactlyInAnyOrder(
                new TerrainPoint(0, 0, 0), new TerrainPoint(10, 6, 0)
        );
    }

    @Test
    public void scalesFloats() {
        final TranslationTerrainProcessor translationTerrainProcessor = TranslationTerrainProcessor
                .builder().scale(100).build();

        assertThat(translationTerrainProcessor.apply(Stream.of(
                new TerrainPoint(0, 0, 0), new TerrainPoint(3.86, 4.81, 5)
        )).collect(Collectors.toList())).containsExactlyInAnyOrder(
                new TerrainPoint(0, 0, 0), new TerrainPoint(386, 481, 5)
        );
    }

    @Test
    public void handlesNegativePointsWithPositiveOffset() {
        final TranslationTerrainProcessor translationTerrainProcessor = TranslationTerrainProcessor
                .builder().center(new Point2D(7, 3)).build();

        assertThat(translationTerrainProcessor.apply(Stream.of(
                new TerrainPoint(-7, -1, 10), new TerrainPoint(1, -3, 20)
        )).collect(Collectors.toList())).containsExactlyInAnyOrder(
                new TerrainPoint(0, 2, 10), new TerrainPoint(8, 0, 20)
        );
    }

}