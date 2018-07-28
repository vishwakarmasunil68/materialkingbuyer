package com.appentus.materialking.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Hp on 2/6/2018.
 */

public class PartialOrderModel implements Serializable{

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
    private Integer totalCount;
    @SerializedName("final_offer")
    @Expose
    private String finalOffer;
    @SerializedName("shipping_price")
    @Expose
    private String shippingPrice;
    @SerializedName("min_shipping_days")
    @Expose
    private String min_shipping_days;
    @SerializedName("max_shipping_days")
    @Expose
    private String max_shipping_days;

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

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public String getFinalOffer() {
        return finalOffer;
    }

    public void setFinalOffer(String finalOffer) {
        this.finalOffer = finalOffer;
    }

    public String getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(String shippingPrice) {
        this.shippingPrice = shippingPrice;
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
