package edu.iga.adi.sm;

import com.beust.jcommander.Parameter;
import edu.iga.adi.sm.core.Mesh;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

import static edu.iga.adi.sm.core.Mesh.aMesh;

@ToString
@Builder
@AllArgsConstructor
@Getter
public class SolverConfiguration {

    @Parameter(names = {"--log", "-l"})
    @Builder.Default
    private boolean isLogging = false;

    @Parameter(names = {"--problem"})
    @Builder.Default
    private String problemType = "heat";

    @Parameter(names = {"--plot", "-p"})
    @Builder.Default
    private boolean isPlotting = false;

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

    /*

        TERRAIN CONFIGURATION PARAMETERS

     */

    @Parameter(names = {"--terrain-file"})
    private String terrainFile;

    @Parameter(names = {"--terrain-scale"})
    @Builder.Default
    private int scale = 1; // 100

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

}
