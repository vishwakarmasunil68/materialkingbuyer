package com.appentus.materialking.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.appentus.materialking.R;
import com.appentus.materialking.model.MyBidsModel;
import com.appentus.materialking.pojo.MyOrdersPOJO;
import com.appentus.materialking.views.activity.OrderActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Hp on 2/6/2018.
 */

public class MyBidsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<MyOrdersPOJO> items = new ArrayList<>();


    public MyBidsAdapter(Context context, List<MyOrdersPOJO> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_my_bids_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder holder1 = (ViewHolder) holder;
        holder1.tvBidOrderId.setText("ORDER ID : #"+items.get(position).getOrderId());
        holder1.tvBidDate.setText("Date : "+items.get(position).getOrderOn());
        holder1.tvBidStatus.setText("Order Status : "+items.get(position).getOrderStatus());
        holder1.tvBidCompletedOrders.setText("Complete Order list : "+items.get(position).getCompletedOrderBids());
        holder1.tvBidRecommendedOrders.setText("Recommended Order list : "+items.get(position).getRecommendedBids());
        holder1.tvBidPartialOrders.setText("Partial Order list : "+items.get(position).getPartialOrderBids());
        holder1.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,OrderActivity.class);
                intent.putExtra("order_id",items.get(position).getOrderId());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_bid_orderId)
        AppCompatTextView tvBidOrderId;
        @BindView(R.id.tv_bid_date)
        AppCompatTextView tvBidDate;
        @BindView(R.id.tv_bid_status)
        AppCompatTextView tvBidStatus;
        @BindView(R.id.tv_bid_completed_orders)
        AppCompatTextView tvBidCompletedOrders;
        @BindView(R.id.tv_bid_recommended_orders)
        AppCompatTextView tvBidRecommendedOrders;
        @BindView(R.id.tv_bid_partial_orders)
        AppCompatTextView tvBidPartialOrders;
        @BindView(R.id.iv_next)
        ImageView ivNext;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
