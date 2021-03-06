package edu.iga.adi.sm;

import com.beust.jcommander.Parameter;
import edu.iga.adi.sm.core.Mesh;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static edu.iga.adi.sm.core.Mesh.aMesh;

@ToString
@Builder
@AllArgsConstructor
@Getter
public class SolverConfiguration {

    public static final String EXECUTION_TIMESTAMP = getTimestamp();
    public static final String TEMPORARY_DIR = getTemporaryDir("iga-adi-sm-solution");

    @Parameter(names = {"--log", "-l"})
    @Builder.Default
    private boolean isLogging = false;

    @Parameter(names = {"--problem"})
    @Builder.Default
    private String problemType = "heat";

    @Parameter(names = {"--plot", "-p"})
    @Builder.Default
    private boolean isPlotting = false;

    @Parameter(names = {"--store", "-w"})
    @Builder.Default
    private boolean isStoring = false;

    @Parameter(names = {"--images", "-i"})
    @Builder.Default
    private boolean isStoringImages = false;

    @Parameter(names = {"--images-dir"})
    @Builder.Default
    private String imagesDir = new File(TEMPORARY_DIR, "images").getAbsolutePath();

    @Parameter(names = {"--images-freq"})
    @Builder.Default
    private int imagesFrequencyPercentage = 10;

    @Parameter(names = {"--retrieve"})
    @Builder.Default
    private boolean retrieve = false;

    @Parameter(names = {"--store-file"})
    @Builder.Default
    private String resultFile = TEMPORARY_DIR;

    @Parameter(names = {"--problem-size", "-s"})
    @Builder.Default
    private int problemSize = 48;

    @Parameter(names = {"--max-threads", "-t"})
    @Builder.Default
    private int maxThreads = Runtime.getRuntime().availableProcessors();

    /*

        TIME DEPENDENT PROBLEMS

     */

    @Parameter(names = {"--delta", "-d"})
    @Builder.Default
    private double delta = 0.001;

    @Parameter(names = {"--steps", "-o"})
    @Builder.Default
    private int steps = 10;

    @Parameter(names = {"--downsample"})
    @Builder.Default
    private int downSampleRatio = 1;

    /*

        SURFACE-INITIAL-STATE-ENABLED PROBLEMS

     */

    @Parameter(names = {"--surface"})
    @Builder.Default
    private String initialSurface = "cone";

    @Parameter(names = {"--surface-formula"})
    private String initialSurfaceFormula;

    /*

        TERRAIN CONFIGURATION PARAMETERS

     */

    @Parameter(names = {"--terrain-file"})
    private String terrainFile;

    @Parameter(names = {"--terrain-scale"})
    @Builder.Default
    private double scale = 1; // 100

    @Parameter(names = {"--terrain-x-offset"})
    @Builder.Default
    private double xOffset = 0; // 560000;

    @Parameter(names = {"--terrain-y-offset"})
    @Builder.Default
    private double yOffset = 0; //180000;

    @Parameter(names = {"--ranks", "-r"})
    @Builder.Default
    private List<Integer> ranks = new ArrayList<>();

    public Mesh getMesh() {
        return aMesh()
                .withElementsX(problemSize)
                .withElementsY(problemSize)
                .withResolutionX(problemSize)
                .withResolutionY(problemSize)
                .withOrder(2).build();
    }

    private static String getTimestamp() {
        return new SimpleDateFormat("yyyy-MM-dd-HH-mm").format(new Date());
    }

    private static String getTemporaryDir(String name) {
        return new File(new File(FileUtils.getUserDirectory(), "iga-adi-mf"), name + "-" + EXECUTION_TIMESTAMP).getAbsolutePath();
    }

}
