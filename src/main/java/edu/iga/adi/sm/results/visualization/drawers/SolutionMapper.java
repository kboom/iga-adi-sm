package edu.iga.adi.sm.results.visualization.drawers;

@FunctionalInterface
public interface SolutionMapper {

    double value(double x, double y, double z);

}
