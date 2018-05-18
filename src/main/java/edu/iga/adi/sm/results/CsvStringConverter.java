package edu.iga.adi.sm.results;

import edu.iga.adi.sm.core.SolutionGrid;
import edu.iga.adi.sm.support.Point;
import lombok.Builder;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

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

        points.stream().map(point -> String.format("%.8f,%.8f,%.8f", point.getX(), point.getY(), point.getValue()))
                .forEachOrdered(s -> {
                    sb.append(s);
                    sb.append(NEWLINE);
                });

        return sb.toString();
    }

}
