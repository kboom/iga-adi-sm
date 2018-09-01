package edu.iga.adi.sm;

import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.Problem;
import edu.iga.adi.sm.core.Solution;
import edu.iga.adi.sm.core.direction.IntermediateSolution;
import edu.iga.adi.sm.core.direction.Vertex;

import java.util.function.BiFunction;

import static edu.iga.adi.sm.core.Mesh.aMesh;

public class TestData {

    public static final int GRID_SIZE = 12;
    public static Mesh DUMMY_MESH = aMesh()
            .withElementsX(GRID_SIZE)
            .withElementsY(GRID_SIZE)
            .withResolutionX(12d)
            .withResolutionY(12d)
            .withOrder(2).build();

    public static double[][] generate2DMatrix(int rows, int cols) {
        return generate2DMatrix(rows, cols, (i, j) -> (double) (i + j));
    }

    public static double[][] generate2DMatrix(int rows, int cols, BiFunction<Integer, Integer, Double> fn) {
        final double[][] grid = new double[rows][cols];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = fn.apply(i, j);
            }
        }
        return grid;
    }

    public static double[] generatePartition(int size) {
        final double[] partition = new double[size];
        for (int i = 0; i < partition.length; i++) {
            partition[i] = i;
        }
        return partition;
    }

    public static double[][] generate2DMatrixFor(Mesh mesh) {
        return generate2DMatrix(mesh.getDofsX() + 1, mesh.getDofsY() + 1);
    }

    public static Vertex emptyVertex() {
        return vertex(0, 3);
    }

    public static Vertex nodeWithChildren() {
        Vertex parent = vertex(0, 3);
        parent.setLeftChild(vertex(0, 1));
        parent.setMiddleChild(vertex(1, 2));
        parent.setRightChild(vertex(2, 3));
        return parent;
    }

    private static Vertex vertex(double beginning, double ending) {
        return Vertex.aVertex().withMesh(DUMMY_MESH).withBeginning(beginning).withEnding(ending).build();
    }

    public static double[][] zeroed2DMatrix(int rows, int cols) {
        final double[][] grid = new double[rows][cols];
        for (int i = 0; i < grid.length; i++) {
            grid[i] = new double[cols];
        }
        return grid;
    }

    public static Problem dummyProblem() {
        return ((x, y) -> 1);
    }

    public static Solution dummySolution() {
        return new IntermediateSolution(DUMMY_MESH, generate2DMatrixFor(DUMMY_MESH));
    }
}
