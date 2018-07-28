package com.appentus.materialking.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;

import com.appentus.materialking.R;
import com.appentus.materialking.model.ViewDetailCompleteModel;
import com.appentus.materialking.pojo.BidInfoPOJO;
import com.appentus.materialking.views.activity.ViewDetailsCompleteOrder;
import com.appentus.materialking.views.activity.ViewDetailsPartialOrder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Hp on 2/7/2018.
 */

public class ViewDetailCompleteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<BidInfoPOJO> viewDetailCompleteModels = new ArrayList<>();

    public ViewDetailCompleteAdapter(Context context, List<BidInfoPOJO> viewDetailCompleteModels) {
        this.context = context;
        this.viewDetailCompleteModels = viewDetailCompleteModels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_complete_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder holder1 = (ViewHolder) holder;
        holder1.tvViewCompleteName.setText(viewDetailCompleteModels.get(position).getName());
        holder1.tvViewCompleteAmount.setText("Offers: "+viewDetailCompleteModels.get(position).getPriceHave()+" INR");
        holder1.btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(context instanceof ViewDetailsCompleteOrder){
                    ViewDetailsCompleteOrder viewDetailsCompleteOrder= (ViewDetailsCompleteOrder) context;
                    viewDetailsCompleteOrder.placeOrder(viewDetailCompleteModels.get(position));
                }else if(context instanceof ViewDetailsPartialOrder){
                    ViewDetailsPartialOrder viewDetailsPartialOrder= (ViewDetailsPartialOrder) context;
                    viewDetailsPartialOrder.placeOrder(viewDetailCompleteModels.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return viewDetailCompleteModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_view_complete_name)
        AppCompatTextView tvViewCompleteName;
        @BindView(R.id.tv_view_complete_amount)
        AppCompatTextView tvViewCompleteAmount;
        @BindView(R.id.btn_order)
        Button btn_order;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
