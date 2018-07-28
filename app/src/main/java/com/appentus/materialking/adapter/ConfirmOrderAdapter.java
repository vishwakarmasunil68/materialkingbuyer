package com.appentus.materialking.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appentus.materialking.R;
import com.appentus.materialking.model.ConfirmOrderModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Hp on 2/8/2018.
 */

public class ConfirmOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<ConfirmOrderModel> confirmOrderModels = new ArrayList<>();



    public ConfirmOrderAdapter(Context context, List<ConfirmOrderModel> confirmOrderModels) {
        this.context = context;
        this.confirmOrderModels = confirmOrderModels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_confirm_order_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder holder1 = (ViewHolder) holder;

        holder1.tvItemNameConfirm.setText(confirmOrderModels.get(position).getProductNameConfirm());
    }

    @Override
    public int getItemCount() {
        return confirmOrderModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_item_name_confirm)
        AppCompatTextView tvItemNameConfirm;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
