package com.appentus.materialking.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appentus.materialking.R;
import com.appentus.materialking.pojo.OrderProductPOJO;
import com.appentus.materialking.views.activity.OrderActivity;
import com.appentus.materialking.webservice.WebServiceUrl;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Hp on 2/6/2018.
 */

public class OrderItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<OrderProductPOJO> items = new ArrayList<>();
    String order_id;

    public OrderItemAdapter(Context context, List<OrderProductPOJO> items) {
        this.context = context;
        this.items = items;
        this.order_id = order_id;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_order_item, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder holder1 = (ViewHolder) holder;
        Picasso.with(context)
                .load(WebServiceUrl.IMAGEBASEURL + items.get(position).getProductVariationImage())
                .placeholder(R.drawable.ic_default_category)
                .error(R.drawable.ic_default_category)
                .into(holder1.iv_product);

        holder1.tv_description.setText(items.get(position).getProductDescription());
        holder1.tv_brand.setText(items.get(position).getBrandName());
        holder1.tv_size.setText(items.get(position).getSizeName());
        holder1.tv_product_name.setText(items.get(position).getProductName());
        holder1.tv_date.setText(items.get(position).getOrder_on());

        holder1.tv_product_selected.setVisibility(View.GONE);
        holder1.tv_bid_placed.setVisibility(View.GONE);
        holder1.tv_no_bid_placed.setVisibility(View.GONE);

        if (items.get(position).isFinal_order()) {
            holder1.tv_product_selected.setVisibility(View.VISIBLE);
        } else if (items.get(position).isBid_placed()) {
            holder1.tv_bid_placed.setVisibility(View.VISIBLE);
        } else {
            holder1.tv_no_bid_placed.setVisibility(View.VISIBLE);
        }

        holder1.ll_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(context, OrderItemBidProductActivity.class);
//                intent.putExtra("order_id",items.get(position).getOrderId());
//                intent.putExtra("product_id",items.get(position).getProductId());
//                context.startActivity(intent);
                Intent intent = new Intent(context, OrderActivity.class);
                intent.putExtra("order_id", items.get(position).getOrderId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_product)
        ImageView iv_product;
        @BindView(R.id.tv_product_name)
        TextView tv_product_name;
        @BindView(R.id.tv_description)
        TextView tv_description;
        @BindView(R.id.tv_brand)
        TextView tv_brand;
        @BindView(R.id.tv_size)
        TextView tv_size;
        @BindView(R.id.tv_date)
        TextView tv_date;
        @BindView(R.id.tv_bid_placed)
        TextView tv_bid_placed;
        @BindView(R.id.tv_product_selected)
        TextView tv_product_selected;
        @BindView(R.id.tv_no_bid_placed)
        TextView tv_no_bid_placed;
        @BindView(R.id.ll_order)
        LinearLayout ll_order;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
