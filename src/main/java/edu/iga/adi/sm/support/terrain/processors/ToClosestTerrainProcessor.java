package edu.iga.adi.sm.support.terrain.processors;

import edu.iga.adi.sm.support.terrain.TerrainPoint;
import edu.iga.adi.sm.support.terrain.TerrainProcessor;
import edu.iga.adi.sm.support.terrain.support.KdTree2D;
import edu.iga.adi.sm.support.terrain.support.Point2D;

import java.util.stream.Stream;

public class ToClosestTerrainProcessor implements TerrainProcessor {

    private KdTree2D<Double> kdTree2D;

    @Override
    public Stream<TerrainPoint> analyze(Stream<TerrainPoint> terrainPointStream) {
        kdTree2D = new KdTree2D<>();
        return terrainPointStream.peek(terrainPoint -> kdTree2D.insert(toPoint(terrainPoint)));
    }

    @Override
    public Stream<TerrainPoint> apply(Stream<TerrainPoint> terrainPointStream) {
        return terrainPointStream.map(terrainPoint -> {
            Point2D<Double> nearestPoint = kdTree2D.nearest(toPoint(terrainPoint));
            return new TerrainPoint(terrainPoint, nearestPoint.getData());
        });
    }

    @Override
    public void teardown() {
        kdTree2D = null;
    }

    private Point2D<Double> toPoint(TerrainPoint terrainPoint) {
        return new Point2D<>(terrainPoint.x, terrainPoint.y, terrainPoint.z);
    }

}
