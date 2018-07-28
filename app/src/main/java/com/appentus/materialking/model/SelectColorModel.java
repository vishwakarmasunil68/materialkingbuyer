package com.appentus.materialking.model;

/**
 * Created by Hp on 2/7/2018.
 */

public class SelectColorModel {


    String color;
    boolean isColorSelected;


    public SelectColorModel(String color, boolean isColorSelected) {
        this.color = color;
        this.isColorSelected = isColorSelected;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isColorSelected() {
        return isColorSelected;
    }

    public void setColorSelected(boolean colorSelected) {
        isColorSelected = colorSelected;
    }
}


