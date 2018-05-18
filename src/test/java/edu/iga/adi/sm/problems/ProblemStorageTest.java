package edu.iga.adi.sm.problems;

import edu.iga.adi.sm.SolverConfiguration;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;

import static org.apache.commons.io.FileUtils.directoryContains;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ProblemStorageTest extends AbstractProblemTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void producesResultZip() throws Exception {
        final String temporaryFolderPath = temporaryFolder.newFolder().getAbsolutePath();
        launchSolver(SolverConfiguration.builder()
                .problemSize(12)
                .problemType("flood")
                .isStoring(true)
                .resultFile(temporaryFolderPath)
                .build());

        File expectedZipFile = new File(temporaryFolderPath + ".zip");

        assertThat(directoryContains(temporaryFolder.getRoot(), expectedZipFile))
                .isTrue();
    }

}
