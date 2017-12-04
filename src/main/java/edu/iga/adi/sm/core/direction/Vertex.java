package edu.iga.adi.sm.core.direction;

import edu.iga.adi.sm.core.Mesh;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Vertex {

    public Vertex leftChild;
    public Vertex middleChild;
    public Vertex rightChild;
    public Mesh mesh;
    public double[][] m_a;
    public double[][] m_b;
    public double[][] m_x;
    public double beginning;
    public double ending;
    private Vertex parent;

    private Vertex() {
    }

    public static VertexBuilder aVertex() {
        return new VertexBuilder();
    }

    public void setLeftChild(Vertex leftChild) {
        this.leftChild = leftChild;
    }

    public void setMiddleChild(Vertex middleChild) {
        this.middleChild = middleChild;
    }

    public void setRightChild(Vertex rightChild) {
        this.rightChild = rightChild;
    }

    public Vertex getParent() {
        return parent;
    }

    public void setParent(Vertex parent) {
        this.parent = parent;
    }

    public List<Vertex> getChildren() {
        return Stream.of(leftChild, middleChild, rightChild)
                .filter(Objects::nonNull).collect(Collectors.toList());
    }

    public static class VertexBuilder {

        private Vertex vertex = new Vertex();

        public VertexBuilder withBeggining(double beggining) {
            vertex.beginning = beggining;
            return this;
        }

        public VertexBuilder withEnding(double ending) {
            vertex.ending = ending;
            return this;
        }

        public VertexBuilder withMesh(Mesh mesh) {
            vertex.mesh = mesh;
            return this;
        }

        public Vertex build() {
            vertex.m_a = new double[7][7];
            vertex.m_b = new double[7][vertex.mesh.getElementsY() + vertex.mesh.getSplineOrder() + 1];
            vertex.m_x = new double[7][vertex.mesh.getElementsY() + vertex.mesh.getSplineOrder() + 1];
            return vertex;
        }

    }

}