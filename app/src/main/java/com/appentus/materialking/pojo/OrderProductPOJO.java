package com.appentus.materialking.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderProductPOJO {

    @SerializedName("order_cart_product_id")
    @Expose
    private String orderCartProductId;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("variation_id")
    @Expose
    private String variationId;
    @SerializedName("color_id")
    @Expose
    private String colorId;
    @SerializedName("product_variation_id")
    @Expose
    private String productVariationId;
    @SerializedName("size_id")
    @Expose
    private String sizeId;
    @SerializedName("qty_required")
    @Expose
    private String qtyRequired;
    @SerializedName("product_number")
    @Expose
    private String productNumber;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("product_image")
    @Expose
    private String productImage;
    @SerializedName("product_description")
    @Expose
    private String productDescription;
    @SerializedName("brand_name")
    @Expose
    private String brandName;
    @SerializedName("type_name")
    @Expose
    private Object typeName;
    @SerializedName("color_name")
    @Expose
    private String colorName;
    @SerializedName("size_name")
    @Expose
    private String sizeName;

    public String getOrderCartProductId() {
        return orderCartProductId;
    }

    public void setOrderCartProductId(String orderCartProductId) {
        this.orderCartProductId = orderCartProductId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
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

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Object getTypeName() {
        return typeName;
    }

    public void setTypeName(Object typeName) {
        this.typeName = typeName;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }
}
