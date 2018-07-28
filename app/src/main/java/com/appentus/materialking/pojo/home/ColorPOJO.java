package com.appentus.materialking.pojo.home;

import com.google.gson.annotations.SerializedName;

public class ColorPOJO {
    @SerializedName("color_id")
    String color_id;
    @SerializedName("colorName")
    String colorName;
    @SerializedName("colorCode")
    String colorCode;

    public String getColor_id() {
        return color_id;
    }

    public void setColor_id(String color_id) {
        this.color_id = color_id;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }
}
