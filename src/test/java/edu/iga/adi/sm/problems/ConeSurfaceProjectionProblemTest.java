package edu.iga.adi.sm.problems;

import edu.iga.adi.sm.AcceptanceTest;
import edu.iga.adi.sm.core.Mesh;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static edu.iga.adi.sm.core.Mesh.aMesh;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class ConeSurfaceProjectionProblemTest {

    public static final int GRID_SIZE = 12;
    public static Mesh DUMMY_MESH = aMesh()
            .withElementsX(GRID_SIZE)
            .withElementsY(GRID_SIZE)
            .withResolutionX(12d)
            .withResolutionY(12d)
            .withOrder(2).build();

    private final int x;
    private final int y;
    private final int expectedResult;

    public ConeSurfaceProjectionProblemTest(int x, int y, int expectedResult) {
        this.x = x;
        this.y = y;
        this.expectedResult = expectedResult;
    }

    @Test
    public void evaluatesExpression() {
        assertThat(SurfaceProjectionProblems.coneInTheMiddle(DUMMY_MESH).getValue(x, y)).isEqualTo(expectedResult);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                { 5, 0, 0},
                { 8, 8, 1},
                { 7, 7, 1},
                { 6, 6, 1},
                { 9, 9, 0},
                { 10, 9, 0},
                { 11, 11, 0},
                { 9, 9, 0},
        });
    }

}