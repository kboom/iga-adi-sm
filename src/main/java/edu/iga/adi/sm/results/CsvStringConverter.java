package edu.iga.adi.sm.results;

import edu.iga.adi.sm.core.SolutionGrid;
import edu.iga.adi.sm.support.Point;
import lombok.Builder;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Builder
public class CsvStringConverter {

    private static final String NEWLINE = System.lineSeparator();

    @Builder.Default
    private String header = "x,y,val";

    public String convertToCsv(SolutionGrid solutionGrid) {
        List<Point> points = solutionGrid.getPoints();
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotEmpty(header)) {
            sb.append(header);
        }
        sb.append(NEWLINE);

        CsvStreamConverter converter = CsvStreamConverter.builder().filter((s) -> header.equals(s)).build();

        converter.toStringStream(points.stream()).forEachOrdered(s -> {
            sb.append(s);
            sb.append(NEWLINE);
        });

        return sb.toString();
    }

    public SolutionGrid retrieve(String csv) {
        CsvStreamConverter converter = CsvStreamConverter.builder().filter((s) -> !header.equals(s)).build();
        BufferedReader stringReader = new BufferedReader(new StringReader(csv));
        Stream<Point> pointStream = converter.toPointStream(stringReader.lines());
        return SolutionGrid.solutionGrid(pointStream.collect(Collectors.toList()));
    }

}
