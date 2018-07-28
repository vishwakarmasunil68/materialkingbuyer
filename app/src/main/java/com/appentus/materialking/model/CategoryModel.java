package com.appentus.materialking.model;

import java.util.ArrayList;

/**
 * Created by ggg on 2/19/2018.
 */

public class CategoryModel {

    String id;
    String name;
    String image;
    String isParent;

    public String getTotalProductCount() {
        return totalProductCount;
    }

    public void setTotalProductCount(String totalProductCount) {
        this.totalProductCount = totalProductCount;
    }

    String totalProductCount;

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    String banner;


    public ArrayList<CategoryModel> getIsChilds() {
        return isChilds;
    }

    public void setIsChilds(ArrayList<CategoryModel> isChilds) {
        this.isChilds = isChilds;
    }

    ArrayList<CategoryModel> isChilds;




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIsParent() {
        return isParent;
    }

    public void setIsParent(String isParent) {
        this.isParent = isParent;
    }





}
