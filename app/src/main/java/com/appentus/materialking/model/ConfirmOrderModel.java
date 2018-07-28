package com.appentus.materialking.model;

/**
 * Created by Hp on 2/8/2018.
 */

public class ConfirmOrderModel {

    String productNameConfirm;

    public ConfirmOrderModel(String productNameConfirm) {
        this.productNameConfirm = productNameConfirm;
    }

    public String getProductNameConfirm() {
        return productNameConfirm;
    }

    public void setProductNameConfirm(String productNameConfirm) {
        this.productNameConfirm = productNameConfirm;
    }
}
