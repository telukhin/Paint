package com.telukhin.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DisplayDriver {

    private GraphicsContext gc;

    public DisplayDriver(GraphicsContext gc) {
        this.gc = gc;
    }

    public void cleanScreen() {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    }

    public void drawOval(int x, int y, int size, boolean filled) {
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.GREEN);
        gc.setLineWidth(3);
        if (filled) {
            gc.fillOval(x, y, size, size);
        } else {
            gc.strokeOval(x, y, size, size);
        }
    }

    public void drawSquare(int x, int y, int size, boolean filled) {
        gc.setFill(Color.BLUE);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(3);
        if (filled) {
            gc.fillRect(x, y, size, size);
        } else {
            gc.strokeRect(x, y, size, size);
        }
    }

    public void drawTriangle(int x, int y, int size, boolean filled) {
        gc.setFill(Color.RED);
        gc.setStroke(Color.RED);
        gc.setLineWidth(3);
        if (filled) {
            gc.fillPolygon(new double[]{x, x + size / 2.0, x + size}, new double[]{y + size, y, y + size}, 3);
        } else {
            gc.strokePolygon(new double[]{x, x + size / 2.0, x + size}, new double[]{y + size, y, y + size}, 3);
        }
    }
}