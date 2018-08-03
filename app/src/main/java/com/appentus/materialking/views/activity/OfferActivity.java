package com.appentus.materialking.views.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appentus.materialking.R;
import com.appentus.materialking.adapter.NotificationBidInfoAdapter;
import com.appentus.materialking.pojo.FinalOrderPOJO;
import com.appentus.materialking.pojo.ResponseListPOJO;
import com.appentus.materialking.utility.MyApplication;
import com.appentus.materialking.utility.PrefsData;
import com.appentus.materialking.webservice.ResponseListCallback;
import com.appentus.materialking.webservice.WebServiceBaseResponseList;
import com.appentus.materialking.webservice.WebServiceUrl;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OfferActivity extends AppCompatActivity {

    @BindView(R.id.rv_offers)
    RecyclerView rv_offers;
    @BindView(R.id.btn_place_order)
    Button btn_place_order;
    @BindView(R.id.tv_total_price)
    TextView tv_total_price;
    @BindView(R.id.tv_shipping_charges)
    TextView tv_shipping_charges;
    @BindView(R.id.ll_price)
    LinearLayout ll_price;

    String order_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);
        ButterKnife.bind(this);
        attachAdapter();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            order_id = bundle.getString("order_id");
            getOfferData();
        }

        btn_place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeOrder();
            }
        });

    }

    public void placeOrder() {
//        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
//        int count=0;
//        for(BidInfoPOJO bidInfoPOJO:bidInfoPOJOS){
//            nameValuePairs.add(new BasicNameValuePair("product["+count+"][user_id]", MyApplication.readStringPref(PrefsData.PREF_USERID)));
//            nameValuePairs.add(new BasicNameValuePair("product["+count+"][order_id]", order_id));
//            nameValuePairs.add(new BasicNameValuePair("product["+count+"][seller_id]", bidInfoPOJO.getSeller_id()));
//            nameValuePairs.add(new BasicNameValuePair("product["+count+"][product_id]", bidInfoPOJO.getProductId()));
//            nameValuePairs.add(new BasicNameValuePair("product["+count+"][bid_id]", bidInfoPOJO.getBidId()));
//            nameValuePairs.add(new BasicNameValuePair("product["+count+"][bid_product_id]", bidInfoPOJO.getBidProductId()));
//            if(bidInfoPOJO.getRecommendedBidInfoPOJO()!=null) {
//                nameValuePairs.add(new BasicNameValuePair("product["+count+"][bid_product_recommended_id]", bidInfoPOJO.getRecommendedBidInfoPOJO().getBidProductRecommendedId()));
//            }else{
//                nameValuePairs.add(new BasicNameValuePair("product["+count+"][bid_product_recommended_id]", "0"));
//            }
//            count++;
//        }
//
//        if(nameValuePairs.size()>0){
//            nameValuePairs.add(new BasicNameValuePair("order_id",order_id));
//            new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
//                @Override
//                public void onGetMsg(String apicall, String response) {
//                    try{
//                        JSONObject jsonObject=new JSONObject(response);
////                        if(jsonObject.optInt("status")==1){
////
////                        }else{
////
////                        }
//                        ToastClass.showShortToast(getApplicationContext(),"Offer sent to seller");
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                }
//            },"POST_FINAL_OFFER",true).execute(WebServiceUrl.PLACE_FINAL_OFFER);
//        }

    }


    List<FinalOrderPOJO> bidInfoPOJOS = new ArrayList<>();
    NotificationBidInfoAdapter notificationBidInfoAdapter;

    public void attachAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_offers.setHasFixedSize(true);
        rv_offers.setLayoutManager(linearLayoutManager);
        notificationBidInfoAdapter = new NotificationBidInfoAdapter(this, null, bidInfoPOJOS);
        rv_offers.setAdapter(notificationBidInfoAdapter);
        rv_offers.setNestedScrollingEnabled(false);
        rv_offers.setItemAnimator(new DefaultItemAnimator());
    }

//    public void getOfferData(){
//        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
//        nameValuePairs.add(new BasicNameValuePair("order_id",order_id));
//        new WebServiceBaseResponseList<BidInfoPOJO>(nameValuePairs, this, new ResponseListCallback<BidInfoPOJO>() {
//            @Override
//            public void onGetMsg(ResponseListPOJO<BidInfoPOJO> responseListPOJO) {
//                try{
//                    bidInfoPOJOS.clear();
//                    bidInfoPOJOS.addAll(responseListPOJO.getResultList());
//                    offerAdapter.notifyDataSetChanged();
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        },BidInfoPOJO.class,"GET_OFFER_DETAILS",true).execute(WebServiceUrl.GET_OFFER_DETAILS);
//    }


    double shipping_price = 0.0;
    double total_price = 0.0;

    public void getOfferData() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("order_id", order_id));
        nameValuePairs.add(new BasicNameValuePair("user_id", MyApplication.readStringPref(PrefsData.PREF_USERID)));
        new WebServiceBaseResponseList<FinalOrderPOJO>(nameValuePairs, this, new ResponseListCallback<FinalOrderPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<FinalOrderPOJO> responseListPOJO) {
                try {
                    bidInfoPOJOS.clear();
                    bidInfoPOJOS.addAll(responseListPOJO.getResultList());

                    if (responseListPOJO.getResultList().size() > 0) {
                        for (FinalOrderPOJO finalOrderPOJO : responseListPOJO.getResultList()) {
                            if (finalOrderPOJO.getBidProductInfo() != null) {
                                if (finalOrderPOJO.getBidProductRecommendedId().equals("0")) {
                                    total_price += getValue(finalOrderPOJO.getBidProductInfo().getPriceHave());
                                    shipping_price += getValue(finalOrderPOJO.getBidProductInfo().getShippingCharge());
                                } else {
                                    total_price += getValue(finalOrderPOJO.getBidProductInfo().getOfferRecommendedProduct().getPriceHave());
                                    shipping_price += finalOrderPOJO.getBidProductInfo().getOfferRecommendedProduct().getShipping_charge();
                                }
                            }
                        }
                        ll_price.setVisibility(View.VISIBLE);
                        tv_shipping_charges.setText("Total Shipping Charges:- "+shipping_price +" INR");
                        tv_total_price.setText("Total Price:- "+total_price +" INR");
                    }else{
                        ll_price.setVisibility(View.GONE);
                    }
                    notificationBidInfoAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, FinalOrderPOJO.class, "GET_OFFER_DETAILS", true).execute(WebServiceUrl.GET_ALL_SELECTED_OFFERS);
    }

    public double getValue(String val) {
        try {
            return Double.parseDouble(val);
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }

}
