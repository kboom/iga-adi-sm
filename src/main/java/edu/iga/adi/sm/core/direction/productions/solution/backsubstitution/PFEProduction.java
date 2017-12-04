package edu.iga.adi.sm.core.direction.productions.solution.backsubstitution;

import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.direction.Vertex;
import edu.iga.adi.sm.core.direction.productions.Production;

public class PFEProduction extends Production {

    PFEProduction(Vertex Vert, Mesh Mesh) {
        super(Vert, Mesh);
    }

    Vertex partial_forward_elimination(Vertex T, int elim, int size, int nrhs) {
        for (int irow = 1; irow <= elim; irow++) {
            double diag = T.m_a[irow][irow];
            for (int icol = irow; icol <= size; icol++) {
                T.m_a[irow][icol] /= diag;
            }
            for (int irhs = 1; irhs <= nrhs; irhs++) {
                T.m_b[irow][irhs] /= diag;
            }
            for (int isub = irow + 1; isub <= size; isub++) {
                double mult = T.m_a[isub][irow];
                for (int icol = irow; icol <= size; icol++) {
                    T.m_a[isub][icol] -= T.m_a[irow][icol] * mult;
                }
                for (int irhs = 1; irhs <= nrhs; irhs++) {
                    T.m_b[isub][irhs] -= T.m_b[irow][irhs] * mult;
                }
            }
        }
        return T;
    }

    Vertex partial_backward_substitution(Vertex T, int elim, int size, int nrhs) {
        for (int irhs = 1; irhs <= nrhs; irhs++) {
            for (int irow = elim; irow >= 1; irow--) {
                T.m_x[irow][irhs] = T.m_b[irow][irhs];
                for (int icol = irow + 1; icol <= size; icol++) {
                    T.m_x[irow][irhs] -= T.m_a[irow][icol] * T.m_x[icol][irhs];
                }
                T.m_x[irow][irhs] /= T.m_a[irow][irow];
            }
        }
        return T;
    }

    public Vertex apply(Vertex v) {
        return null;
    }

}