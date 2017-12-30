package edu.iga.adi.sm.results.storage;

import edu.iga.adi.sm.core.Solution;
import lombok.Builder;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.stream.Stream;

import static edu.iga.adi.sm.results.storage.StorageProcessor.NOOP_STORAGE_PROCESSOR;
import static org.apache.commons.lang3.SerializationUtils.deserialize;
import static org.apache.commons.lang3.SerializationUtils.serialize;

@Builder
public class FileSolutionStorage<T extends Solution> implements SolutionStorage<T> {

    private static final String NEWLINE = System.lineSeparator();
    public static final String SPLINE_ORDER = "splineOrder";
    public static final String ELEMENTS_X = "elementsX";
    public static final String ELEMENTS_Y = "elementsY";
    public static final String SOLUTION_EXTENSION = "solution";

    @Builder.Default
    private File solutionDirectory = new File(FileUtils.getUserDirectory(), "solution-" + getTimestamp());

    @Builder.Default
    private StorageProcessor storageProcessor = NOOP_STORAGE_PROCESSOR;


    @Override
    public void setUp() throws IOException {
        setUpOrFail(solutionDirectory);
        storageProcessor.afterSetUp(solutionDirectory);
    }

    @Override
    public void tearDown() throws IOException {
        storageProcessor.beforeTearDown(solutionDirectory);
        tearDownOrFail(solutionDirectory);
    }

    @Override
    public void store(int id, T solution) throws IOException {
        byte[] serializedSolution = serialize(solution);
        File file = solutionFile(id);
        FileUtils.writeByteArrayToFile(file, serializedSolution);
    }

    @Override
    public void storeAll(Stream<T> subsequentSolutions) throws IOException {
        final int[] index = {0};
        subsequentSolutions.forEachOrdered(solution -> {
            try {
                store(index[0]++, solution);
            } catch (IOException e) {
                throw new IllegalStateException("Could not save one of the solutions", e);
            }
        });
    }

    @Override
    public T retrieveOne(int id) throws IOException {
        File file = solutionFile(id);
        return retrieveFromFile(file);
    }

    @Override
    public Stream<T> retrieveAll() {
        return FileUtils.listFiles(solutionDirectory, new String[]{SOLUTION_EXTENSION}, false).stream()
                .sorted(new SolutionFileComparator())
                .map(this::retrieveFromFileOrFail);
    }

    private File solutionFile(int id) {
        return new File(solutionDirectory, solutionFileName(id));
    }

    @SuppressWarnings("unchecked")
    private T retrieveFromFile(File file) throws IOException {
        byte[] serializedSolution = FileUtils.readFileToByteArray(file);
        return (T) deserialize(serializedSolution);
    }

    private T retrieveFromFileOrFail(File file) {
        try {
            return retrieveFromFile(file);
        } catch (Exception e) {
            throw wrongFile(file, e);
        }
    }

    private void setUpOrFail(File directory) {
        try {
            FileUtils.forceMkdir(directory);
        } catch (Exception e) {
            throw wrongFile(directory, e);
        }
    }

    private void tearDownOrFail(File directory) {
        try {
            FileUtils.forceDeleteOnExit(directory);
        } catch (Exception e) {
            throw wrongFile(directory, e);
        }
    }

    private RuntimeException wrongFile(File file, Exception e) {
        return new IllegalStateException("Could not create directory " + file.getAbsolutePath(), e);
    }

    private String solutionFileName(int id) {
        return "run-" + id + "." + SOLUTION_EXTENSION;
    }

    private static class SolutionFileComparator implements Comparator<File> {

        @Override
        public int compare(File o1, File o2) {
            final String name1 = o1.getName();
            final String name2 = o2.getName();
            return name1.compareTo(name2);
        }


    }

    private static String getTimestamp() {
        return new SimpleDateFormat("yyyy-MM-dd-HH:mm").format(new Date());
    }

}
