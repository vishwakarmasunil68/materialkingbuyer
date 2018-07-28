package com.appentus.materialking.views.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.appentus.materialking.R;
import com.appentus.materialking.Util.ToastClass;
import com.appentus.materialking.adapter.OfferAdapter;
import com.appentus.materialking.pojo.BidInfoPOJO;
import com.appentus.materialking.pojo.ResponseListPOJO;
import com.appentus.materialking.utility.MyApplication;
import com.appentus.materialking.utility.PrefsData;
import com.appentus.materialking.webservice.WebServiceUrl;
import com.appentus.materialking.webservice.ResponseListCallback;
import com.appentus.materialking.webservice.WebServiceBase;
import com.appentus.materialking.webservice.WebServiceBaseResponseList;
import com.appentus.materialking.webservice.WebServicesCallBack;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OfferActivity extends AppCompatActivity {

    @BindView(R.id.rv_offers)
    RecyclerView rv_offers;
    @BindView(R.id.btn_place_order)
    Button btn_place_order;

    String order_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);
        ButterKnife.bind(this);
        attachAdapter();
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            order_id=bundle.getString("order_id");
            getOfferData();
        }

        btn_place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeOrder();
            }
        });

    }

    public void placeOrder(){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        int count=0;
        for(BidInfoPOJO bidInfoPOJO:bidInfoPOJOS){
            nameValuePairs.add(new BasicNameValuePair("product["+count+"][user_id]", MyApplication.readStringPref(PrefsData.PREF_USERID)));
            nameValuePairs.add(new BasicNameValuePair("product["+count+"][order_id]", order_id));
            nameValuePairs.add(new BasicNameValuePair("product["+count+"][seller_id]", bidInfoPOJO.getSeller_id()));
            nameValuePairs.add(new BasicNameValuePair("product["+count+"][product_id]", bidInfoPOJO.getProductId()));
            nameValuePairs.add(new BasicNameValuePair("product["+count+"][bid_id]", bidInfoPOJO.getBidId()));
            nameValuePairs.add(new BasicNameValuePair("product["+count+"][bid_product_id]", bidInfoPOJO.getBidProductId()));
            if(bidInfoPOJO.getRecommendedBidInfoPOJO()!=null) {
                nameValuePairs.add(new BasicNameValuePair("product["+count+"][bid_product_recommended_id]", bidInfoPOJO.getRecommendedBidInfoPOJO().getBidProductRecommendedId()));
            }else{
                nameValuePairs.add(new BasicNameValuePair("product["+count+"][bid_product_recommended_id]", "0"));
            }
            count++;
        }

        if(nameValuePairs.size()>0){
            nameValuePairs.add(new BasicNameValuePair("order_id",order_id));
            new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
                @Override
                public void onGetMsg(String apicall, String response) {
                    try{
                        JSONObject jsonObject=new JSONObject(response);
//                        if(jsonObject.optInt("status")==1){
//
//                        }else{
//
//                        }
                        ToastClass.showShortToast(getApplicationContext(),"Offer sent to seller");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            },"POST_FINAL_OFFER",true).execute(WebServiceUrl.PLACE_FINAL_OFFER);
        }

    }


    List<BidInfoPOJO> bidInfoPOJOS= new ArrayList<>();
    OfferAdapter offerAdapter;

    public void attachAdapter() {

        offerAdapter = new OfferAdapter(this, bidInfoPOJOS);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_offers.setHasFixedSize(true);
        rv_offers.setAdapter(offerAdapter);
        rv_offers.setLayoutManager(linearLayoutManager);
        rv_offers.setItemAnimator(new DefaultItemAnimator());
    }

    public void getOfferData(){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("order_id",order_id));
        new WebServiceBaseResponseList<BidInfoPOJO>(nameValuePairs, this, new ResponseListCallback<BidInfoPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<BidInfoPOJO> responseListPOJO) {
                try{
                    bidInfoPOJOS.clear();
                    bidInfoPOJOS.addAll(responseListPOJO.getResultList());
                    offerAdapter.notifyDataSetChanged();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },BidInfoPOJO.class,"GET_OFFER_DETAILS",true).execute(WebServiceUrl.GET_OFFER_DETAILS);
    }

}
