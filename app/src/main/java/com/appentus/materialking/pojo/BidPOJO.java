package com.appentus.materialking.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BidPOJO implements Serializable {
    @SerializedName("bid_id")
    @Expose
    private String bidId;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("seller_id")
    @Expose
    private String sellerId;
    @SerializedName("seller_status")
    @Expose
    private String sellerStatus;
    @SerializedName("buyer_status")
    @Expose
    private String buyerStatus;
    @SerializedName("delivered_on")
    @Expose
    private String deliveredOn;
    @SerializedName("total_count")
    @Expose
    private String total_count;
    @SerializedName("final_offer")
    @Expose
    private String final_offer;
    @SerializedName("shipping_price")
    @Expose
    private String shipping_price;
    @SerializedName("min_shipping_days")
    @Expose
    private String min_shipping_days;
    @SerializedName("max_shipping_days")
    @Expose
    private String max_shipping_days;
//    @SerializedName("bid_info")
//    @Expose
//    private BidInfoPOJO bidInfo;

    public String getBidId() {
        return bidId;
    }

    public void setBidId(String bidId) {
        this.bidId = bidId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerStatus() {
        return sellerStatus;
    }

    public void setSellerStatus(String sellerStatus) {
        this.sellerStatus = sellerStatus;
    }

    public String getBuyerStatus() {
        return buyerStatus;
    }

    public void setBuyerStatus(String buyerStatus) {
        this.buyerStatus = buyerStatus;
    }

    public String getDeliveredOn() {
        return deliveredOn;
    }

    public void setDeliveredOn(String deliveredOn) {
        this.deliveredOn = deliveredOn;
    }

//    public BidInfoPOJO getBidInfo() {
//        return bidInfo;
//    }
//
//    public void setBidInfo(BidInfoPOJO bidInfo) {
//        this.bidInfo = bidInfo;
//    }

    public String getTotal_count() {
        return total_count;
    }

    public void setTotal_count(String total_count) {
        this.total_count = total_count;
    }

    public String getFinal_offer() {
        return final_offer;
    }

    public void setFinal_offer(String final_offer) {
        this.final_offer = final_offer;
    }

    public String getShipping_price() {
        return shipping_price;
    }

    public void setShipping_price(String shipping_price) {
        this.shipping_price = shipping_price;
    }

    public String getMin_shipping_days() {
        return min_shipping_days;
    }

    public void setMin_shipping_days(String min_shipping_days) {
        this.min_shipping_days = min_shipping_days;
    }

    public String getMax_shipping_days() {
        return max_shipping_days;
    }

    public void setMax_shipping_days(String max_shipping_days) {
        this.max_shipping_days = max_shipping_days;
    }
}
