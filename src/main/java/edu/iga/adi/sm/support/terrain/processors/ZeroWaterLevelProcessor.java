package edu.iga.adi.sm.support.terrain.processors;

import edu.iga.adi.sm.support.terrain.TerrainPoint;
import edu.iga.adi.sm.support.terrain.TerrainProcessor;

import java.util.stream.Stream;

public class ZeroWaterLevelProcessor implements TerrainProcessor {

    @Override
    public Stream<TerrainPoint> analyze(Stream<TerrainPoint> terrainPointStream) {
        return terrainPointStream;
    }

    @Override
    public Stream<TerrainPoint> apply(Stream<TerrainPoint> terrainPointStream) {
        return terrainPointStream.map(terrainPoint -> new TerrainPoint(terrainPoint, Math.max(0, terrainPoint.z)));
    }

    @Override
    public void teardown() {

    }

}
