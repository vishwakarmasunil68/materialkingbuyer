package com.appentus.materialking.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Hp on 1/12/2018.
 */

public class SubCategories implements Parcelable {

    String subCategoryID;
    String subCategory;
    String subCategoryImg;

    public SubCategories(String subCategoryID,String name,String img) {
        this.subCategoryID = subCategoryID;
        this.subCategory = name;
        this.subCategoryImg = img;
    }


    protected SubCategories(Parcel in) {
        subCategory = in.readString();
        subCategoryID = in.readString();
        subCategoryImg = in.readString();

    }

    public String getSubCategoryID() {
        return subCategoryID;
    }

    public void setSubCategoryID(String subCategoryID) {
        this.subCategoryID = subCategoryID;
    }

    public String getSubCategoryImg() {
        return subCategoryImg;
    }

    public void setSubCategoryImg(String subCategoryImg) {
        this.subCategoryImg = subCategoryImg;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(subCategory);
    }

    public static final Creator<SubCategories> CREATOR = new Creator<SubCategories>() {
        @Override
        public SubCategories createFromParcel(Parcel in) {
            return new SubCategories(in);
        }
        @Override
        public SubCategories[] newArray(int size) {
            return new SubCategories[size];
        }
    };
}
