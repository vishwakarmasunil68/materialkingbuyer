package com.appentus.materialking.views.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.appentus.materialking.R;
import com.appentus.materialking.adapter.ConfirmOrderAdapter;
import com.appentus.materialking.adapter.ViewDetailsPartialAdapter;
import com.appentus.materialking.model.ConfirmOrderModel;
import com.appentus.materialking.model.ViewDetailPartialModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderConfirmationActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_confirm_order)
    RecyclerView recyclerConfirmOrder;

    ConfirmOrderAdapter mAdapter;
    List<ConfirmOrderModel> confirmOrderModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Confirm Order");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        recyclerConfirmOrder.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ConfirmOrderAdapter(this, confirmOrderModels);
        recyclerConfirmOrder.setAdapter(mAdapter);

        prepareData();

    }

    private void prepareData() {
        confirmOrderModels.clear();

        ConfirmOrderModel model = new ConfirmOrderModel("Teak & Steel \nPetanque Set");
        confirmOrderModels.add(model);

        model = new ConfirmOrderModel("Lemon Peel Baseball");
        confirmOrderModels.add(model);

        model = new ConfirmOrderModel("Seil Marschall Hiking");
        confirmOrderModels.add(model);

        model = new ConfirmOrderModel("Product 4");
        confirmOrderModels.add(model);

        mAdapter.notifyDataSetChanged();
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

}
