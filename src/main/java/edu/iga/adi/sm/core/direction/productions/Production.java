package edu.iga.adi.sm.core.direction.productions;

import edu.iga.adi.sm.core.direction.Vertex;

public abstract class Production {

    public Vertex node;

    public Production(Vertex node) {
        this.node = node;
    }

    protected abstract Vertex apply(Vertex v);

    public void run() {
        node = apply(node);
    }

    protected void swapDofs(int a, int b, int size, int nrhs) {
        for (int i = 1; i <= size; i++) {
            double temp = node.m_a[a][i];
            node.m_a[a][i] = node.m_a[b][i];
            node.m_a[b][i] = temp;
        }
        for (int i = 1; i <= size; i++) {
            double temp = node.m_a[i][a];
            node.m_a[i][a] = node.m_a[i][b];
            node.m_a[i][b] = temp;
        }
        for (int i = 1; i <= nrhs; i++) {
            double temp = node.m_b[a][i];
            node.m_b[a][i] = node.m_b[b][i];
            node.m_b[b][i] = temp;

            temp = node.m_x[a][i];
            node.m_x[a][i] = node.m_x[b][i];
            node.m_x[b][i] = temp;
        }
    }
}