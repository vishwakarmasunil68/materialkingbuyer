package com.appentus.materialking.model;

/**
 * Created by Hp on 2/6/2018.
 */

public class CompleteOdersModel {

    String totalAmount;


    public CompleteOdersModel(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}
