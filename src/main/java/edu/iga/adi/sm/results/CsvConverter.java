package edu.iga.adi.sm.results;

import edu.iga.adi.sm.core.SolutionGrid;
import edu.iga.adi.sm.support.Point;
import lombok.Builder;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.List;
import java.util.stream.Collectors;

import static edu.iga.adi.sm.support.Point.solutionPoint;

@Builder
public class CsvConverter {

    private static final String NEWLINE = System.lineSeparator();

    @Builder.Default
    private String header = "x,y,val";

    public String convertToCsv(SolutionGrid solutionGrid) {
        List<Point> points = solutionGrid.getPoints();
        StringBuilder sb = new StringBuilder();
        if(StringUtils.isNotEmpty(header)) {
            sb.append(header);
        }
        sb.append(NEWLINE);
        for (Point point : points) {
            sb.append(String.format("%.2f,%.2f,%.2f", point.getX(), point.getY(), point.getValue()));
            sb.append(NEWLINE);
        }
        return sb.toString();
    }

    public SolutionGrid convertFromCsv(String csv) {
        return SolutionGrid.solutionGrid(readPoints(csv));
    }

    private List<Point> readPoints(String csv) {
        return new BufferedReader(new StringReader(csv))
        .lines()
        .filter(line -> line.equals(header))
        .map(line -> {
            String[] split = line.split(" ");
            double x = Double.valueOf(split[0]);
            double y = Double.valueOf(split[1]);
            double z = Double.valueOf(split[2]);
            return solutionPoint(x, y, z);
        })
        .collect(Collectors.toList());
    }

}
