package com.appentus.materialking.pojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MyOrdersPOJO implements Serializable{
    @SerializedName("order_id")
    private String orderId;
    @SerializedName("order_on")
    private String orderOn;
    @SerializedName("order_status")
    private String orderStatus;
    @SerializedName("total_bids")
    private String totalBids;
    @SerializedName("completed_order_bids")
    private String completedOrderBids;
    @SerializedName("recommended_bids")
    private String recommendedBids;
    @SerializedName("partial_order_bids")
    private String partialOrderBids;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderOn() {
        return orderOn;
    }

    public void setOrderOn(String orderOn) {
        this.orderOn = orderOn;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getTotalBids() {
        return totalBids;
    }

    public void setTotalBids(String totalBids) {
        this.totalBids = totalBids;
    }

    public String getCompletedOrderBids() {
        return completedOrderBids;
    }

    public void setCompletedOrderBids(String completedOrderBids) {
        this.completedOrderBids = completedOrderBids;
    }

    public String getRecommendedBids() {
        return recommendedBids;
    }

    public void setRecommendedBids(String recommendedBids) {
        this.recommendedBids = recommendedBids;
    }

    public String getPartialOrderBids() {
        return partialOrderBids;
    }

    public void setPartialOrderBids(String partialOrderBids) {
        this.partialOrderBids = partialOrderBids;
    }
}
