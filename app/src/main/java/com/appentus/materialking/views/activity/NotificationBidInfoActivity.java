package com.appentus.materialking.views.activity;

import android.app.Notification;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.appentus.materialking.R;
import com.appentus.materialking.adapter.NotificationAdapter;
import com.appentus.materialking.adapter.NotificationBidInfoAdapter;
import com.appentus.materialking.pojo.FinalOrderPOJO;
import com.appentus.materialking.pojo.ResponseListPOJO;
import com.appentus.materialking.pojo.ResponsePOJO;
import com.appentus.materialking.pojo.notification.NotificationPOJO;
import com.appentus.materialking.webservice.ResponseCallBack;
import com.appentus.materialking.webservice.ResponseListCallback;
import com.appentus.materialking.webservice.WebServiceBaseResponse;
import com.appentus.materialking.webservice.WebServiceUrl;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationBidInfoActivity extends AppCompatActivity {
    String notification_id="";

    @BindView(R.id.rv_bids)
    RecyclerView rv_bids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_bid_info);
        ButterKnife.bind(this);
        attachAdapter();
        notification_id=getIntent().getStringExtra("notification_id");

        getNotificationDetails();
    }

    public void getNotificationDetails(){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("id",notification_id));
        new WebServiceBaseResponse<NotificationPOJO>(nameValuePairs, this, new ResponseCallBack<NotificationPOJO>() {
            @Override
            public void onGetMsg(ResponsePOJO<NotificationPOJO> responsePOJO) {
                try{
                    if(responsePOJO.isSuccess()){
                        finalOrderPOJOS.addAll(responsePOJO.getResult().getFinalOrderPOJOS());
                        notificationBidInfoAdapter.notifyDataSetChanged();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, NotificationPOJO.class, "GET_Notification", true).execute(WebServiceUrl.GET_NOTIFICATION);
    }



    NotificationBidInfoAdapter notificationBidInfoAdapter;
    List<FinalOrderPOJO> finalOrderPOJOS= new ArrayList<>();

    public void attachAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_bids.setHasFixedSize(true);
        rv_bids.setLayoutManager(linearLayoutManager);
        notificationBidInfoAdapter = new NotificationBidInfoAdapter(this,null,finalOrderPOJOS);
        rv_bids.setAdapter(notificationBidInfoAdapter);
        rv_bids.setNestedScrollingEnabled(false);
        rv_bids.setItemAnimator(new DefaultItemAnimator());
    }



}
