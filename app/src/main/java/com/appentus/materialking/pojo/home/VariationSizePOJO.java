package com.appentus.materialking.pojo.home;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 23-04-2018.
 */

public class VariationSizePOJO {
    @SerializedName("product_variation_id")
    private String productVariationId;
    @SerializedName("variation_id")
    private String variationId;
    @SerializedName("size_id")
    private String sizeId;
    @SerializedName("qty")
    private String qty;
    @SerializedName("image")
    private String image;
    @SerializedName("sizeName")
    private String sizeName;

    public String getProductVariationId() {
        return productVariationId;
    }

    public void setProductVariationId(String productVariationId) {
        this.productVariationId = productVariationId;
    }

    public String getVariationId() {
        return variationId;
    }

    public void setVariationId(String variationId) {
        this.variationId = variationId;
    }

    public String getSizeId() {
        return sizeId;
    }

    public void setSizeId(String sizeId) {
        this.sizeId = sizeId;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }
}
