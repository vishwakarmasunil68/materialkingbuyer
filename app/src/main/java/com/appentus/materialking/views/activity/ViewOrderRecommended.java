package com.appentus.materialking.views.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.appentus.materialking.R;
import com.appentus.materialking.Util.ToastClass;
import com.appentus.materialking.adapter.ViewDetailRecommendedAdapter;
import com.appentus.materialking.pojo.BidInfoPOJO;
import com.appentus.materialking.pojo.RecommendedBidInfoPOJO;
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

public class ViewOrderRecommended extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view_details_recommended)
    RecyclerView recyclerViewDetailsRecommended;
    @BindView(R.id.btn_select)
    Button btn_select;

    ViewDetailRecommendedAdapter mAdapter;
    List<BidInfoPOJO> recommendedBidInfoPOJOS=new ArrayList<>();

    String bid_id="";
    String order_id="";
    String seller_id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order_recommended);
        ButterKnife.bind(this);

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            bid_id=bundle.getString("bid_id");
            order_id=bundle.getString("order_id");
            seller_id=bundle.getString("seller_id");
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Recommended Item By Seller");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        recyclerViewDetailsRecommended.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ViewDetailRecommendedAdapter(this, recommendedBidInfoPOJOS);
        recyclerViewDetailsRecommended.setAdapter(mAdapter);


        getRecommendedBidDetails();
        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkSelectedOffers();
            }
        });
    }

    public void checkSelectedOffers(){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        int count=0;
        for(BidInfoPOJO bidInfoPOJO:recommendedBidInfoPOJOS){
            if(bidInfoPOJO.isIs_selected()){
                boolean is_recommend_selected=false;
                if(bidInfoPOJO.getRecommendedBidInfoPOJOS()!=null
                        &&bidInfoPOJO.getRecommendedBidInfoPOJOS().size()>0) {
                    for (RecommendedBidInfoPOJO recommendedBidInfoPOJO : bidInfoPOJO.getRecommendedBidInfoPOJOS()){
                        if(recommendedBidInfoPOJO.isSelected()) {
                            is_recommend_selected=true;
                            nameValuePairs.add(new BasicNameValuePair("products[" + count + "][order_id]", order_id));
                            nameValuePairs.add(new BasicNameValuePair("products[" + count + "][bid_id]", bidInfoPOJO.getBidId()));
                            nameValuePairs.add(new BasicNameValuePair("products[" + count + "][bid_product_id]", bidInfoPOJO.getBidProductId()));
                            nameValuePairs.add(new BasicNameValuePair("products[" + count + "][product_id]", bidInfoPOJO.getProductId()));
                            nameValuePairs.add(new BasicNameValuePair("products[" + count + "][bid_product_recommended_id]", recommendedBidInfoPOJO.getBidProductRecommendedId()));
                            nameValuePairs.add(new BasicNameValuePair("products[" + count + "][seller_id]", seller_id));
                            nameValuePairs.add(new BasicNameValuePair("products[" + count + "][user_id]", MyApplication.readStringPref(PrefsData.PREF_USERID)));
                            count++;
                        }
                    }
                }

                if(!is_recommend_selected){
                    if(bidInfoPOJO.getRecommendYesNo().equalsIgnoreCase("0")){
                        nameValuePairs.add(new BasicNameValuePair("products[" + count + "][order_id]", order_id));
                        nameValuePairs.add(new BasicNameValuePair("products[" + count + "][bid_id]", bidInfoPOJO.getBidId()));
                        nameValuePairs.add(new BasicNameValuePair("products[" + count + "][bid_product_id]", bidInfoPOJO.getBidProductId()));
                        nameValuePairs.add(new BasicNameValuePair("products[" + count + "][product_id]", bidInfoPOJO.getProductId()));
                        nameValuePairs.add(new BasicNameValuePair("products[" + count + "][bid_product_recommended_id]", "0"));
                        nameValuePairs.add(new BasicNameValuePair("products[" + count + "][seller_id]", seller_id));
                        nameValuePairs.add(new BasicNameValuePair("products[" + count + "][user_id]", MyApplication.readStringPref(PrefsData.PREF_USERID)));
                        count++;
                    }
                }
            }
        }

        if(count>0){
//            String nmv="";
//            for(NameValuePair nameValuePair:nameValuePairs){
//                nmv=nmv+nameValuePair.getName()+" : "+nameValuePair.getValue()+"\n";
//            }
//            Log.d(TagUtils.getTag(),"nmv:-"+nmv);
            new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
                @Override
                public void onGetMsg(String apicall, String response) {

                }
            },"SAVE_BID_PRODUCTS",true).execute(WebServiceUrl.INSERT_BID_PRODUCTS);
        }else{
            ToastClass.showShortToast(getApplicationContext(),"Please Select atleast 1 product");
        }
    }

    public void getRecommendedBidDetails(){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("bid_id",bid_id));
        new WebServiceBaseResponseList<BidInfoPOJO>(nameValuePairs, this, new ResponseListCallback<BidInfoPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<BidInfoPOJO> responseListPOJO) {
                try{
                    if(responseListPOJO.isSuccess()) {
                        recommendedBidInfoPOJOS.clear();
                        recommendedBidInfoPOJOS.addAll(responseListPOJO.getResultList());
                        mAdapter.notifyDataSetChanged();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },BidInfoPOJO.class,"GET_BID_INFO",true).execute(WebServiceUrl.GET_RECOMMENDED_BID_INFO);
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


    public void placeOrder(BidInfoPOJO bidInfoPOJO,String recommended_id) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("order_id", order_id));
        nameValuePairs.add(new BasicNameValuePair("bid_id", bidInfoPOJO.getBidId()));
        nameValuePairs.add(new BasicNameValuePair("product_id", bidInfoPOJO.getProductId()));
        nameValuePairs.add(new BasicNameValuePair("bid_product_id", bidInfoPOJO.getBidProductId()));
        nameValuePairs.add(new BasicNameValuePair("bid_product_recommended_id", recommended_id));
        nameValuePairs.add(new BasicNameValuePair("seller_id", seller_id));
        nameValuePairs.add(new BasicNameValuePair("user_id", MyApplication.readStringPref(PrefsData.PREF_USERID)));

        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    ToastClass.showShortToast(getApplicationContext(),jsonObject.optString("message"));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, "SAVE_BID_PRODUCTS", true).execute(WebServiceUrl.INSERT_FINAL_BID_PRODUCT);
    }

}
