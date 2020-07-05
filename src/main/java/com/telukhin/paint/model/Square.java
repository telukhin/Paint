package com.telukhin.paint.model;

import com.telukhin.paint.Board;
import com.telukhin.paint.DisplayDriver;

public class Square extends AbstractShape {

    private Shape.ShapeType saveType = ShapeType.SQUARE;

    public Square(DisplayDriver driver, Board board, int x, int y, int size) {
        super(driver, board, x, y, size);
    }

    @Override
    public void draw() {
        driver.drawSquare(x, y, size, active);
    }

    @Override
    public Shape copy() {
        return new Square(driver, board, x, y, size);
    }

}