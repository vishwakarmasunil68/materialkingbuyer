package com.appentus.materialking.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderProductPOJO {

    @Expose
    @SerializedName("size_name")
    private String sizeName;
    @Expose
    @SerializedName("color_name")
    private String colorName;
    @Expose
    @SerializedName("brand_name")
    private String brandName;
    @Expose
    @SerializedName("product_description")
    private String productDescription;
    @Expose
    @SerializedName("product_variation_image")
    private String productVariationImage;
    @Expose
    @SerializedName("product_image")
    private String productImage;
    @Expose
    @SerializedName("product_name")
    private String productName;
    @Expose
    @SerializedName("product_number")
    private String productNumber;
    @Expose
    @SerializedName("qty_required")
    private String qtyRequired;
    @Expose
    @SerializedName("size_id")
    private String sizeId;
    @Expose
    @SerializedName("product_variation_id")
    private String productVariationId;
    @Expose
    @SerializedName("color_id")
    private String colorId;
    @Expose
    @SerializedName("variation_id")
    private String variationId;
    @Expose
    @SerializedName("product_id")
    private String productId;
    @Expose
    @SerializedName("order_id")
    private String orderId;
    @Expose
    @SerializedName("order_cart_product_id")
    private String orderCartProductId;
    @Expose
    @SerializedName("order_on")
    private String order_on;
    @Expose
    @SerializedName("bid_placed")
    private boolean bid_placed;
    @Expose
    @SerializedName("final_order")
    private boolean final_order;

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductVariationImage() {
        return productVariationImage;
    }

    public void setProductVariationImage(String productVariationImage) {
        this.productVariationImage = productVariationImage;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public String getQtyRequired() {
        return qtyRequired;
    }

    public void setQtyRequired(String qtyRequired) {
        this.qtyRequired = qtyRequired;
    }

    public String getSizeId() {
        return sizeId;
    }

    public void setSizeId(String sizeId) {
        this.sizeId = sizeId;
    }

    public String getProductVariationId() {
        return productVariationId;
    }

    public void setProductVariationId(String productVariationId) {
        this.productVariationId = productVariationId;
    }

    public String getColorId() {
        return colorId;
    }

    public void setColorId(String colorId) {
        this.colorId = colorId;
    }

    public String getVariationId() {
        return variationId;
    }

    public void setVariationId(String variationId) {
        this.variationId = variationId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderCartProductId() {
        return orderCartProductId;
    }

    public void setOrderCartProductId(String orderCartProductId) {
        this.orderCartProductId = orderCartProductId;
    }

    public String getOrder_on() {
        return order_on;
    }

    public void setOrder_on(String order_on) {
        this.order_on = order_on;
    }

    public boolean isBid_placed() {
        return bid_placed;
    }

    public void setBid_placed(boolean bid_placed) {
        this.bid_placed = bid_placed;
    }

    public boolean isFinal_order() {
        return final_order;
    }

    public void setFinal_order(boolean final_order) {
        this.final_order = final_order;
    }
}
