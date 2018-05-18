package edu.iga.adi.sm.results.storage;


import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.Solution;
import edu.iga.adi.sm.core.direction.IntermediateSolution;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class FileSolutionStorageTest {

    private static final Solution DUMMY_SOLUTION = dummySolution(1);

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void canCreate() throws IOException {
        FileSolutionStorage.builder()
                .solutionDirectory(temporaryFolder.newFolder("test"))
                .build().setUp();
    }

    @Test
    public void solutionStoredAndRetrievedAreEqual() throws IOException {
        FileSolutionStorage<Solution> solutionStorage = FileSolutionStorage.builder()
                .solutionDirectory(temporaryFolder.newFolder("test"))
                .build();

        solutionStorage.setUp();

        solutionStorage.store(1, DUMMY_SOLUTION);

        assertThat(solutionStorage.retrieveOne(1)).isEqualTo(DUMMY_SOLUTION);
    }

    @Test
    public void canRetrieveAllSolutions() throws IOException {
        FileSolutionStorage<Solution> solutionStorage = FileSolutionStorage.builder()
                .solutionDirectory(temporaryFolder.newFolder("test"))
                .build();

        solutionStorage.setUp();

        IntermediateSolution firstSolution = dummySolution(1);
        IntermediateSolution secondSolution = dummySolution(2);

        solutionStorage.store(1, firstSolution);
        solutionStorage.store(2, secondSolution);

        assertThat(solutionStorage.retrieveAll().collect(Collectors.toList()))
                .containsExactly(firstSolution, secondSolution);
    }

    private static IntermediateSolution dummySolution(int seed) {
        Mesh dummyMesh = Mesh.aMesh().withElementsX(2).withElementsY(2).build();
        return new IntermediateSolution(dummyMesh, new double[][] {
                new double[] { seed, seed + 1 },
                new double[] { seed + 2, seed + 3 }
        });
    }

}