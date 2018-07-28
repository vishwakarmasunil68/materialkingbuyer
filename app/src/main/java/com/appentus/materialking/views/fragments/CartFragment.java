package com.appentus.materialking.views.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.appentus.materialking.R;
import com.appentus.materialking.Util.ToastClass;
import com.appentus.materialking.adapter.CartListAdapter;
import com.appentus.materialking.pojo.cart.CartItemPOJO;
import com.appentus.materialking.utility.MyApplication;
import com.appentus.materialking.utility.PrefsData;
import com.appentus.materialking.webservice.WebServiceUrl;
import com.appentus.materialking.views.activity.MainActivity;
import com.appentus.materialking.webservice.WebServiceBase;
import com.appentus.materialking.webservice.WebServicesCallBack;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartFragment extends Fragment {

    @BindView(R.id.rv_cart)
    RecyclerView rv_cart;
    @BindView(R.id.btn_order)
    Button btn_order;
    @BindView(R.id.check_recommended_products)
    CheckBox check_recommended_products;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_cart, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        attachAdapter();
        callCartApi();

        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmDialog();
            }
        });
    }

    public void showConfirmDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Confirmation");
        alertDialog.setMessage("Do you want to send the list to seller as you will not be able to edit the list later?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (getActivity() instanceof MainActivity) {
                    MainActivity mainActivity = (MainActivity) getActivity();
                    ScheduleOrderFragment scheduleOrderFragment = new ScheduleOrderFragment();
                    Bundle bundle = new Bundle();
                    if (check_recommended_products.isChecked()) {
                        bundle.putString("is_recommended", "1");
                    } else {
                        bundle.putString("is_recommended", "0");
                    }
                    scheduleOrderFragment.setArguments(bundle);
                    mainActivity.changeFragmentHome(scheduleOrderFragment, "ScheduleOrderFragment");
                }
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }


    private void callCartApi() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", MyApplication.readStringPref(PrefsData.PREF_USERID)));
        new WebServiceBase(nameValuePairs, getActivity(), new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try {
                    cartItemPOJOS.clear();
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optInt("status") == 1) {
                        JSONArray jsonArray = jsonObject.optJSONArray("result");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Gson gson = new Gson();
                            CartItemPOJO cartItemPOJO = gson.fromJson(jsonArray.optJSONObject(i).toString(), CartItemPOJO.class);
                            cartItemPOJOS.add(cartItemPOJO);
                        }
                        btn_order.setVisibility(View.VISIBLE);
                    } else {
                        btn_order.setVisibility(View.GONE);
                        ToastClass.showShortToast(getActivity().getApplicationContext(), jsonObject.optString("message"));
                    }
                    cartListAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "CALL_CART_API", true).execute(WebServiceUrl.CART_LIST);
    }

    List<CartItemPOJO> cartItemPOJOS = new ArrayList<>();
    CartListAdapter cartListAdapter;

    public void attachAdapter() {

        cartListAdapter = new CartListAdapter(getActivity(), cartItemPOJOS);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_cart.setHasFixedSize(true);
        rv_cart.setAdapter(cartListAdapter);
        rv_cart.setLayoutManager(linearLayoutManager);
        rv_cart.setItemAnimator(new DefaultItemAnimator());
    }

}
