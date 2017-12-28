package edu.iga.adi.sm.results;

import edu.iga.adi.sm.support.Point;
import org.junit.Test;

import static edu.iga.adi.sm.core.SolutionGrid.solutionGrid;
import static org.assertj.core.api.Java6Assertions.assertThat;

public class CsvConverterTest {

    private final CsvConverter csvConverter = CsvConverter.builder().build();

    @Test
    public void convertToCsv() throws Exception {
        assertThat(csvConverter.convertToCsv(solutionGrid(
                Point.solutionPoint(1,2,3),
                Point.solutionPoint(3,4,-1),
                Point.solutionPoint(-1,-3,5),
                Point.solutionPoint(5,2,3)
        ))).isEqualTo("x,y,val\n" +
                "-1.00,-3.00,5.00\n" +
                "1.00,2.00,3.00\n" +
                "5.00,2.00,3.00\n" +
                "3.00,4.00,-1.00\n");
    }

    @Test
    public void convertFromCsv() throws Exception {
        assertThat(csvConverter.convertFromCsv("x,y,val\n" +
                "-1.00,-3.00,5.00\n" +
                "1.00,2.00,3.00\n" +
                "5.00,2.00,3.00\n" +
                "3.00,4.00,-1.00\n"))
                .isEqualTo(solutionGrid(
                        Point.solutionPoint(1,2,3),
                        Point.solutionPoint(3,4,-1),
                        Point.solutionPoint(-1,-3,5),
                        Point.solutionPoint(5,2,3)
                ));
    }

}