package edu.iga.adi.sm.support;

import lombok.Getter;

@Getter
public class Tuple<X, Y> {

    public final X x;

    public final Y y;

    public Tuple(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    public static <X, Y> Tuple<X, Y> tupleOf(X x, Y y) {
        return new Tuple<>(x, y);
    }

}
