package com.appentus.materialking.pojo.home;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 23-04-2018.
 */

public class HomePOJO {

    @SerializedName("banner")
    List<BannerPOJO> bannerPOJOS;
    @SerializedName("categories")
    List<CategoryPOJO> categoryPOJOS;


    public List<BannerPOJO> getBannerPOJOS() {
        return bannerPOJOS;
    }

    public void setBannerPOJOS(List<BannerPOJO> bannerPOJOS) {
        this.bannerPOJOS = bannerPOJOS;
    }

    public List<CategoryPOJO> getCategoryPOJOS() {
        return categoryPOJOS;
    }

    public void setCategoryPOJOS(List<CategoryPOJO> categoryPOJOS) {
        this.categoryPOJOS = categoryPOJOS;
    }
}
