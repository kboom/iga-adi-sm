package edu.iga.adi.sm.support.terrain;

import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.support.terrain.processors.TranslationTerrainProcessor;
import edu.iga.adi.sm.support.terrain.processors.ChainedTerrainProcessor;
import edu.iga.adi.sm.support.terrain.processors.ToClosestTerrainProcessor;
import edu.iga.adi.sm.support.terrain.storage.TerrainStorage;
import edu.iga.adi.sm.support.terrain.support.Point2D;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;

public class TerraformerTest implements TerrainStorage {

    private List<TerrainPoint> savedPoints;
    private List<TerrainPoint> originalPoints;

    @Before
    public void prepare() {
        this.savedPoints = new ArrayList<>();
        this.originalPoints = new ArrayList<>();
    }

    @Test
    public void isExactForExactMatch() {
        final Terraformer terraformer = buildAligned(0, 0, 1);

        originalPoints.addAll(newArrayList(
                new TerrainPoint(0, 0, 1), new TerrainPoint(0, 1, 2),
                new TerrainPoint(1, 0, 3), new TerrainPoint(1, 1, 4)
        ));

        terraformer.terraform(Mesh.aMesh().withElementsX(2).withElementsY(2).build());

        assertThat(savedPoints).containsExactlyInAnyOrder(
                new TerrainPoint(0, 0, 1), new TerrainPoint(0, 1, 2),
                new TerrainPoint(1, 0, 3), new TerrainPoint(1, 1, 4)
        );
    }

    @Test
    public void isExactForExactMatchWithOffset() {
        final Terraformer terraformer = buildAligned(100, 100, 1);

        originalPoints.addAll(newArrayList(
                new TerrainPoint(100, 100, 1), new TerrainPoint(100, 101, 2),
                new TerrainPoint(101, 100, 3), new TerrainPoint(101, 101, 4)
        ));

        terraformer.terraform(Mesh.aMesh().withElementsX(2).withElementsY(2).build());

        assertThat(savedPoints).containsExactlyInAnyOrder(
                new TerrainPoint(0, 0, 1), new TerrainPoint(0, 1, 2),
                new TerrainPoint(1, 0, 3), new TerrainPoint(1, 1, 4)
        );
    }

    @Test
    public void snapsToClosestPointsValues() {
        final Terraformer terraformer = buildAligned(0, 0, 1);

        originalPoints.addAll(newArrayList(
                new TerrainPoint(3, 4, 101),
                new TerrainPoint(4, 3, 303)
        ));

        terraformer.terraform(Mesh.aMesh().withElementsX(2).withElementsY(2).build());

        assertThat(savedPoints).containsExactlyInAnyOrder(
                new TerrainPoint(0, 0, 101), new TerrainPoint(0, 1, 101),
                new TerrainPoint(1, 0, 303), new TerrainPoint(1, 1, 101)
        );
    }

    @Test
    public void handlesSmallMeshes() {
        final Terraformer terraformer = buildAligned(72.443, 19.321, 0.001);

        originalPoints.addAll(newArrayList(
                new TerrainPoint(72.443, 19.321, 4),
                new TerrainPoint(72.444, 19.322, 101)
        ));

        terraformer.terraform(Mesh.aMesh().withElementsX(2).withElementsY(2).build());

        assertThat(savedPoints).containsExactlyInAnyOrder(
                new TerrainPoint(0, 0, 4), new TerrainPoint(0, 1, 4),
                new TerrainPoint(1, 0, 101), new TerrainPoint(1, 1, 101)
        );
    }

    @Override
    public Stream<TerrainPoint> loadTerrainPoints() {
        return this.originalPoints.stream();
    }

    @Override
    public void saveTerrainPoints(Stream<TerrainPoint> terrainPointStream) {
        this.savedPoints = terrainPointStream.collect(Collectors.toList());
    }

    private Terraformer buildAligned(double x, double y, double scale) {
        TranslationTerrainProcessor translator = TranslationTerrainProcessor.builder()
                .center(new Point2D(x, y)).scale(scale).build();

        return Terraformer.builder()
                .inputStorage(this)
                .outputStorage(this)
                .terrainProcessor(
                        ChainedTerrainProcessor.startingFrom(translator)
                                .withNext(new ToClosestTerrainProcessor())
                                .withNext(translator.reverse())
                )
                .build();
    }

}