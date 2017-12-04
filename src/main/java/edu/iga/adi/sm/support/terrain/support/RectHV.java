package edu.iga.adi.sm.support.terrain.support;

public final class RectHV {
    private final double xmin, ymin;   // minimum x- and y-coordinates
    private final double xmax, ymax;   // maximum x- and y-coordinates

    /**
     * Initializes a new rectangle [<em>xmin</em>, <em>xmax</em>]
     * x [<em>ymin</em>, <em>ymax</em>].
     *
     * @param xmin the <em>x</em>-coordinate of the lower-left endpoint
     * @param xmax the <em>x</em>-coordinate of the upper-right endpoint
     * @param ymin the <em>y</em>-coordinate of the lower-left endpoint
     * @param ymax the <em>y</em>-coordinate of the upper-right endpoint
     * @throws IllegalArgumentException if any of {@code xmin},
     *                                  {@code xmax}, {@code ymin}, or {@code ymax}
     *                                  is {@code Double.NaN}.
     * @throws IllegalArgumentException if {@code xmax < xmin} or {@code ymax < ymin}.
     */
    public RectHV(double xmin, double ymin, double xmax, double ymax) {
        if (Double.isNaN(xmin) || Double.isNaN(xmax))
            throw new IllegalArgumentException("x-coordinate cannot be NaN");
        if (Double.isNaN(ymin) || Double.isNaN(ymax))
            throw new IllegalArgumentException("y-coordinates cannot be NaN");
        if (xmax < xmin || ymax < ymin) {
            throw new IllegalArgumentException("Invalid rectangle");
        }
        this.xmin = xmin;
        this.ymin = ymin;
        this.xmax = xmax;
        this.ymax = ymax;
    }

    /**
     * Returns the minimum <em>x</em>-coordinate of any point in this rectangle.
     *
     * @return the minimum <em>x</em>-coordinate of any point in this rectangle
     */
    public double xmin() {
        return xmin;
    }

    /**
     * Returns the maximum <em>x</em>-coordinate of any point in this rectangle.
     *
     * @return the maximum <em>x</em>-coordinate of any point in this rectangle
     */
    public double xmax() {
        return xmax;
    }

    /**
     * Returns the minimum <em>y</em>-coordinate of any point in this rectangle.
     *
     * @return the minimum <em>y</em>-coordinate of any point in this rectangle
     */
    public double ymin() {
        return ymin;
    }

    /**
     * Returns the maximum <em>y</em>-coordinate of any point in this rectangle.
     *
     * @return the maximum <em>y</em>-coordinate of any point in this rectangle
     */
    public double ymax() {
        return ymax;
    }

    /**
     * Returns the width of this rectangle.
     *
     * @return the width of this rectangle {@code xmax - xmin}
     */
    public double width() {
        return xmax - xmin;
    }

    /**
     * Returns the height of this rectangle.
     *
     * @return the height of this rectangle {@code ymax - ymin}
     */
    public double height() {
        return ymax - ymin;
    }

    /**
     * Returns true if the two rectangles intersect. This includes
     * <em>improper intersections</em> (at points on the boundary
     * of each rectangle) and <em>nested intersctions</em>
     * (when one rectangle is contained inside the other)
     *
     * @param that the other rectangle
     * @return {@code true} if this rectangle intersect the argument
     * rectangle at one or more points
     */
    public boolean intersects(RectHV that) {
        return this.xmax >= that.xmin && this.ymax >= that.ymin
                && that.xmax >= this.xmin && that.ymax >= this.ymin;
    }

    /**
     * Returns true if this rectangle contain the point.
     *
     * @param p the point
     * @return {@code true} if this rectangle contain the point {@code p},
     * possibly at the boundary; {@code false} otherwise
     */
    public boolean contains(Point2D p) {
        return (p.x() >= xmin) && (p.x() <= xmax)
                && (p.y() >= ymin) && (p.y() <= ymax);
    }

    /**
     * Returns the Euclidean distance between this rectangle and the point {@code p}.
     *
     * @param p the point
     * @return the Euclidean distance between the point {@code p} and the closest point
     * on this rectangle; 0 if the point is contained in this rectangle
     */
    public double distanceTo(Point2D p) {
        return Math.sqrt(this.distanceSquaredTo(p));
    }

    /**
     * Returns the square of the Euclidean distance between this rectangle and the point {@code p}.
     *
     * @param p the point
     * @return the square of the Euclidean distance between the point {@code p} and
     * the closest point on this rectangle; 0 if the point is contained
     * in this rectangle
     */
    public double distanceSquaredTo(Point2D p) {
        double dx = 0.0, dy = 0.0;
        if (p.x() < xmin) dx = p.x() - xmin;
        else if (p.x() > xmax) dx = p.x() - xmax;
        if (p.y() < ymin) dy = p.y() - ymin;
        else if (p.y() > ymax) dy = p.y() - ymax;
        return dx * dx + dy * dy;
    }

    /**
     * Compares this rectangle to the specified rectangle.
     *
     * @param other the other rectangle
     * @return {@code true} if this rectangle equals {@code other};
     * {@code false} otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        RectHV that = (RectHV) other;
        if (this.xmin != that.xmin) return false;
        if (this.ymin != that.ymin) return false;
        if (this.xmax != that.xmax) return false;
        if (this.ymax != that.ymax) return false;
        return true;
    }

    /**
     * Returns an integer hash code for this rectangle.
     *
     * @return an integer hash code for this rectangle
     */
    @Override
    public int hashCode() {
        int hash1 = ((Double) xmin).hashCode();
        int hash2 = ((Double) ymin).hashCode();
        int hash3 = ((Double) xmax).hashCode();
        int hash4 = ((Double) ymax).hashCode();
        return 31 * (31 * (31 * hash1 + hash2) + hash3) + hash4;
    }

    /**
     * Returns a string representation of this rectangle.
     *
     * @return a string representation of this rectangle, using the format
     * {@code [xmin, xmax] x [ymin, ymax]}
     */
    @Override
    public String toString() {
        return "[" + xmin + ", " + xmax + "] x [" + ymin + ", " + ymax + "]";
    }

}