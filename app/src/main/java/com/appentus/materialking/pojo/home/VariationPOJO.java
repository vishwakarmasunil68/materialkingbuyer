package com.appentus.materialking.pojo.home;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 23-04-2018.
 */

public class VariationPOJO {
    @SerializedName("variation_id")
    private String variationId;
    @SerializedName("color_id")
    private String colorId;
    @SerializedName("colorName")
    private String colorName;
    @SerializedName("colorCode")
    private String colorCode;
    @SerializedName("sizes")
    List<VariationSizePOJO> variationSizePOJOS;

    public String getVariationId() {
        return variationId;
    }

    public void setVariationId(String variationId) {
        this.variationId = variationId;
    }

    public String getColorId() {
        return colorId;
    }

    public void setColorId(String colorId) {
        this.colorId = colorId;
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

    public List<VariationSizePOJO> getVariationSizePOJOS() {
        return variationSizePOJOS;
    }

    public void setVariationSizePOJOS(List<VariationSizePOJO> variationSizePOJOS) {
        this.variationSizePOJOS = variationSizePOJOS;
    }
}
