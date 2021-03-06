package edu.iga.adi.sm.results.series;

import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.Solution;
import edu.iga.adi.sm.results.storage.SolutionStorage;
import lombok.Builder;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Builder
public class FromStorageSolutionSeries implements SolutionSeries {

    private final Mesh mesh;
    private final int solutionCount;
    private final SolutionStorage<Solution> solutionStorage;

    @Override
    @SneakyThrows
    public Solution getFinalSolution() {
        return solutionStorage.retrieveOne(solutionCount - 1);
    }

    @Override
    public int getTimeStepCount() {
        return solutionCount;
    }

    @Override
    @SneakyThrows
    public Solution getSolutionAt(int timeStep) {
        return solutionStorage.retrieveOne(timeStep);
    }

    @Override
    public Mesh getMesh() {
        return mesh;
    }

    @Override
    public Stream<Solution> getSubsequentSolutions() {
        return IntStream.range(0, solutionCount).mapToObj(i -> {
            try {
                return solutionStorage.retrieveOne(i);
            } catch (IOException e) {
                throw new IllegalStateException("Could not get intermediate solution " + i, e);
            }
        });
    }

}
