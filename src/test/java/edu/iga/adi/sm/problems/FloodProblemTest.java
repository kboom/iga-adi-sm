package edu.iga.adi.sm.problems;

import edu.iga.adi.sm.SolverConfiguration;
import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


public class FloodProblemTest extends AbstractProblemTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void solvesFloodProblem() throws Exception {
        ProblemManagerTestResults testResults = launchSolver(SolverConfiguration.builder()
                .problemSize(12)
                .problemType("flood")
                .build());
        assertThat(testResults.getResultAsCsvString()).isEqualTo(readTestFile("flood.csv"));
    }

    @Test
    public void retrievesSolutionOfFloodProblem() throws Exception {
        File flood3Folder = temporaryFolder.newFolder("flood3");
        final String flood3FolderPath = flood3Folder.getAbsolutePath();
        File testFile = getTestFile("flood3.zip");

        FileUtils.copyFileToDirectory(testFile, temporaryFolder.getRoot());

        ProblemManagerTestResults testResults = launchSolver(SolverConfiguration.builder()
                .problemSize(12)
                .problemType("flood")
                .steps(3)
                .resultFile(flood3FolderPath)
                .retrieve(true)
                .build());

        assertThat(testResults.getAllResults()).containsExactly(
                getTestFileContent("run-1.csv"),
                getTestFileContent("run-2.csv"),
                getTestFileContent("run-3.csv")
        );
    }

    private File getTestFile(String name) {
        return new File(getClass().getResource("/flood/" + name).getFile());
    }

    private String getTestFileContent(String name) {
        try {
            return FileUtils.readFileToString(getTestFile(name), Charset.defaultCharset());
        } catch (IOException e) {
            throw new IllegalStateException("Malformed test, missing resource " + name, e);
        }
    }

}
