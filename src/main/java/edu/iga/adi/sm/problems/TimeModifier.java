package edu.iga.adi.sm.problems;

@FunctionalInterface
public interface TimeModifier {

    double getAddedValue(double x, double y, double time);

}
