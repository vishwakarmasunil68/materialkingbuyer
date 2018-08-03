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
import com.appentus.materialking.adapter.CompleteOrdersAdapter;
import com.appentus.materialking.pojo.BidPOJO;
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
public class FragmentCompleteOrder extends Fragment {
    View view;
    @BindView(R.id.recycler_complete_orders)
    RecyclerView recyclerCompleteOrders;
    CompleteOrdersAdapter mAdapter;
    List<BidPOJO> completeOdersModels = new ArrayList<>();
    Unbinder unbinder;
    String order_id;

    public FragmentCompleteOrder(String order_id) {
        this.order_id = order_id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_completed_oders, container, false);
        unbinder = ButterKnife.bind(this, view);


        recyclerCompleteOrders.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        mAdapter = new CompleteOrdersAdapter(getActivity(), completeOdersModels,order_id);
        recyclerCompleteOrders.setAdapter(mAdapter);


        getCompleteOrderDetail();
        return view;
    }



    public void getCompleteOrderDetail() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("order_id", order_id));
        new WebServiceBaseResponseList<BidPOJO>(nameValuePairs, getActivity(), new ResponseListCallback<BidPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<BidPOJO> responseListPOJO) {
                try {
                    if (responseListPOJO.isSuccess()) {
                        completeOdersModels.clear();
                        completeOdersModels.addAll(responseListPOJO.getResultList());
                        mAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, BidPOJO.class, "GET_COMPLETE_BID_INFO", true).execute(WebServiceUrl.GET_COMPLETE_ORDER_DETAIL);
    }


//    private void prepareData() {
//        completeOdersModels.clear();
//
//        CompleteOdersModel model = new CompleteOdersModel("15,00000 INR");
//        completeOdersModels.add(model);
//
//        model = new CompleteOdersModel("15,00000 INR");
//        completeOdersModels.add(model);
//
//        model = new CompleteOdersModel("15,00000 INR");
//        completeOdersModels.add(model);
//
//        mAdapter.notifyDataSetChanged();
//
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
