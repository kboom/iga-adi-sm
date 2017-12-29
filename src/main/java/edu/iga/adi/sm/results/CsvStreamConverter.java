package edu.iga.adi.sm.results;

import edu.iga.adi.sm.support.Point;
import lombok.Builder;

import java.util.function.Predicate;
import java.util.stream.Stream;

import static edu.iga.adi.sm.support.Point.solutionPoint;

@Builder
class CsvStreamConverter {

    @Builder.Default
    private Predicate<? super String> filter = (s) -> true;

    Stream<String> toStringStream(Stream<Point> pointStream) {
        return pointStream
                .map(point -> String.format("%.8f,%.8f,%.8f", point.getX(), point.getY(), point.getValue()));
    }

    Stream<Point> toPointStream(Stream<String> reader) {
        return reader
                .map(line -> {
                    String[] split = line.split(",");
                    double x = Double.valueOf(split[0]);
                    double y = Double.valueOf(split[1]);
                    double z = Double.valueOf(split[2]);
                    return solutionPoint(x, y, z);
                });
    }

}
