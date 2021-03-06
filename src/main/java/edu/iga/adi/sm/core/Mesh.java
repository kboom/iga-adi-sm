package edu.iga.adi.sm.core;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@ToString
@EqualsAndHashCode
public class Mesh implements Serializable {

    private static final long serialVersionUID = 4620414724589559355L;

    private double resolutionX;

    private double resolutionY;

    private int elementsX;

    private int elementsY;

    private int splineOrder = 2;

    private int dofsX;

    private int dofsY;

    private Mesh() {
    }

    public static MeshBuilder aMesh() {
        return new MeshBuilder();
    }

    public double getResolutionX() {
        return resolutionX;
    }

    public double getDx() {
        return resolutionX / elementsX;
    }

    public double getDy() {
        return resolutionY / elementsY;
    }

    public int getElementsX() {
        return elementsX;
    }

    public int getElementsY() {
        return elementsY;
    }

    public int getSplineOrder() {
        return splineOrder;
    }

    public int getDofsX() {
        return dofsX;
    }

    public int getDofsY() {
        return dofsY;
    }

    public static class MeshBuilder {

        private Mesh mesh = new Mesh();

        public MeshBuilder withResolutionX(double resolutionX) {
            mesh.resolutionX = resolutionX;
            return this;
        }

        public MeshBuilder withResolutionY(double resolutionY) {
            mesh.resolutionY = resolutionY;
            return this;
        }

        public MeshBuilder withElementsX(int elementsX) {
            mesh.elementsX = elementsX;
            return this;
        }

        public MeshBuilder withElementsY(int elementsY) {
            mesh.elementsY = elementsY;
            return this;
        }

        public MeshBuilder withOrder(int order) {
            mesh.splineOrder = order;
            return this;
        }

        public Mesh build() {
            if (mesh.resolutionY == 0) {
                mesh.resolutionY = mesh.elementsY;
            }
            if (mesh.resolutionX == 0) {
                mesh.resolutionX = mesh.elementsX;
            }
            mesh.dofsX = mesh.elementsX + mesh.splineOrder;
            mesh.dofsY = mesh.elementsY + mesh.splineOrder;
            return mesh;
        }

    }

}
