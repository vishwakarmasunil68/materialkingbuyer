package com.appentus.materialking.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appentus.materialking.R;
import com.appentus.materialking.pojo.FinalOrderPOJO;
import com.appentus.materialking.views.activity.ReceiptActivity;
import com.appentus.materialking.views.activity.SellerDetailActivity;
import com.appentus.materialking.webservice.WebServiceUrl;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 03-11-2017.
 */

public class NotificationBidInfoAdapter extends RecyclerView.Adapter<NotificationBidInfoAdapter.ViewHolder> {
    private List<FinalOrderPOJO> items;
    Activity activity;
    Fragment fragment;

    public NotificationBidInfoAdapter(Activity activity, Fragment fragment, List<FinalOrderPOJO> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_final_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Picasso.with(activity.getApplicationContext())
                .load(WebServiceUrl.IMAGEBASEURL + items.get(position).getBidProductInfo().getImage())
                .into(holder.iv_product_image);


        holder.tv_product_name.setText(items.get(position).getBidProductInfo().getName());

//        if (items.get(position).getSellerStatus().equalsIgnoreCase("0")) {
//            holder.tv_status.setText("PENDING");
//            holder.tv_status.setBackgroundColor(Color.parseColor("#FFFF00"));
//        } else if (items.get(position).getSellerStatus().equalsIgnoreCase("1")) {
//            holder.tv_status.setText("ACCEPTED");
//            holder.tv_status.setBackgroundColor(Color.parseColor("#00FF00"));
//        } else if (items.get(position).getSellerStatus().equalsIgnoreCase("-1")) {
//            holder.tv_status.setText("DECLINED");
//            holder.tv_status.setBackgroundColor(Color.parseColor("#FF0000"));
//        }
        holder.tv_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity, ReceiptActivity.class);
                intent.putExtra("order_id",items.get(position).getOrderId());
                intent.putExtra("user_id",items.get(position).getUserId());
                intent.putExtra("seller_id",items.get(position).getSellerId());
                activity.startActivity(intent);
            }
        });

        if (items.get(position).getBidProductRecommendedId().equals("0")) {
            holder.ll_recommended.setVisibility(View.GONE);
            holder.tv_price_placed.setText("Price :- " + items.get(position).getBidProductInfo().getPriceHave() + " INR");
            holder.tv_shipping_charge.setText("Shipping Charges :- " + items.get(position).getBidProductInfo().getShippingCharge() + " INR");
            holder.tv_delivered_in.setText(items.get(position).getBidProductInfo().getDeliveredOn() + " days");

        } else {
            holder.ll_recommended.setVisibility(View.VISIBLE);
            holder.tv_recommended_name.setText(items.get(position).getBidProductInfo().getOfferRecommendedProduct().getName());
            Picasso.with(activity.getApplicationContext())
                    .load(WebServiceUrl.IMAGEBASEURL + items.get(position).getBidProductInfo().getOfferRecommendedProduct().getImage())
                    .into(holder.iv_product_recommended_image);
            holder.tv_price_placed.setText("Price :- " + items.get(position).getBidProductInfo().getOfferRecommendedProduct().getPriceHave() + " INR");
            holder.tv_shipping_charge.setText("Shipping Charges :- " + items.get(position).getBidProductInfo().getOfferRecommendedProduct().getShipping_charge() + " INR");
            holder.tv_delivered_in.setText(items.get(position).getBidProductInfo().getOfferRecommendedProduct().getDeliveredOn() + " days");

        }


        holder.tv_seller_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, SellerDetailActivity.class);
                intent.putExtra("seller_id", items.get(position).getSellerId());
                activity.startActivity(intent);
            }
        });

        holder.itemView.setTag(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_product_image)
        ImageView iv_product_image;
        @BindView(R.id.tv_product_name)
        TextView tv_product_name;
        @BindView(R.id.tv_price_placed)
        TextView tv_price_placed;
        @BindView(R.id.tv_status)
        TextView tv_status;
        @BindView(R.id.tv_seller_details)
        TextView tv_seller_details;
        @BindView(R.id.tv_delivered_in)
        TextView tv_delivered_in;
        @BindView(R.id.tv_shipping_charge)
        TextView tv_shipping_charge;
        @BindView(R.id.ll_recommended)
        LinearLayout ll_recommended;
        @BindView(R.id.iv_product_recommended_image)
        ImageView iv_product_recommended_image;
        @BindView(R.id.tv_recommended_name)
        TextView tv_recommended_name;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
