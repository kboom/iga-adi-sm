package edu.iga.adi.sm.core;

import edu.iga.adi.sm.support.Point;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@ToString
public class SolutionGrid {

    private static final Comparator<Point> NATURAL_POINT_ORDER_COMPARATOR = (o1, o2) -> {
        if (o1.getY() < o2.getY()) {
            return -1;
        } else if (o1.getY() > o2.getY()) {
            return 1;
        } else return Double.compare(o1.getX(), o2.getX());
    };
    private final Set<Point> points = new HashSet<>();

    private SolutionGrid(Collection<Point> points) {
        this.points.addAll(points);
    }

    public static SolutionGrid solutionGrid(Point... points) {
        return solutionGrid(stream(points).collect(Collectors.toList()));
    }

    public static SolutionGrid solutionGrid(Collection<Point> points) {
        return new SolutionGrid(points);
    }

    public List<Point> getPoints() {
        List<Point> listOfPoints = new ArrayList<>(points);
        listOfPoints.sort(NATURAL_POINT_ORDER_COMPARATOR);
        return listOfPoints;
    }

    public SolutionGrid withPrecisionTo(int precision) {
        return solutionGrid(points.stream().map(p -> Point.solutionPoint(p.getX(), p.getY(), BigDecimal.valueOf(p.getValue())
                .setScale(precision, BigDecimal.ROUND_HALF_UP).doubleValue())).collect(Collectors.toList()));
    }

    void addPoint(Point point) {
        points.add(point);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SolutionGrid that = (SolutionGrid) o;

        return points.size() == that.points.size()
                && points.containsAll(that.points)
                && that.points.containsAll(points);
    }

    @Override
    public int hashCode() {
        return points.hashCode();
    }

}
