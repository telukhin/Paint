package com.telukhin.paint.model;

import com.telukhin.paint.Board;

public interface Shape {

    public static enum ShapeType {OVAL, SQUARE, TRIANGLE, GROUP}

    public void draw();

    public void move(Board.Direction direction);

    public void dec();

    public void inc();

    public void setActive(boolean active);

    public Shape copy();

    boolean consistPoint(int sceneX, int sceneY);
}