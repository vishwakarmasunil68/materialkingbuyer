package com.appentus.materialking.views.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.appentus.materialking.R;
import com.appentus.materialking.Util.ToastClass;
import com.appentus.materialking.pojo.ResponsePOJO;
import com.appentus.materialking.pojo.SellerPOJO;
import com.appentus.materialking.webservice.ResponseCallBack;
import com.appentus.materialking.webservice.WebServiceBaseResponse;
import com.appentus.materialking.webservice.WebServiceUrl;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SellerDetailActivity extends AppCompatActivity {

    @BindView(R.id.tv_seller_name)
    TextView tv_seller_name;
    @BindView(R.id.tv_email)
    TextView tv_email;
    @BindView(R.id.tv_mobile)
    TextView tv_mobile;
    @BindView(R.id.tv_area)
    TextView tv_area;
    @BindView(R.id.tv_city)
    TextView tv_city;
    @BindView(R.id.tv_state)
    TextView tv_state;
    @BindView(R.id.tv_pincode)
    TextView tv_pincode;
    @BindView(R.id.tv_landmark)
    TextView tv_landmark;
    @BindView(R.id.tv_serve_city)
    TextView tv_serve_city;
    @BindView(R.id.tv_business_type)
    TextView tv_business_type;

    String seller_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_detail);
        ButterKnife.bind(this);

        seller_id = getIntent().getStringExtra("seller_id");

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("seller_id", seller_id));
        new WebServiceBaseResponse<SellerPOJO>(nameValuePairs, this, new ResponseCallBack<SellerPOJO>() {
            @Override
            public void onGetMsg(ResponsePOJO<SellerPOJO> responsePOJO) {
                try {
                    if (responsePOJO.isSuccess()) {
                        sellerDetail(responsePOJO.getResult());
                    } else {
                        ToastClass.showShortToast(getApplicationContext(), responsePOJO.getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, SellerPOJO.class, "GET_SELLER_DETAILS", true).execute(WebServiceUrl.GET_SELLER_DETAIL);
    }

    public void sellerDetail(SellerPOJO sellerPOJO) {
        if (sellerPOJO != null) {
            tv_seller_name.setText("Contact Name :- "+sellerPOJO.getContactName());
            tv_email.setText("Email :- "+sellerPOJO.getEmail());
            tv_mobile.setText("Mobile :- "+sellerPOJO.getMobile());
            tv_area.setText("Area :- "+sellerPOJO.getArea());
            tv_city.setText("City :- "+sellerPOJO.getCity());
            tv_state.setText("State :- "+sellerPOJO.getState());
            tv_pincode.setText("Pincode :- "+sellerPOJO.getPincode());
            tv_landmark.setText("LandMark :- "+sellerPOJO.getLandmark());
            tv_serve_city.setText("Cities To Serve :- "+sellerPOJO.getCitiesToServe());
            tv_business_type.setText("Bussiness Type :- "+sellerPOJO.getBusinessType());
        }
    }
}
