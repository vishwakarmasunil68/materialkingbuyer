package com.appentus.materialking.views.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.appentus.materialking.R;
import com.appentus.materialking.Util.ToastClass;
import com.appentus.materialking.adapter.OrderItemAdapter;
import com.appentus.materialking.pojo.OrderProductPOJO;
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

public class OrderItemViewActivity extends AppCompatActivity {

    String order_id="";

    @BindView(R.id.rv_items)
    RecyclerView rv_items;
    List<OrderProductPOJO> orderProductPOJOS=new ArrayList<>();

    OrderItemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_item_view);
        ButterKnife.bind(this);

//        Bundle bundle=new Bundle();
//        if(bundle!=null){
//            order_id=bundle.getString("order_id");
//        }
        order_id=getIntent().getStringExtra("order_id");

        rv_items.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new OrderItemAdapter(this, orderProductPOJOS);
        rv_items.setAdapter(mAdapter);

        callAPI();

    }

    public void callAPI(){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("order_id",order_id));
        new WebServiceBaseResponseList<OrderProductPOJO>(nameValuePairs, this, new ResponseListCallback<OrderProductPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<OrderProductPOJO> responseListPOJO) {
                try{
                    if(responseListPOJO.isSuccess()){
                        orderProductPOJOS.clear();
                        orderProductPOJOS.addAll(responseListPOJO.getResultList());
                        mAdapter.notifyDataSetChanged();
                    }else{
                        ToastClass.showShortToast(getApplicationContext(),responseListPOJO.getMessage());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, OrderProductPOJO.class, "GET_ORDER_DATA", true).execute(WebServiceUrl.GET_ORDER_ITEMS);
    }

}
