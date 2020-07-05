package com.telukhin.paint.save;

import java.util.ArrayList;
import java.util.List;

public class Save {

    private List<ShapeSave> list = new ArrayList<ShapeSave>();
    private int activeShapeIndex;

    public Save() {
    }

    public Save(List<ShapeSave> list, int activeShapeIndex) {
        this.list = list;
        this.activeShapeIndex = activeShapeIndex;
    }

    public List<ShapeSave> getList() {
        return list;
    }

    public void setList(List<ShapeSave> list) {
        this.list = list;
    }

    public int getActiveShapeIndex() {
        return activeShapeIndex;
    }

    public void setActiveShapeIndex(int activeShapeIndex) {
        this.activeShapeIndex = activeShapeIndex;
    }
}