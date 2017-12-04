package edu.iga.adi.sm.support.terrain.processors;

import edu.iga.adi.sm.support.terrain.TerrainPoint;
import edu.iga.adi.sm.support.terrain.TerrainProcessor;

import java.util.stream.Stream;

public class NoOpTerrainProcessor implements TerrainProcessor {

    public static final NoOpTerrainProcessor NO_OP_TERRAIN_PROCESSOR = new NoOpTerrainProcessor();

    @Override
    public Stream<TerrainPoint> analyze(Stream<TerrainPoint> terrainPointStream) {
        return terrainPointStream;
    }

    @Override
    public Stream<TerrainPoint> apply(Stream<TerrainPoint> terrainPointStream) {
        return terrainPointStream;
    }

    @Override
    public void teardown() {

    }

}
