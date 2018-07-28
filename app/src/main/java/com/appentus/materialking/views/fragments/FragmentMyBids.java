package com.appentus.materialking.views.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.appentus.materialking.R;
import com.appentus.materialking.Util.ToastClass;
import com.appentus.materialking.adapter.MyBidsAdapter;
import com.appentus.materialking.pojo.MyOrdersPOJO;
import com.appentus.materialking.pojo.ResponseListPOJO;
import com.appentus.materialking.utility.MyApplication;
import com.appentus.materialking.utility.PrefsData;
import com.appentus.materialking.utility.ProgressView;
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
 * Created by Hp on 2/5/2018.
 */

public class FragmentMyBids extends Fragment {
    View view;
    @BindView(R.id.recycler_my_bids)
    RecyclerView recyclerMyBids;

    MyBidsAdapter mAdapter;
    List<MyOrdersPOJO> myOrdersPOJOS=new ArrayList<>();

    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mybids, container, false);
        unbinder = ButterKnife.bind(this, view);

        recyclerMyBids.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL));
        recyclerMyBids.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new MyBidsAdapter(getActivity(), myOrdersPOJOS);
        recyclerMyBids.setAdapter(mAdapter);

        getAllMyOrder();

        return view;
    }

    public void getAllMyOrder(){
        final ProgressView progressView = new ProgressView(getActivity());
        progressView.showLoader();

        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", MyApplication.readStringPref(PrefsData.PREF_USERID)));
        new WebServiceBaseResponseList<MyOrdersPOJO>(nameValuePairs, getActivity(), new ResponseListCallback<MyOrdersPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<MyOrdersPOJO> responseListPOJO) {
                try {
                    myOrdersPOJOS.clear();
                    if (responseListPOJO.isSuccess()) {
                        myOrdersPOJOS.addAll(responseListPOJO.getResultList());
                    } else {
                        ToastClass.showShortToast(getActivity().getApplicationContext(), responseListPOJO.getMessage());
                    }
                    mAdapter.notifyDataSetChanged();
                }catch (Exception e){
                    e.printStackTrace();
                }
                progressView.hideLoader();
            }
        },MyOrdersPOJO.class,"MyOrder",false).execute(WebServiceUrl.MY_ORDER_LIST);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
