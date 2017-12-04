package edu.iga.adi.sm.support.terrain.support;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class KDTreeTest {

    @Test
    public void canFindNearest() {
        KdTree2D kdTree2D = new KdTree2D();
        kdTree2D.insert(new Point2D(1, 2));
        kdTree2D.insert(new Point2D(3, 4));
        kdTree2D.insert(new Point2D(6, -6));
        kdTree2D.insert(new Point2D(-100, 4));
        assertThat(kdTree2D.nearest(new Point2D(4, 5))).isEqualTo(new Point2D(3, 4));
    }

    @Test
    public void canFindNearestZero() {
        KdTree2D kdTree2D = new KdTree2D();
        kdTree2D.insert(new Point2D(0, 0));
        kdTree2D.insert(new Point2D(3, 4));
        kdTree2D.insert(new Point2D(6, -6));
        kdTree2D.insert(new Point2D(6, 4));
        assertThat(kdTree2D.nearest(new Point2D(0, 0))).isEqualTo(new Point2D(0, 0));
    }

    @Test
    public void canFindNearest2() {
        KdTree2D kdTree2D = new KdTree2D();
        kdTree2D.insert(new Point2D(1, 2));
        kdTree2D.insert(new Point2D(4, 4));
        kdTree2D.insert(new Point2D(6, -6));
        assertThat(kdTree2D.nearest(new Point2D(4, 5))).isEqualTo(new Point2D(4, 4));
    }

    @Test
    public void canFindNearest3() {
        KdTree2D kdTree2D = new KdTree2D();
        kdTree2D.insert(new Point2D(0, 0));
        kdTree2D.insert(new Point2D(50, 50));
        kdTree2D.insert(new Point2D(100, 100));
        assertThat(kdTree2D.nearest(new Point2D(65, 30))).isEqualTo(new Point2D(50, 50));
    }

}