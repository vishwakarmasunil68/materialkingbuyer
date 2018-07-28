package com.appentus.materialking.model;

/**
 * Created by Hp on 2/6/2018.
 */

public class MyBidsModel {

    String orderId;

    public MyBidsModel(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
