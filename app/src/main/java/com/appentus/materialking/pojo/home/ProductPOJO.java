package com.appentus.materialking.pojo.home;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 23-04-2018.
 */

public class ProductPOJO {
    @SerializedName("product_id")
    private String productId;
    @SerializedName("category_id")
    private String categoryId;
    @SerializedName("subcategory_id")
    private String subcategoryId;
    @SerializedName("brand_id")
    private String brandId;
    @SerializedName("type_id")
    private String typeId;
    @SerializedName("productNumber")
    private String productNumber;
    @SerializedName("name")
    private String name;
    @SerializedName("image")
    private String image;
    @SerializedName("description")
    private String description;
    @SerializedName("brandName")
    private String brandName;
    @SerializedName("typeName")
    private String typeName;
    @SerializedName("variations")
    List<VariationPOJO> variationPOJOS;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(String subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<VariationPOJO> getVariationPOJOS() {
        return variationPOJOS;
    }

    public void setVariationPOJOS(List<VariationPOJO> variationPOJOS) {
        this.variationPOJOS = variationPOJOS;
    }
}
