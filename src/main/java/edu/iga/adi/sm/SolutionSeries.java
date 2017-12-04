package edu.iga.adi.sm;

import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.Solution;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public class SolutionSeries {

    private final List<Solution> subsequentSolutions;
    private Mesh mesh;

    SolutionSeries(List<Solution> subsequentSolutions, Mesh mesh) {
        this.subsequentSolutions = unmodifiableList(subsequentSolutions);
        this.mesh = mesh;
    }

    static SolutionsInTimeBuilder solutionsInTime() {
        return new SolutionsInTimeBuilder();
    }

    public Solution getFinalSolution() {
        return subsequentSolutions.get(subsequentSolutions.size() - 1);
    }

    public int getTimeStepCount() {
        return subsequentSolutions.size();
    }

    public Solution getSolutionAt(int timeStep) {
        return subsequentSolutions.get(timeStep);
    }

    public Mesh getMesh() {
        return mesh;
    }

    static class SolutionsInTimeBuilder {

        private final List<Solution> solutions = new ArrayList<>();
        private Mesh mesh;

        SolutionsInTimeBuilder withMesh(Mesh mesh) {
            this.mesh = mesh;
            return this;
        }

        void addSolution(Solution solution) {
            solutions.add(solution);
        }

        public SolutionSeries build() {
            return new SolutionSeries(solutions, mesh);
        }
    }

}