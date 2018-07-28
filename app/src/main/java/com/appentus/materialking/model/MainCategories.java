package com.appentus.materialking.model;

import android.os.Parcel;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by Hp on 1/12/2018.
 */

public class MainCategories extends ExpandableGroup<SubCategories>{

    String CategoryID;
    String Category;
    String CategoryImg;


    public MainCategories(String id,String title,String img, List<SubCategories> items) {
        super(title, items);
        this.CategoryID = id;
        this.Category = title;
        this.CategoryImg = img;
    }

    protected MainCategories(Parcel in) {
        super(in);
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        this.Category = category;
    }

    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String categoryID) {
        CategoryID = categoryID;
    }

    public String getCategoryImg() {
        return CategoryImg;
    }

    public void setCategoryImg(String categoryImg) {
        CategoryImg = categoryImg;
    }
}
