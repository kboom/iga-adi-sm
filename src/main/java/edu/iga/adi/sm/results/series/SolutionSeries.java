package edu.iga.adi.sm.results.series;

import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.Solution;

import java.util.function.ToDoubleBiFunction;
import java.util.stream.Stream;

public interface SolutionSeries {

    Solution getFinalSolution();

    int getTimeStepCount();

    Solution getSolutionAt(int timeStep);

    Mesh getMesh();

    void addModifier(ToDoubleBiFunction<Double, Double> fn);

    Stream<Solution> getSubsequentSolutions();

}
