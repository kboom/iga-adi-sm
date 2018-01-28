package edu.iga.adi.sm.support.terrain.processors;

import edu.iga.adi.sm.support.terrain.TerrainPoint;
import edu.iga.adi.sm.support.terrain.support.Point2D;
import org.junit.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class AdjustmentTerrainProcessorTest {

    @Test
    public void addsOffset() {
        final AdjustmentTerrainProcessor adjustmentTerrainProcessor = AdjustmentTerrainProcessor
                .builder().center(new Point2D(1, 1)).build();

        assertThat(adjustmentTerrainProcessor.apply(Stream.of(
                new TerrainPoint(0, 0, 0), new TerrainPoint(1, 3, 0)
        )).collect(Collectors.toList())).containsExactlyInAnyOrder(
                new TerrainPoint(1, 1, 0), new TerrainPoint(2, 4, 0)
        );
    }

    @Test
    public void scales() {
        final AdjustmentTerrainProcessor adjustmentTerrainProcessor = AdjustmentTerrainProcessor
                .builder().scale(2).build();

        assertThat(adjustmentTerrainProcessor.apply(Stream.of(
                new TerrainPoint(0, 0, 0), new TerrainPoint(5, 3, 0)
        )).collect(Collectors.toList())).containsExactlyInAnyOrder(
                new TerrainPoint(0, 0, 0), new TerrainPoint(10, 6, 0)
        );
    }

    @Test
    public void scalesFloats() {
        final AdjustmentTerrainProcessor adjustmentTerrainProcessor = AdjustmentTerrainProcessor
                .builder().scale(100).build();

        assertThat(adjustmentTerrainProcessor.apply(Stream.of(
                new TerrainPoint(0, 0, 0), new TerrainPoint(3.86, 4.81, 5)
        )).collect(Collectors.toList())).containsExactlyInAnyOrder(
                new TerrainPoint(0, 0, 0), new TerrainPoint(386, 481, 5)
        );
    }

}