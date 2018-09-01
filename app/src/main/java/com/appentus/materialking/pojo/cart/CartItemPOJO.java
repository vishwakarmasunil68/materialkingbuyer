package com.appentus.materialking.pojo.cart;

import com.appentus.materialking.pojo.home.BrandPOJO;
import com.appentus.materialking.pojo.home.SizePOJO;
import com.appentus.materialking.pojo.home.TypePOJO;
import com.google.gson.annotations.SerializedName;

public class CartItemPOJO {
    @SerializedName("cart_id")
    private String cartId;
    @SerializedName("user_id")
    private String userId;
    @SerializedName("product_id")
    private String productId;
    @SerializedName("variation_id")
    private String variationId;
    @SerializedName("color_id")
    private String colorId;
    @SerializedName("product_variation_id")
    private String productVariationId;
    @SerializedName("size_id")
    private String sizeId;
    @SerializedName("qty_required")
    private String qtyRequired;
    @SerializedName("added_on")
    private String addedOn;
    @SerializedName("updated_on")
    private String updatedOn;
    @SerializedName("product_name")
    private String productName;
    @SerializedName("product_image")
    private String productImage;
    @SerializedName("colorName")
    private String colorName;
    @SerializedName("colorCode")
    private String colorCode;
    @SerializedName("sizeName")
    private String sizeName;
    @SerializedName("product_size_image")
    private String productSizeImage;
    @SerializedName("size_detail")
    private SizePOJO size_detail;
    @SerializedName("brand_detail")
    private BrandPOJO brand_detail;
    @SerializedName("type_detail")
    private TypePOJO type_detail;

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

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

    public String getProductVariationId() {
        return productVariationId;
    }

    public void setProductVariationId(String productVariationId) {
        this.productVariationId = productVariationId;
    }

    public String getSizeId() {
        return sizeId;
    }

    public void setSizeId(String sizeId) {
        this.sizeId = sizeId;
    }

    public String getQtyRequired() {
        return qtyRequired;
    }

    public void setQtyRequired(String qtyRequired) {
        this.qtyRequired = qtyRequired;
    }

    public String getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(String addedOn) {
        this.addedOn = addedOn;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
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

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    public String getProductSizeImage() {
        return productSizeImage;
    }

    public void setProductSizeImage(String productSizeImage) {
        this.productSizeImage = productSizeImage;
    }

    public SizePOJO getSize_detail() {
        return size_detail;
    }

    public void setSize_detail(SizePOJO size_detail) {
        this.size_detail = size_detail;
    }

    public BrandPOJO getBrand_detail() {
        return brand_detail;
    }

    public void setBrand_detail(BrandPOJO brand_detail) {
        this.brand_detail = brand_detail;
    }

    public TypePOJO getType_detail() {
        return type_detail;
    }

    public void setType_detail(TypePOJO type_detail) {
        this.type_detail = type_detail;
    }
}
