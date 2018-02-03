package edu.iga.adi.sm.support.terrain.processors;

import edu.iga.adi.sm.support.terrain.TerrainPoint;
import edu.iga.adi.sm.support.terrain.TerrainProcessor;
import edu.iga.adi.sm.support.terrain.support.Point2D;
import lombok.Builder;
import lombok.Getter;

import java.util.function.Function;
import java.util.stream.Stream;

@Getter
public class TranslationTerrainProcessor implements TerrainProcessor {

    private final Function<TerrainPoint, TerrainPoint> transform;
    private final Point2D center;
    private final double scale;

    @Builder
    public TranslationTerrainProcessor(Point2D center, double scale) {
        this(center, scale, point -> new TerrainPoint(
                center.x() + point.x * scale,
                center.y() + point.y * scale,
                point.z
        ));
    }

    private TranslationTerrainProcessor(
            Point2D center,
            double scale,
            Function<TerrainPoint, TerrainPoint> transform
    ) {
        this.center = center;
        this.scale = scale;
        this.transform = transform;
    }

    @Override
    public Stream<TerrainPoint> analyze(Stream<TerrainPoint> terrainPointStream) {
        return terrainPointStream;
    }

    @Override
    public Stream<TerrainPoint> apply(Stream<TerrainPoint> terrainPointStream) {
        return terrainPointStream.map(transform);
    }

    public TranslationTerrainProcessor reverse() {
        return new TranslationTerrainProcessor(center, scale, point -> new TerrainPoint(
                Math.round((point.x - center.x()) / scale),
                Math.round((point.y - center.y()) / scale),
                point.z
        ));
    }

    @Override
    public void teardown() {

    }

}
