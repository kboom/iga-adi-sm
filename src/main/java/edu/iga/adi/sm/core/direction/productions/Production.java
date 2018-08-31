package edu.iga.adi.sm.core.direction.productions;

import edu.iga.adi.sm.core.Mesh;
import edu.iga.adi.sm.core.direction.Vertex;


public abstract class Production {

    public Vertex m_vertex;
    public Mesh m_mesh;

    public Production(Vertex Vert, Mesh Mesh) {
        m_vertex = Vert;
        m_mesh = Mesh;
    }

    public abstract Vertex apply(Vertex v);

    public void run() {
        m_vertex = apply(m_vertex);
    }

    protected void swapDofs(int a, int b, int size, int nrhs) {
        for (int i = 1; i <= size; i++) {
            double temp = m_vertex.m_a[a][i];
            m_vertex.m_a[a][i] = m_vertex.m_a[b][i];
            m_vertex.m_a[b][i] = temp;
        }
        for (int i = 1; i <= size; i++) {
            double temp = m_vertex.m_a[i][a];
            m_vertex.m_a[i][a] = m_vertex.m_a[i][b];
            m_vertex.m_a[i][b] = temp;
        }
        for (int i = 1; i <= nrhs; i++) {
            double temp = m_vertex.m_b[a][i];
            m_vertex.m_b[a][i] = m_vertex.m_b[b][i];
            m_vertex.m_b[b][i] = temp;

            temp = m_vertex.m_x[a][i];
            m_vertex.m_x[a][i] = m_vertex.m_x[b][i];
            m_vertex.m_x[b][i] = temp;
        }
    }
}