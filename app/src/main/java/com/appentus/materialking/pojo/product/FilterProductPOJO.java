package com.appentus.materialking.pojo.product;

import com.appentus.materialking.pojo.home.BrandPOJO;
import com.appentus.materialking.pojo.home.SizePOJO;
import com.appentus.materialking.pojo.home.TypePOJO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FilterProductPOJO {

    @Expose
    @SerializedName("variation_image")
    private String variationImage;
    @Expose
    @SerializedName("qty")
    private String qty;
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
    @SerializedName("status")
    private String status;
    @Expose
    @SerializedName("description")
    private String description;
    @Expose
    @SerializedName("image")
    private String image;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("productNumber")
    private String productnumber;
    @Expose
    @SerializedName("type_id")
    private String typeId;
    @Expose
    @SerializedName("brand_id")
    private String brandId;
    @Expose
    @SerializedName("subcategory_id")
    private String subcategoryId;
    @Expose
    @SerializedName("category_id")
    private String categoryId;
    @Expose
    @SerializedName("size_detail")
    private SizePOJO size_detail;
    @Expose
    @SerializedName("brand_detail")
    private BrandPOJO brand_detail;
    @Expose
    @SerializedName("type_detail")
    private TypePOJO type_detail;
    @Expose
    @SerializedName("product_id")
    private String productId;

    public String getVariationImage() {
        return variationImage;
    }

    public void setVariationImage(String variationImage) {
        this.variationImage = variationImage;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductnumber() {
        return productnumber;
    }

    public void setProductnumber(String productnumber) {
        this.productnumber = productnumber;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(String subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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
