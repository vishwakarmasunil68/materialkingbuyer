package com.appentus.materialking.model;

/**
 * Created by Hp on 1/18/2018.
 */

public class ChooseBrandModel {

    int itemImage;
    String itemTitle;
    boolean isSelectedBackground;


    public ChooseBrandModel(int itemImage, String itemTitle, boolean isSelectedBackground) {
        this.itemImage = itemImage;
        this.itemTitle = itemTitle;
        this.isSelectedBackground = isSelectedBackground;
    }

    public int getItemImage() {
        return itemImage;
    }

    public void setItemImage(int itemImage) {
        this.itemImage = itemImage;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public boolean isSelectedBackground() {
        return isSelectedBackground;
    }

    public void setSelectedBackground(boolean selectedBackground) {
        isSelectedBackground = selectedBackground;
    }
}
