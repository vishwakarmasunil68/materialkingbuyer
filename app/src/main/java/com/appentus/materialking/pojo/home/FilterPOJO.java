package com.appentus.materialking.pojo.home;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FilterPOJO {
    @SerializedName("Brand")
    List<BrandPOJO> brandPOJOS;
    @SerializedName("Type")
    List<TypePOJO> typePOJOS;
    @SerializedName("Color")
    List<ColorPOJO> colorPOJOS;
    @SerializedName("Size")
    List<SizePOJO> sizePOJOS;

    public List<BrandPOJO> getBrandPOJOS() {
        return brandPOJOS;
    }

    public void setBrandPOJOS(List<BrandPOJO> brandPOJOS) {
        this.brandPOJOS = brandPOJOS;
    }

    public List<TypePOJO> getTypePOJOS() {
        return typePOJOS;
    }

    public void setTypePOJOS(List<TypePOJO> typePOJOS) {
        this.typePOJOS = typePOJOS;
    }

    public List<ColorPOJO> getColorPOJOS() {
        return colorPOJOS;
    }

    public void setColorPOJOS(List<ColorPOJO> colorPOJOS) {
        this.colorPOJOS = colorPOJOS;
    }

    public List<SizePOJO> getSizePOJOS() {
        return sizePOJOS;
    }

    public void setSizePOJOS(List<SizePOJO> sizePOJOS) {
        this.sizePOJOS = sizePOJOS;
    }
}
