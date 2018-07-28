package com.appentus.materialking.views.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.appentus.materialking.R;
import com.appentus.materialking.adapter.NotificationAdapter;
import com.appentus.materialking.pojo.ResponseListPOJO;
import com.appentus.materialking.pojo.notification.NotificationPOJO;
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

public class NotificationActivity extends AppCompatActivity {

    @BindView(R.id.rv_notifications)
    RecyclerView rv_notifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);

        attachAdapter();

        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", MyApplication.readStringPref(PrefsData.PREF_USERID)));
        new WebServiceBaseResponseList<NotificationPOJO>(nameValuePairs, this, new ResponseListCallback<NotificationPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<NotificationPOJO> responseListPOJO) {
                try{
                    if(responseListPOJO.isSuccess()){
                        notificationPOJOS.addAll(responseListPOJO.getResultList());
                        notificationAdapter.notifyDataSetChanged();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },NotificationPOJO.class,"call_notification",true).execute(WebServiceUrl.NOTIFICATION_LIST);

    }

    NotificationAdapter notificationAdapter;
    List<NotificationPOJO> notificationPOJOS= new ArrayList<>();

    public void attachAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_notifications.setHasFixedSize(true);
        rv_notifications.setLayoutManager(linearLayoutManager);
        notificationAdapter = new NotificationAdapter(this,null,notificationPOJOS);
        rv_notifications.setAdapter(notificationAdapter);
        rv_notifications.setNestedScrollingEnabled(false);
        rv_notifications.setItemAnimator(new DefaultItemAnimator());
    }

}
