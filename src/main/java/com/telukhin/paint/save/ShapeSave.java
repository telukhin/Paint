package com.telukhin.paint.save;

import com.telukhin.paint.Board;
import com.telukhin.paint.DisplayDriver;
import com.telukhin.paint.ShapeFactory;
import com.telukhin.paint.model.AbstractShape;
import com.telukhin.paint.model.Group;
import com.telukhin.paint.model.Oval;
import com.telukhin.paint.model.Shape;
import com.telukhin.paint.model.Square;
import com.telukhin.paint.model.Triangle;

import java.util.ArrayList;
import java.util.List;

public class ShapeSave {

    private Shape.ShapeType saveType;

    private List<ShapeSave> list;

    private int size;
    private int x;
    private int y;

    public ShapeSave() {
    }

    public Shape.ShapeType getSaveType() {
        return saveType;
    }

    public List<ShapeSave> getList() {
        return list;
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

    public static ShapeSave createShapeSave(Shape shape) {
        ShapeSave result = new ShapeSave();
        if (shape instanceof AbstractShape) {
            AbstractShape abstractShape = (AbstractShape) shape;
            result.x = abstractShape.getX();
            result.y = abstractShape.getY();
            result.size = abstractShape.getSize();
        }
        if (shape instanceof Square) {
            result.saveType = Shape.ShapeType.SQUARE;
        }
        if (shape instanceof Triangle) {
            result.saveType = Shape.ShapeType.TRIANGLE;
        }
        if (shape instanceof Oval) {
            result.saveType = Shape.ShapeType.OVAL;
        }
        if (shape instanceof Group) {
            Group group = (Group) shape;
            result.list = new ArrayList<ShapeSave>();
            for (Shape tmpShape : group.getList()) {
                result.list.add(createShapeSave(tmpShape));
            }
            result.saveType = Shape.ShapeType.GROUP;
        }
        return result;
    }

    public static Shape createShape(ShapeSave shapeSave, Board board, DisplayDriver driver) {
        Shape result = ShapeFactory.createShape(shapeSave.getSaveType(), driver, board, shapeSave.getX(), shapeSave.getY(), shapeSave.getSize());
        if (shapeSave.getSaveType() == Shape.ShapeType.GROUP) {
            Group group = (Group) result;
            List<Shape> groupList = new ArrayList<Shape>();
            for (ShapeSave tmpShapeSave : shapeSave.getList()) {
                groupList.add(createShape(tmpShapeSave, board, driver));
            }
            group.setList(groupList);
        }
        return result;
    }
}