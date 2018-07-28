package com.appentus.materialking.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.appentus.materialking.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShippingConfirmationActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_shipping_first_name)
    AppCompatEditText etShippingFirstName;
    @BindView(R.id.et_shipping_second_name)
    AppCompatEditText etShippingSecondName;
    @BindView(R.id.et_shipping_phone_number)
    AppCompatEditText etShippingPhoneNumber;
    @BindView(R.id.et_shipping_address)
    AppCompatEditText etShippingAddress;
    @BindView(R.id.et_shipping_pincode)
    AppCompatEditText etShippingPincode;
    @BindView(R.id.et_shipping_city)
    AppCompatEditText etShippingCity;
    @BindView(R.id.btn_shipping_address_confirm)
    AppCompatButton btnShippingAddressConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_confirmation);
        ButterKnife.bind(this);


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Shipping Address");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);



        btnShippingAddressConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShippingConfirmationActivity.this,OrderConfirmationActivity.class));
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
