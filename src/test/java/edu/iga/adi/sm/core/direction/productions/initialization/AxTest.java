package edu.iga.adi.sm.core.direction.productions.initialization;

import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.direction.Vertex;
import org.junit.Test;

import static edu.iga.adi.sm.core.Mesh.aMesh;
import static java.util.Arrays.deepToString;
import static org.assertj.core.api.Assertions.assertThat;

public class AxTest {

    private static final int GRID_SIZE = 12;

    private Mesh DUMMY_MESH = aMesh()
            .withElementsX(GRID_SIZE)
            .withElementsY(GRID_SIZE)
            .withResolutionX(12d)
            .withResolutionY(12d)
            .withOrder(2).build();


    @Test
    public void fillsWithQuadraturePoints() {
        Vertex leafNode = Vertex.aVertex().withMesh(DUMMY_MESH).build();

        Ax.builder()
                .node(leafNode)
                .problem((x, y) -> x + y)
                .coefficients(ExplicitMethodCoefficients.EXPLICIT_METHOD_COEFFICIENTS)
                .build()
                .apply(leafNode);

        assertThat(deepToString(leafNode.m_b)).isEqualTo(
                "[[0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], [0.0, 0.013888888888888885, 0.12500000000000006, 0.29166666666666685, 0.45833333333333354, 0.6250000000000003, 0.7916666666666662, 0.9583333333333335, 1.1250000000000002, 1.291666666666667, 1.4583333333333317, 1.6250000000000007, 1.7916666666666687, 1.611111111111111, 0.3333333333333336], [0.0, 0.08333333333333336, 0.6388888888888888, 1.3333333333333321, 2.000000000000001, 2.666666666666666, 3.3333333333333317, 4.000000000000002, 4.666666666666665, 5.333333333333333, 6.000000000000001, 6.6666666666666625, 7.3333333333333375, 6.583333333333337, 1.361111111111111], [0.0, 0.02777777777777778, 0.1944444444444444, 0.37499999999999994, 0.5416666666666667, 0.7083333333333331, 0.8750000000000001, 1.0416666666666667, 1.2083333333333328, 1.3750000000000004, 1.5416666666666679, 1.7083333333333326, 1.8750000000000002, 1.6805555555555556, 0.3472222222222222], [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0]]");
    }

}