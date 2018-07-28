package com.appentus.materialking.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SellerTypePOJO implements Serializable{
    @SerializedName("seller_type_id")
    @Expose
    private String sellerTypeId;
    @SerializedName("seller_type_name")
    @Expose
    private String sellerTypeName;
    @SerializedName("seller_type_status")
    @Expose
    private String sellerTypeStatus;


    boolean is_checked=false;

    public String getSellerTypeId() {
        return sellerTypeId;
    }

    public void setSellerTypeId(String sellerTypeId) {
        this.sellerTypeId = sellerTypeId;
    }

    public String getSellerTypeName() {
        return sellerTypeName;
    }

    public void setSellerTypeName(String sellerTypeName) {
        this.sellerTypeName = sellerTypeName;
    }

    public String getSellerTypeStatus() {
        return sellerTypeStatus;
    }

    public void setSellerTypeStatus(String sellerTypeStatus) {
        this.sellerTypeStatus = sellerTypeStatus;
    }

    public boolean isIs_checked() {
        return is_checked;
    }

    public void setIs_checked(boolean is_checked) {
        this.is_checked = is_checked;
    }
}
