package edu.iga.adi.sm.support.terrain.processors;

import edu.iga.adi.sm.support.terrain.TerrainPoint;
import edu.iga.adi.sm.support.terrain.TerrainProcessor;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.stream.Stream;

import static edu.iga.adi.sm.support.terrain.processors.NoOpTerrainProcessor.NO_OP_TERRAIN_PROCESSOR;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChainedTerrainProcessor implements TerrainProcessor {

    private TerrainProcessor delegate;
    private TerrainProcessor next;

    public static ChainedTerrainProcessor startingFrom(TerrainProcessor terrainProcessor) {
        return new ChainedTerrainProcessor(terrainProcessor, NO_OP_TERRAIN_PROCESSOR);
    }

    @Override
    public Stream<TerrainPoint> analyze(Stream<TerrainPoint> terrainPointStream) {
        return next.analyze(delegate.analyze(terrainPointStream));
    }

    @Override
    public Stream<TerrainPoint> apply(Stream<TerrainPoint> terrainPointStream) {
        return next.apply(delegate.apply(terrainPointStream));
    }

    @Override
    public void teardown() {
        delegate.teardown();
        next.teardown();
    }

    public ChainedTerrainProcessor withNext(TerrainProcessor terrainProcessor) {
        return new ChainedTerrainProcessor(this, terrainProcessor);
    }

}
