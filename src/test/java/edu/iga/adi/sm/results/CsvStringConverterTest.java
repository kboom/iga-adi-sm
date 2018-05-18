package edu.iga.adi.sm.results;

import edu.iga.adi.sm.support.Point;
import org.junit.Test;

import static edu.iga.adi.sm.core.SolutionGrid.solutionGrid;
import static org.assertj.core.api.Java6Assertions.assertThat;

public class CsvStringConverterTest {

    private final CsvStringConverter csvStringConverter = CsvStringConverter.builder().build();

    @Test
    public void convertToCsv() throws Exception {
        assertThat(csvStringConverter.convertToCsv(solutionGrid(
                Point.solutionPoint(1,2,3),
                Point.solutionPoint(3,4,-1),
                Point.solutionPoint(-1,-3,5),
                Point.solutionPoint(5,2,3)
        ))).isEqualTo("x,y,val\n" +
                "-1.00000000,-3.00000000,5.00000000\n" +
                "1.00000000,2.00000000,3.00000000\n" +
                "5.00000000,2.00000000,3.00000000\n" +
                "3.00000000,4.00000000,-1.00000000\n");
    }

}