package com.appentus.materialking.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appentus.materialking.R;
import com.appentus.materialking.pojo.BidPOJO;
import com.appentus.materialking.views.activity.ViewDetailsCompleteOrder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Hp on 2/6/2018.
 */

public class CompleteOrdersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<BidPOJO> completeOdersModels = new ArrayList<>();
    String order_id;

    public CompleteOrdersAdapter(Context context, List<BidPOJO> completeOdersModels,String order_id) {
        this.context = context;
        this.completeOdersModels = completeOdersModels;
        this.order_id=order_id;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_complete_orders, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder holder1 = (ViewHolder) holder;

        holder1.tv_final_offer.setText(completeOdersModels.get(position).getFinal_offer() + " INR");
        holder1.tv_shipping_charges.setText(completeOdersModels.get(position).getShipping_price() + " INR");
        try {
            if (completeOdersModels.get(position).getMin_shipping_days().equalsIgnoreCase(completeOdersModels.get(position).getMax_shipping_days())) {
                holder1.tv_shipping_days.setText(completeOdersModels.get(position).getMin_shipping_days() + " days");
            } else {
                holder1.tv_shipping_days.setText(completeOdersModels.get(position).getMin_shipping_days() + " - " + completeOdersModels.get(position).getMax_shipping_days() + " days");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            holder1.tvTotalAmountCompleted.setText(String.valueOf(Integer.parseInt(completeOdersModels.get(position).getFinal_offer())
                    + Integer.parseInt(completeOdersModels.get(position).getShipping_price())) + " INR");
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder1.btnViewComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ViewDetailsCompleteOrder.class)
                        .putExtra("bid_id", completeOdersModels.get(position).getBidId())
                        .putExtra("order_id", order_id)
                        .putExtra("seller_id", completeOdersModels.get(position).getSellerId())
                );
            }
        });
    }

    @Override
    public int getItemCount() {
        return completeOdersModels.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_total_amount_completed)
        AppCompatTextView tvTotalAmountCompleted;
        @BindView(R.id.btn_view_complete)
        AppCompatButton btnViewComplete;
        @BindView(R.id.tv_shipping_charges)
        AppCompatTextView tv_shipping_charges;
        @BindView(R.id.tv_shipping_days)
        AppCompatTextView tv_shipping_days;
        @BindView(R.id.tv_final_offer)
        AppCompatTextView tv_final_offer;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
