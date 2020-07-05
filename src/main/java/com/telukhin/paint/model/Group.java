package com.telukhin.paint.model;

import com.telukhin.paint.Board;

import java.util.ArrayList;
import java.util.List;

public class Group implements Shape {

    private List<Shape> list = new ArrayList<Shape>();

    public Group() {
    }

    private Group(List<Shape> list) {
        setList(list);
    }

    public Group(Shape s1, Shape s2) {
        addShape(s1);
        addShape(s2);
    }

    private void addShape(Shape shape) {
        if (shape instanceof Group) {
            Group group = (Group) shape;
            list.addAll(group.getList());
        } else {
            list.add(shape);
        }
    }

    @Override
    public void draw() {
        for (Shape shape : list) {
            shape.draw();
        }
    }

    @Override
    public void move(Board.Direction direction) {
        for (Shape shape : list) {
            shape.move(direction);
        }
    }

    @Override
    public void dec() {
        for (Shape shape : list) {
            shape.dec();
        }
    }

    @Override
    public void inc() {
        for (Shape shape : list) {
            shape.inc();
        }
    }

    @Override
    public void setActive(boolean active) {
        for (Shape shape : list) {
            shape.setActive(active);
        }
    }

    @Override
    public Shape copy() {
        List<Shape> resultList = new ArrayList<Shape>();
        for (Shape shape : list) {
            resultList.add(shape.copy());
        }
        return new Group(resultList);
    }

    @Override
    public boolean consistPoint(int sceneX, int sceneY) {
        for (Shape shape : list) {
            if (shape.consistPoint(sceneX, sceneY)) {
                return true;
            }
        }
        return false;
    }

    public void setList(List<Shape> list) {
        this.list.clear();
        for (Shape shape : list) {
            addShape(shape);
        }
    }

    public List<Shape> getList() {
        return list;
    }
}