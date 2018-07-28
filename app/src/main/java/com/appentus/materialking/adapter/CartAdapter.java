package com.appentus.materialking.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.appentus.materialking.R;
import com.appentus.materialking.model.CartModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Hp on 2/5/2018.
 */

public class CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<CartModel> cartModels = new ArrayList<>();
    int count=0;


    public CartAdapter(Context context, List<CartModel> cartModels) {
        this.context = context;
        this.cartModels = cartModels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_cart_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHolder holder1 = (ViewHolder) holder;

        holder1.tvItemName.setText(cartModels.get(position).getItemName());
        holder1.tvQuantityCart.setText(cartModels.get(position).getQuantity());


        holder1.tvMinusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count>0){
                    count--;
                    holder1.tvQuantityCart.setText(String.valueOf(count));
                    notifyDataSetChanged();
                }

            }
        });

        holder1.tvPlusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                holder1.tvQuantityCart.setText(String.valueOf(count));
                notifyDataSetChanged();
            }
        });



    }

    @Override
    public int getItemCount() {
        return cartModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_item_image_cart)
        ImageView ivItemImageCart;
        @BindView(R.id.tv_item_name)
        AppCompatTextView tvItemName;
        @BindView(R.id.tv_minus_item)
        AppCompatTextView tvMinusItem;
        @BindView(R.id.tv_quantity_cart)
        AppCompatTextView tvQuantityCart;
        @BindView(R.id.tv_plus_item)
        AppCompatTextView tvPlusItem;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
