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
import com.appentus.materialking.adapter.RecommendedOrderAdapter;
import com.appentus.materialking.pojo.MyOrdersPOJO;
import com.appentus.materialking.pojo.RecommendedBidPOJO;
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
public class FragmentRecommendedOrder extends Fragment {
    View view;
    @BindView(R.id.recycler_recommended)
    RecyclerView recyclerRecommended;
    RecommendedOrderAdapter mAdapter;
    List<RecommendedBidPOJO> recommendedOrderModels = new ArrayList<>();

    Unbinder unbinder;

    String order_id;

    public FragmentRecommendedOrder(String order_id){
        this.order_id=order_id;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recommended_oders, container, false);
        unbinder = ButterKnife.bind(this, view);



        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerRecommended.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new RecommendedOrderAdapter(getActivity(), recommendedOrderModels);
        recyclerRecommended.setAdapter(mAdapter);

        getRecommendBids();
    }

    public void getRecommendBids(){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("order_id",order_id));
        new WebServiceBaseResponseList<RecommendedBidPOJO>(nameValuePairs, getActivity(), new ResponseListCallback<RecommendedBidPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<RecommendedBidPOJO> responseListPOJO) {
                try{
                    if(responseListPOJO.isSuccess()){
                        recommendedOrderModels.clear();
                        recommendedOrderModels.addAll(responseListPOJO.getResultList());
                        mAdapter.notifyDataSetChanged();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },RecommendedBidPOJO.class,"GET_RECOMMENDED_BID_DETAILS",true).execute(WebServiceUrl.GET_RECOMMENDED_BID);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
