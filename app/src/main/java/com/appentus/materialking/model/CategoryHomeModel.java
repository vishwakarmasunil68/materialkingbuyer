package com.appentus.materialking.model;

/**
 * Created by Hp on 1/17/2018.
 */

public class CategoryHomeModel {

    String mainTitle,description,otherData;

    public CategoryHomeModel(String mainTitle, String description, String otherData) {
        this.mainTitle = mainTitle;
        this.description = description;
        this.otherData = otherData;
    }

    public String getMainTitle() {
        return mainTitle;
    }

    public void setMainTitle(String mainTitle) {
        this.mainTitle = mainTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOtherData() {
        return otherData;
    }

    public void setOtherData(String otherData) {
        this.otherData = otherData;
    }

}
