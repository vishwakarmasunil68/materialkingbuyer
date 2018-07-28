package com.appentus.materialking.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appentus.materialking.R;
import com.appentus.materialking.model.ViewDetailPartialModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Hp on 2/8/2018.
 */

public class ViewDetailsPartialAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<ViewDetailPartialModel> viewDetailPartialModels = new ArrayList<>();


    public ViewDetailsPartialAdapter(Context context, List<ViewDetailPartialModel> viewDetailPartialModels) {
        this.context = context;
        this.viewDetailPartialModels = viewDetailPartialModels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_partial_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder holder1 = (ViewHolder) holder;
       holder1.tvViewPartialName.setText(viewDetailPartialModels.get(position).getPartialProductAvailable());
    }

    @Override
    public int getItemCount() {
        return viewDetailPartialModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_view_partial_name)
        AppCompatTextView tvViewPartialName;
        @BindView(R.id.tv_view_partial_amount)
        AppCompatTextView tvViewPartialAmount;
        @BindView(R.id.cb_check_partial)
        AppCompatCheckBox cbCheckPartial;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
