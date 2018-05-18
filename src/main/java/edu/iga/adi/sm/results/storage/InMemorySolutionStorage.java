package edu.iga.adi.sm.results.storage;

import edu.iga.adi.sm.core.Solution;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class InMemorySolutionStorage<T extends Solution> implements SolutionStorage<T> {

    private final Map<Integer, T> solutionMap = new HashMap<>();

    public InMemorySolutionStorage() {

    }

    @SneakyThrows
    public InMemorySolutionStorage(List<T> solutions) {
        storeAll(solutions.stream());
    }

    @Override
    public void store(int id, T solution) throws IOException {
        solutionMap.put(id, solution);
    }

    @Override
    public void storeAll(Stream<T> subsequentSolutions) throws IOException {
        solutionMap.putAll(subsequentSolutions.collect(toMap(
                solution -> (int) solution.metadata.get("solutionNumber"),
                Function.identity()
        )));
    }

    @Override
    public T retrieveOne(int id) throws IOException {
        return solutionMap.get(id);
    }

    @Override
    public Stream<T> retrieveAll() {
        return solutionMap.values().stream();
    }

}
