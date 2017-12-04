package edu.iga.adi.sm.core.direction;

import java.util.Collection;

public interface SolutionLogger {

    void logMatrixValues(Iterable<Vertex> vertices, String comment);

    void logValuesOfChildren(Collection<Vertex> parentVertices, String comment);

    void logMatrixValuesAt(Vertex vertex, String message);
}
