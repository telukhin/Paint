package com.telukhin.paint;

import com.telukhin.paint.model.Group;
import com.telukhin.paint.model.Shape;
import com.telukhin.paint.save.Save;
import com.telukhin.paint.save.ShapeSave;

import java.util.ArrayList;
import java.util.List;

public class Board {

    public enum Direction {UP, RIGHT, DOWN, LEFT}

    public interface ShapeSelectedListener {
        public void onShapeSelected(int index);
    }

    private DisplayDriver driver;
    private ShapeSelectedListener shapeSelectedListener;
    private List<Shape> list = new ArrayList<Shape>();
    private int activeShapeIndex;

    public Board(DisplayDriver driver, ShapeSelectedListener listener) {
        this.driver = driver;
        this.shapeSelectedListener = listener;
        add(Shape.ShapeType.OVAL);
    }

    public void move(Direction direction) {
        getActiveShape().move(direction);
        drawAll();
    }

    public void add(Shape.ShapeType shapeType) {
        list.add(ShapeFactory.createShape(shapeType, driver, this, 0, 0));
        activeShapeIndex = list.size() - 1;
        shapeSelectedListener.onShapeSelected(activeShapeIndex);
        drawAll();
    }

    private void drawAll() {
        driver.cleanScreen();
        for (int i = 0; i < list.size(); i++) {
            Shape shape = list.get(i);
            if (i == activeShapeIndex) {
                shape.setActive(true);
            } else {
                shape.setActive(false);
            }
            shape.draw();
        }
    }


    public void next() {
        activeShapeIndex++;
        if (activeShapeIndex >= list.size()) {
            activeShapeIndex = 0;
        }
        shapeSelectedListener.onShapeSelected(activeShapeIndex);
        drawAll();
    }

    public void previous() {
        activeShapeIndex--;
        if (activeShapeIndex < 0) {
            activeShapeIndex = list.size() - 1;
        }
        shapeSelectedListener.onShapeSelected(activeShapeIndex);
        drawAll();
    }

    private Shape getActiveShape() {
        return list.get(activeShapeIndex);
    }

    public void dec() {
        getActiveShape().dec();
        drawAll();
    }

    public void inc() {
        getActiveShape().inc();
        drawAll();
    }

    /**
     * remove active, and make active previous
     */
    public void remove() {
        if (list.size() == 1) {
            return;
        }
        list.remove(activeShapeIndex);
        previous();
        drawAll();
    }

    public void merge(int sceneX, int sceneY, boolean merge) {
        for (int i = 0; i < list.size(); i++) {
            Shape shape = list.get(i);
            if (shape.consistPoint(sceneX, sceneY)) {
                Shape result = new Group(shape.copy(), getActiveShape().copy());

                if (merge) {
                    // remove active and selected by mouse Shapes
                    Shape forRemove1 = list.get(i);
                    Shape forRemove2 = list.get(activeShapeIndex);
                    list.remove(forRemove1);
                    list.remove(forRemove2);
                }

                list.add(result);
                activeShapeIndex = list.size() - 1;
                shapeSelectedListener.onShapeSelected(activeShapeIndex);
                drawAll();
                break;
            }
        }
    }

    public Save makeSave() {
        List<ShapeSave> saveList = new ArrayList<ShapeSave>();
        for (Shape shape : list) {
            ShapeSave save = ShapeSave.createShapeSave(shape);
            saveList.add(save);
        }
        return new Save(saveList, activeShapeIndex);
    }

    public void loadSave(Save save) {
        list.clear();
        List<ShapeSave> saveList = save.getList();
        activeShapeIndex = save.getActiveShapeIndex();
        for (ShapeSave shapeSave : saveList) {
            Shape shape = ShapeSave.createShape(shapeSave, this, driver);
            list.add(shape);
        }
        drawAll();
    }

}