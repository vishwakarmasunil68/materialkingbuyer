package com.appentus.materialking.pojo.home;

import com.google.gson.annotations.SerializedName;

public class ProductVariantSizePOJO {
    @SerializedName("product_id")
    private String productId;
    @SerializedName("category_id")
    private String categoryId;
    @SerializedName("subcategory_id")
    private String subcategoryId;
    @SerializedName("category_name")
    private String categoryName;
    @SerializedName("sub_category_name")
    private String subCategoryName;
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
    @SerializedName("colorName")
    private String colorName;
    @SerializedName("colorCode")
    private String colorCode;
    @SerializedName("sizeName")
    private String sizeName;
    @SerializedName("size_image")
    private String sizeImage;
    @SerializedName("color_id")
    private String colorId;
    @SerializedName("size_id")
    private String sizeId;
    @SerializedName("variation_id")
    private String variationId;
    @SerializedName("product_variation_id")
    private String productVariationId;
    @SerializedName("qty")
    private String qty;
    @SerializedName("variation_image")
    private String variation_image;
    @SerializedName("size_detail")
    private SizePOJO size_detail;

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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
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

    public String getSizeImage() {
        return sizeImage;
    }

    public void setSizeImage(String sizeImage) {
        this.sizeImage = sizeImage;
    }

    public String getColorId() {
        return colorId;
    }

    public void setColorId(String colorId) {
        this.colorId = colorId;
    }

    public String getSizeId() {
        return sizeId;
    }

    public void setSizeId(String sizeId) {
        this.sizeId = sizeId;
    }

    public String getVariationId() {
        return variationId;
    }

    public void setVariationId(String variationId) {
        this.variationId = variationId;
    }

    public String getProductVariationId() {
        return productVariationId;
    }

    public void setProductVariationId(String productVariationId) {
        this.productVariationId = productVariationId;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getVariation_image() {
        return variation_image;
    }

    public void setVariation_image(String variation_image) {
        this.variation_image = variation_image;
    }

    public SizePOJO getSize_detail() {
        return size_detail;
    }

    public void setSize_detail(SizePOJO size_detail) {
        this.size_detail = size_detail;
    }
}
