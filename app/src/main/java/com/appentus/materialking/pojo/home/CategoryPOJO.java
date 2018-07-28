package com.appentus.materialking.pojo.home;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunil on 23-04-2018.
 */

public class CategoryPOJO implements Serializable{
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("image")
    private String image;
    @SerializedName("banner")
    private String banner;
    @SerializedName("isParent")
    private String isParent;
    @SerializedName("isChilds")
    private List<SubCategoryPOJO> subCategoryPOJOS;


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

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getIsParent() {
        return isParent;
    }

    public void setIsParent(String isParent) {
        this.isParent = isParent;
    }

    public List<SubCategoryPOJO> getSubCategoryPOJOS() {
        return subCategoryPOJOS;
    }

    public void setSubCategoryPOJOS(List<SubCategoryPOJO> subCategoryPOJOS) {
        this.subCategoryPOJOS = subCategoryPOJOS;
    }
}
