package com.appentus.materialking.views.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.appentus.materialking.R;
import com.appentus.materialking.adapter.ViewDetailRecommendedAdapter;
import com.appentus.materialking.pojo.BidInfoPOJO;
import com.appentus.materialking.pojo.ResponseListPOJO;
import com.appentus.materialking.webservice.WebServiceUrl;
import com.appentus.materialking.webservice.ResponseListCallback;
import com.appentus.materialking.webservice.WebServiceBaseResponseList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderItemBidProductActivity extends AppCompatActivity {

    @BindView(R.id.rv_bids)
    RecyclerView rv_bids;

    String order_id="";
    String product_id="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_item_bid_product);
        ButterKnife.bind(this);

        order_id=getIntent().getStringExtra("order_id");
        product_id=getIntent().getStringExtra("product_id");

        rv_bids.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ViewDetailRecommendedAdapter(this, recommendedBidInfoPOJOS);
        rv_bids.setAdapter(mAdapter);

        callAPI();

    }
    ViewDetailRecommendedAdapter mAdapter;
    List<BidInfoPOJO> recommendedBidInfoPOJOS=new ArrayList<>();
    public void callAPI(){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("order_id",order_id));
        nameValuePairs.add(new BasicNameValuePair("product_id",product_id));

        new WebServiceBaseResponseList<BidInfoPOJO>(nameValuePairs, this, new ResponseListCallback<BidInfoPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<BidInfoPOJO> responseListPOJO) {
                try{
                    if(responseListPOJO.isSuccess()){
                        recommendedBidInfoPOJOS.clear();
                        recommendedBidInfoPOJOS.addAll(responseListPOJO.getResultList());
                        mAdapter.notifyDataSetChanged();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },BidInfoPOJO.class,"GET_BID_POJO",true).execute(WebServiceUrl.GET_PRODUCT_BID);

    }
}
