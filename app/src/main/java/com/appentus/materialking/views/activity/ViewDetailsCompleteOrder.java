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
import com.appentus.materialking.adapter.ViewDetailCompleteAdapter;
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

public class ViewDetailsCompleteOrder extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view_details_complete)
    RecyclerView recyclerViewDetailsComplete;
    @BindView(R.id.btn_order_now)
    Button btn_order_now;
    ViewDetailCompleteAdapter mAdapter;
    List<BidInfoPOJO> bidInfoPOJOS = new ArrayList<>();

    String bid_id = "";
    String order_id = "";
    String seller_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details_complete_order);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Select Item By Seller");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            bid_id = bundle.getString("bid_id");
            order_id = bundle.getString("order_id");
            seller_id = bundle.getString("seller_id");
        }

        recyclerViewDetailsComplete.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ViewDetailCompleteAdapter(this, bidInfoPOJOS);
        recyclerViewDetailsComplete.setAdapter(mAdapter);

//        prepareData();
        getCompleteOrderDetail();
        btn_order_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkStatus();
            }
        });
    }

    public void checkStatus() {
//        boolean is_checked=false;
//        for(BidInfoPOJO bidInfoPOJO:bidInfoPOJOS){
//            if(bidInfoPOJO.isIs_selected()){
//                is_checked=true;
//            }
//        }

//        if(is_checked){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        int count = 0;
        for (BidInfoPOJO bidInfoPOJO : bidInfoPOJOS) {
//                if(bidInfoPOJO.isIs_selected()) {
            nameValuePairs.add(new BasicNameValuePair("products[" + count + "][order_id]", order_id));
            nameValuePairs.add(new BasicNameValuePair("products[" + count + "][bid_id]", bidInfoPOJO.getBidId()));
            nameValuePairs.add(new BasicNameValuePair("products[" + count + "][product_id]", bidInfoPOJO.getProductId()));
            nameValuePairs.add(new BasicNameValuePair("products[" + count + "][bid_product_id]", bidInfoPOJO.getBidProductId()));
            nameValuePairs.add(new BasicNameValuePair("products[" + count + "][bid_product_recommended_id]", "0"));
            nameValuePairs.add(new BasicNameValuePair("products[" + count + "][seller_id]", seller_id));
            nameValuePairs.add(new BasicNameValuePair("products[" + count + "][user_id]", MyApplication.readStringPref(PrefsData.PREF_USERID)));
            count++;
//                }
        }

        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {

            }
        }, "SAVE_BID_PRODUCTS", true).execute(WebServiceUrl.INSERT_BID_PRODUCTS);
//
//        }else{
//            ToastClass.showShortToast(getApplicationContext(),"Please Check atleast 1 product");
//        }
    }


    public void getCompleteOrderDetail() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("bid_id", bid_id));
        new WebServiceBaseResponseList<BidInfoPOJO>(nameValuePairs, this, new ResponseListCallback<BidInfoPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<BidInfoPOJO> responseListPOJO) {
                try {
                    if (responseListPOJO.isSuccess()) {
                        bidInfoPOJOS.clear();
                        bidInfoPOJOS.addAll(responseListPOJO.getResultList());
                        mAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, BidInfoPOJO.class, "GET_COMPLETE_BID_INFO", true).execute(WebServiceUrl.GET_COMPLETE_BID_PRODUCT_INFO);
    }

//    private void prepareData() {
//        bidInfoPOJOS.clear();
//
//        ViewDetailCompleteModel model=new ViewDetailCompleteModel("Product 1");
//        bidInfoPOJOS.add(model);
//
//        model=new ViewDetailCompleteModel("Product 2");
//        bidInfoPOJOS.add(model);
//
//        model=new ViewDetailCompleteModel("Product 3");
//        bidInfoPOJOS.add(model);
//
//        model=new ViewDetailCompleteModel("Product 4");
//        bidInfoPOJOS.add(model);
//
//        mAdapter.notifyDataSetChanged();
//
//    }

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

    public void placeOrder(BidInfoPOJO bidInfoPOJO) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("order_id", order_id));
        nameValuePairs.add(new BasicNameValuePair("bid_id", bidInfoPOJO.getBidId()));
        nameValuePairs.add(new BasicNameValuePair("product_id", bidInfoPOJO.getProductId()));
        nameValuePairs.add(new BasicNameValuePair("bid_product_id", bidInfoPOJO.getBidProductId()));
        nameValuePairs.add(new BasicNameValuePair("bid_product_recommended_id", "0"));
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
