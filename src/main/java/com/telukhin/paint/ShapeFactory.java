package com.telukhin.paint;

import com.telukhin.paint.model.Group;
import com.telukhin.paint.model.Oval;
import com.telukhin.paint.model.Shape;
import com.telukhin.paint.model.Square;
import com.telukhin.paint.model.Triangle;

public class ShapeFactory {

    public static Shape createShape(Shape.ShapeType type, DisplayDriver driver, Board board, int x, int y) {
        return createShape(type, driver, board, x, y, Const.DEFAULT_SIZE);
    }

    public static Shape createShape(Shape.ShapeType type, DisplayDriver driver, Board board, int x, int y, int size) {
        switch (type) {
            case OVAL:
                return new Oval(driver, board, x, y, size);
            case SQUARE:
                return new Square(driver, board, x, y, size);
            case TRIANGLE:
                return new Triangle(driver, board, x, y, size);
            case GROUP:
                return new Group();
            default:
                return null;
        }
    }

}