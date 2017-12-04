package edu.iga.adi.sm.core.direction.productions.initialization;

import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.Problem;
import edu.iga.adi.sm.core.direction.Vertex;

public class A1 extends A {
    public A1(Vertex Vert, Mesh Mesh, Problem rhs) {
        super(Vert, Mesh, rhs);
    }

//    public Vertex apply(Vertex node) {
//        initializeCoefficientsMatrix(node);
//        fillRightHandSides(node);
//        return node;
//    }
//
//    private void initializeCoefficientsMatrix(Vertex node) {
//        useArbitraryCoefficients(node);
//    }
//
//    private void fillRightHandSides(Vertex node) {
//        for (int i = 1; i <= node.mesh.getDofsY(); i++) {
//            node.m_b[1][i] = 1.0;
//            node.m_b[2][i] = 1.0;
//            node.m_b[3][i] = 1.0;
//        }
//    }

}
