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
import com.appentus.materialking.model.PartialOrderModel;
import com.appentus.materialking.views.activity.ViewDetailsPartialOrder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Hp on 2/6/2018.
 */

public class PartialOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<PartialOrderModel> items = new ArrayList<>();


    public PartialOrderAdapter(Context context, List<PartialOrderModel> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_partial_order, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder holder1 = (ViewHolder) holder;


        holder1.tv_apply_bid_products.setText("Applied Bid on " + items.get(position).getTotalCount() + " products");
        holder1.tv_final_offer.setText("Final Offer: " + items.get(position).getFinalOffer() + " INR");
        holder1.tv_shipping_charges.setText("Shipping Charges: " + items.get(position).getShippingPrice() + " INR");
        holder1.tv_seller_num.setText("Seller - " + (position + 1));
        try {
            if (items.get(position).getMin_shipping_days().equalsIgnoreCase(items.get(position).getMax_shipping_days())) {
                holder1.tv_dispatched_days.setText("Dispatched in : "+items.get(position).getMin_shipping_days() + " days");
            } else {
                holder1.tv_dispatched_days.setText("Dispatched in : "+items.get(position).getMin_shipping_days() + " - " + items.get(position).getMax_shipping_days() + " days");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder1.btn_view_details_partial_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewDetailsPartialOrder.class);
                intent.putExtra("bid_id", items.get(position).getBidId());
                intent.putExtra("order_id", items.get(position).getOrderId());
                intent.putExtra("seller_id", items.get(position).getSellerId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_apply_bid_products)
        AppCompatTextView tv_apply_bid_products;
        @BindView(R.id.tv_final_offer)
        AppCompatTextView tv_final_offer;
        @BindView(R.id.tv_shipping_charges)
        AppCompatTextView tv_shipping_charges;
        @BindView(R.id.tv_dispatched_days)
        AppCompatTextView tv_dispatched_days;
        @BindView(R.id.tv_seller_num)
        AppCompatTextView tv_seller_num;
        @BindView(R.id.btn_view_details_partial_order)
        AppCompatButton btn_view_details_partial_order;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
