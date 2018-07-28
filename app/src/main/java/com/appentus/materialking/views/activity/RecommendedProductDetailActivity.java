package com.appentus.materialking.views.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.appentus.materialking.R;
import com.appentus.materialking.pojo.RecommendedBidInfoPOJO;
import com.appentus.materialking.pojo.ResponsePOJO;
import com.appentus.materialking.webservice.WebServiceUrl;
import com.appentus.materialking.webservice.ResponseCallBack;
import com.appentus.materialking.webservice.WebServiceBaseResponse;
import com.bumptech.glide.Glide;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecommendedProductDetailActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_product_name)
    TextView tv_product_name;
    @BindView(R.id.tv_product_description)
    TextView tv_product_description;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.tv_shipping_charges)
    TextView tv_shipping_charges;
    @BindView(R.id.tv_product_quantity)
    TextView tv_product_quantity;
    @BindView(R.id.tv_dod)
    TextView tv_dod;
    @BindView(R.id.iv_product_image)
    ImageView iv_product_image;

    String bid_product_recommended_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended_product_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            bid_product_recommended_id = bundle.getString("bid_product_recommended_id");
            if (bid_product_recommended_id != null) {
                getRecommendedProduct();
            }
        }

    }

    public void getRecommendedProduct() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("bid_product_recommended_id", bid_product_recommended_id));
        new WebServiceBaseResponse<RecommendedBidInfoPOJO>(nameValuePairs, this, new ResponseCallBack<RecommendedBidInfoPOJO>() {
            @Override
            public void onGetMsg(ResponsePOJO<RecommendedBidInfoPOJO> responsePOJO) {
                try {
                    if (responsePOJO.isSuccess()) {
                        tv_product_name.setText(responsePOJO.getResult().getName());
                        getSupportActionBar().setTitle(responsePOJO.getResult().getName());
//                        tv_product_description.setText(responsePOJO.getResult().getpro());
                        tv_price.setText(responsePOJO.getResult().getPriceHave());
//                        tv_shipping_charges.setText(responsePOJO.getResult().get());
                        tv_product_quantity.setText(responsePOJO.getResult().getQuantityHave());
                        tv_dod.setText(String.valueOf(responsePOJO.getResult().getDeliveredOn()));

                        Glide.with(getApplicationContext())
                                .load(WebServiceUrl.IMAGEBASEURL+responsePOJO.getResult().getImage())
                                .into(iv_product_image);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, RecommendedBidInfoPOJO.class, "GET_RECOMMEND_ITEM", true).execute(WebServiceUrl.GET_RECOMMENDED_BID_DETAIL_INFO);
    }
}
