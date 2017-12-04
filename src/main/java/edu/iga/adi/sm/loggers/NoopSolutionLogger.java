package edu.iga.adi.sm.loggers;

import edu.iga.adi.sm.core.direction.SolutionLogger;
import edu.iga.adi.sm.core.direction.Vertex;

import java.util.Collection;

public class NoopSolutionLogger implements SolutionLogger {

    @Override
    public void logMatrixValues(Iterable<Vertex> vertices, String comment) {

    }

    @Override
    public void logValuesOfChildren(Collection<Vertex> parentVertices, String comment) {

    }

    @Override
    public void logMatrixValuesAt(Vertex vertex, String message) {

    }

}
