package edu.iga.adi.sm.results.storage;

import edu.iga.adi.sm.core.Solution;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public interface SolutionStorage<T extends Solution> {

    void setUp() throws IOException;

    void tearDown() throws IOException;

    void store(int id, T solution) throws IOException;

    void storeAll(Stream<T> subsequentSolutions) throws IOException;

    T retrieveOne(int id) throws IOException;

    Stream<T> retrieveAll();

}
