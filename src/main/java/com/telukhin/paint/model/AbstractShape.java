package com.telukhin.paint.model;

import com.telukhin.paint.Board;
import com.telukhin.paint.Const;
import com.telukhin.paint.DisplayDriver;

public abstract class AbstractShape implements Shape {

    protected DisplayDriver driver;
    protected Board board;
    protected int size;
    protected int x;
    protected int y;
    protected boolean active;

    public AbstractShape(DisplayDriver driver, Board board, int x, int y, int size) {
        this.driver = driver;
        this.board = board;
        this.size = size;
        this.x = x;
        this.y = y;
    }

    @Override
    public abstract void draw();

    @Override
    public void move(Board.Direction direction) {
        switch (direction) {
            case UP:
                y -= Const.SPEED;
                break;
            case RIGHT:
                x += Const.SPEED;
                break;
            case DOWN:
                y += Const.SPEED;
                break;
            case LEFT:
                x -= Const.SPEED;
                break;
        }
    }

    @Override
    public void dec() {
        size -= Const.SIZE_INC;
        if (size < Const.MIN_SIZE) {
            size = Const.MIN_SIZE;
        }
    }

    @Override
    public void inc() {
        size += Const.SIZE_INC;
        if (size > Const.MAX_SIZE) {
            size = Const.MAX_SIZE;
        }
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean consistPoint(int sceneX, int sceneY) {
        return between(sceneX, x, x + size) && between(sceneY, y, y + size);
    }

    private static boolean between(int test, int a, int b) {
        return test >= a && test <= b;
    }

    public int getSize() {
        return size;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}