package edu.iga.adi.sm.support.terrain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class TerrainPoint {

    public final double x, y, z;

    public TerrainPoint(TerrainPoint terrainPoint, double z) {
        this(terrainPoint.x, terrainPoint.y, z);
    }

}
