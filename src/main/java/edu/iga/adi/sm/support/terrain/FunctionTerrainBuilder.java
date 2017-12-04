package edu.iga.adi.sm.support.terrain;

import edu.iga.adi.sm.core.Mesh;

import java.util.function.BiFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FunctionTerrainBuilder {

    private Mesh mesh;
    private BiFunction<Integer, Integer, Double> terrainF;

    public static FunctionTerrainBuilder get() {
        return new FunctionTerrainBuilder();
    }

    public FunctionTerrainBuilder withMesh(Mesh mesh) {
        this.mesh = mesh;
        return this;
    }

    public FunctionTerrainBuilder withFunction(BiFunction<Integer, Integer, Double> f) {
        this.terrainF = f;
        return this;
    }

    public Stream<TerrainPoint> build() {
        return IntStream.range(0, mesh.getElementsX()).boxed().parallel().flatMap(
                x -> IntStream.range(0, mesh.getElementsY()).mapToObj(y -> new TerrainPoint(x, y, terrainF.apply(x, y)))
        );
    }

}
