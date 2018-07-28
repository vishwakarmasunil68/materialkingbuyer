package com.appentus.materialking.views.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appentus.materialking.R;
import com.appentus.materialking.adapter.PartialOrderAdapter;
import com.appentus.materialking.model.PartialOrderModel;
import com.appentus.materialking.pojo.MyOrdersPOJO;
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
import butterknife.Unbinder;

/**
 * Created by Hp on 2/6/2018.
 */

@SuppressLint("ValidFragment")
public class FragmentPartialOrder extends Fragment {
    View view;
    @BindView(R.id.recycler_partial_orders)
    RecyclerView recyclerPartialOrders;
    PartialOrderAdapter mAdapter;
    List<PartialOrderModel> partialOrderModels=new ArrayList<>();

    MyOrdersPOJO myOrdersPOJO;

    public FragmentPartialOrder(MyOrdersPOJO myOrdersPOJO){
        this.myOrdersPOJO=myOrdersPOJO;
    }


    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_partial_oders, container, false);
        unbinder = ButterKnife.bind(this, view);


        recyclerPartialOrders.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new PartialOrderAdapter(getActivity(), partialOrderModels);
        recyclerPartialOrders.setAdapter(mAdapter);


        callAPI();

        return view;
    }


    public void callAPI(){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("order_id",myOrdersPOJO.getOrderId() ));
        new WebServiceBaseResponseList<PartialOrderModel>(nameValuePairs, getActivity(), new ResponseListCallback<PartialOrderModel>() {
            @Override
            public void onGetMsg(ResponseListPOJO<PartialOrderModel> responseListPOJO) {
                try{
                    if(responseListPOJO.isSuccess()){
                        partialOrderModels.clear();
                        partialOrderModels.addAll(responseListPOJO.getResultList());
                        mAdapter.notifyDataSetChanged();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },PartialOrderModel.class,"GET_PARTIAL_BID_DETAILS",true).execute(WebServiceUrl.GET_PARTIAL_ORDER_DETAILS);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
