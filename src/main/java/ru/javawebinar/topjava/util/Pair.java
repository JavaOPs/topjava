package ru.javawebinar.topjava.util;

import java.util.Objects;

public class Pair<A, B> {
    private A argA;
    private final B argB;

    public Pair(A arg1, B arg2) {
        this.argA = arg1;
        this.argB = arg2;
    }

    public A getArgA() {
        return argA;
    }

    public void setArgA(A argA) {
        this.argA = argA;
    }

    public B getArgB() {
        return argB;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(argA, pair.argA) && Objects.equals(argB, pair.argB);
    }

    @Override
    public int hashCode() {
        return Objects.hash(argA, argB);
    }
}