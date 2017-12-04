package edu.iga.adi.sm.support.terrain.storage;

import edu.iga.adi.sm.support.terrain.TerrainPoint;

import java.util.stream.Stream;

public interface TerrainStorage {

    Stream<TerrainPoint> loadTerrainPoints();

    void saveTerrainPoints(Stream<TerrainPoint> terrainPointStream);

}
