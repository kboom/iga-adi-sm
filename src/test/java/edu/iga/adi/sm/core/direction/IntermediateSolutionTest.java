package edu.iga.adi.sm.core.direction;

import edu.iga.adi.sm.TestData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static edu.iga.adi.sm.TestData.generate2DMatrix;
import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(PowerMockRunner.class)
@PrepareForTest(IntermediateSolution.class)
public class IntermediateSolutionTest {

    private final IntermediateSolution intermediateSolution = new IntermediateSolution(TestData.DUMMY_MESH, generate2DMatrix(20, 20));

    @Test
    public void delegatesValueToPartialSolution() {
        IntermediateSolution spy = PowerMockito.spy(intermediateSolution);
        spy.getModifiedValue(1, 2);
        Mockito.verify(spy).getValue(1, 2);
    }

    @Test
    public void canComputeValue() {
        assertThat(intermediateSolution.getValue(3, 4)).isEqualTo(12);
        assertThat(intermediateSolution.getValue(5, 2)).isEqualTo(12);
        assertThat(intermediateSolution.getValue(8, 8)).isEqualTo(21);
    }

    @Test
    public void canGetMesh() {
        assertThat(intermediateSolution.getMesh()).isEqualTo(TestData.DUMMY_MESH);
    }

    @Test
    public void squareDifferenceWithItselfIsZero() {
        assertThat(intermediateSolution.squaredDifference(intermediateSolution)).isZero();
    }

    @Test
    public void squareDifferenceIsValid() {
        IntermediateSolution thisSolution = new IntermediateSolution(TestData.DUMMY_MESH, generate2DMatrix(20, 20));
        IntermediateSolution otherSolution = new IntermediateSolution(TestData.DUMMY_MESH, generate2DMatrix(20, 20, (i, j) -> (double) (2 * i + j)));
        assertThat(thisSolution.squaredDifference(otherSolution)).isEqualTo(33.94112549695428);
    }

}