package com.appentus.materialking.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.appentus.materialking.R;
import com.appentus.materialking.adapter.CartAdapter;
import com.appentus.materialking.model.CartModel;
import com.appentus.materialking.views.activity.ShippingConfirmationActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by Hp on 1/17/2018.
 */

public class FragmentCart extends Fragment {

    View view;
    @BindView(R.id.recycler_cart)
    RecyclerView recyclerCart;
    CartAdapter mAdapter;
    List<CartModel> cartModels = new ArrayList<>();

    @BindView(R.id.gif_view)
    GifImageView gifView;
    @BindView(R.id.linear_bottom_item_layout)
    LinearLayout linearBottomItemLayout;

    Unbinder unbinder;
    @BindView(R.id.btn_send_to_suppliers)
    AppCompatButton btnSendToSuppliers;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cart, container, false);
        unbinder = ButterKnife.bind(this, view);

        recyclerCart.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        mAdapter = new CartAdapter(getActivity(), cartModels);
        recyclerCart.setAdapter(mAdapter);
        prepareData();

        gifView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gifView.setVisibility(View.GONE);
                linearBottomItemLayout.setVisibility(View.VISIBLE);
                recyclerCart.setVisibility(View.VISIBLE);
            }
        });


        btnSendToSuppliers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ShippingConfirmationActivity.class));
            }
        });

        return view;
    }

    private void prepareData() {
        cartModels.clear();

        CartModel model = new CartModel("Bharathi OPC 43 Grade", "10");
        cartModels.add(model);

        model = new CartModel("Zenkore OPC 43 Grade", "25");
        cartModels.add(model);

        model = new CartModel("Superfast OPC 43 Grade", "13");
        cartModels.add(model);

        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
