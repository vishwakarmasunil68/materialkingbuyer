package com.appentus.materialking.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.appentus.materialking.R;
import com.appentus.materialking.model.ChooseBrandModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hp on 1/18/2018.
 */

public class ChooseBrandAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<ChooseBrandModel> chooseBrandModels = new ArrayList<>();

    public ChooseBrandAdapter(Context context, List<ChooseBrandModel> chooseBrandModels) {
        this.context = context;
        this.chooseBrandModels = chooseBrandModels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_choose_brand_layout, parent, false);

        return new ChooseBrandAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder holder1 = (ViewHolder) holder;

        Picasso.with(context).load(chooseBrandModels.get(position).getItemImage()).placeholder(R.mipmap.ic_launcher).into(holder1.itemImage);
        holder1.itemName.setText(chooseBrandModels.get(position).getItemTitle());

        if (chooseBrandModels.get(position).isSelectedBackground()) {
            holder1.lin_item.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        } else {
            holder1.lin_item.setBackgroundColor(Color.WHITE);
        }

        holder1.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < chooseBrandModels.size(); i++) {
                    chooseBrandModels.get(i).setSelectedBackground(false);
                }
                chooseBrandModels.get(position).setSelectedBackground(true);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return chooseBrandModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView itemImage;
        AppCompatTextView itemName;
        LinearLayout lin_item;

        public ViewHolder(View itemView) {
            super(itemView);

            itemImage = itemView.findViewById(R.id.iv_item_image);
            itemName = itemView.findViewById(R.id.tv_item_name);
            lin_item = itemView.findViewById(R.id.linear_item);
        }
    }
}
