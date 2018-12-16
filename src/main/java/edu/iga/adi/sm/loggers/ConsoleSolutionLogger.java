package edu.iga.adi.sm.loggers;

import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.direction.SolutionLogger;
import edu.iga.adi.sm.core.direction.Vertex;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ConsoleSolutionLogger implements SolutionLogger {

    private final Mesh mesh;

    public ConsoleSolutionLogger(Mesh mesh) {
        this.mesh = mesh;
    }

    private static void printMatrix(final Vertex v, int size, int nrhs) {
        for (int i = 1; i <= size; ++i) {
            for (int j = 1; j <= size; ++j) {
                System.out.printf("%6.3f ", v.m_a[i][j]);
            }
            System.out.printf("  |  ");
            for (int j = 1; j <= nrhs; ++j) {
                System.out.printf("%6.3f ", v.m_x[i][j]);
            }
            System.out.printf("  |  ");
            for (int j = 1; j <= nrhs; ++j) {
                System.out.printf("%6.3f ", v.m_b[i][j]);
            }
            System.out.println();
        }
    }

    @Override
    public void logMatrixValues(Iterable<Vertex> vertices, String comment) {
        System.out.println(comment + "\n");
        for (Vertex vertex : vertices) {
            printMatrix(vertex, 6, mesh.getDofsY());
            System.out.println("----");
        }
        System.out.println("");
    }

    @Override
    public void logValuesOfChildren(Collection<Vertex> parentVertices, String comment) {
        logMatrixValues(getAllChildrenOf(parentVertices), comment);
    }

    @Override
    public void logMatrixValuesAt(Vertex vertex, String message) {
        System.out.println(message);
        printMatrix(vertex, 6, mesh.getDofsY());
    }

    private List<Vertex> getAllChildrenOf(Collection<Vertex> parentVertices) {
        return parentVertices
                .stream()
                .flatMap(v -> v.getChildren().stream())
                .collect(Collectors.toList());
    }

}
