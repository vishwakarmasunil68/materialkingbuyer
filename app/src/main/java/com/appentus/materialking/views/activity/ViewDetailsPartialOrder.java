package com.appentus.materialking.views.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

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

public class ViewDetailsPartialOrder extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view_details_partial)
    RecyclerView recyclerViewDetailsPartial;
    @BindView(R.id.btn_view_details_partial_book)
    AppCompatButton btnViewDetailsPartialBook;

    ViewDetailCompleteAdapter mAdapter;
    List<BidInfoPOJO> bidInfoPOJOS = new ArrayList<>();

    String bid_id;
    String order_id;
    String seller_id = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details_partial_order);
        ButterKnife.bind(this);

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            bid_id=bundle.getString("bid_id");
            order_id=bundle.getString("order_id");
            seller_id=bundle.getString("seller_id");
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Select Similar Products");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        recyclerViewDetailsPartial.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ViewDetailCompleteAdapter(this, bidInfoPOJOS);
        recyclerViewDetailsPartial.setAdapter(mAdapter);

        getCompleteOrderDetail();
    }


    public void getCompleteOrderDetail(){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("bid_id",bid_id));
        new WebServiceBaseResponseList<BidInfoPOJO>(nameValuePairs, this, new ResponseListCallback<BidInfoPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<BidInfoPOJO> responseListPOJO) {
                try{
                    if(responseListPOJO.isSuccess()){
                        bidInfoPOJOS.clear();
                        bidInfoPOJOS.addAll(responseListPOJO.getResultList());
                        mAdapter.notifyDataSetChanged();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },BidInfoPOJO.class,"GET_COMPLETE_BID_INFO",true).execute(WebServiceUrl.GET_COMPLETE_BID_PRODUCT_INFO);
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
