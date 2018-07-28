package com.appentus.materialking.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.appentus.materialking.R;
import com.appentus.materialking.interfaces.OnItemCheckedListener;
import com.appentus.materialking.model.ViewDetailRecommendedModel;
import com.appentus.materialking.pojo.BidInfoPOJO;
import com.appentus.materialking.pojo.RecommendedBidInfoPOJO;
import com.appentus.materialking.views.activity.ViewOrderRecommended;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Hp on 2/7/2018.
 */

public class ViewDetailRecommendedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<BidInfoPOJO> items = new ArrayList<>();
    public ViewDetailRecommendedAdapter(Context context, List<BidInfoPOJO> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_recommended_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder holder1 = (ViewHolder) holder;

        holder1.tvViewRecommendedName.setText(items.get(position).getName());
        if(items.get(position).getRecommendYesNo().equalsIgnoreCase("1")){
            holder1.tv_replacement.setVisibility(View.VISIBLE);
        }else{
            holder1.tv_replacement.setVisibility(View.GONE);
        }
        RecommendtItemsAdapter mAdapter = null;
        if(items.get(position).getRecommendedBidInfoPOJOS()!=null
                &&items.get(position).getRecommendedBidInfoPOJOS().size()>0){
            holder1.rv_recommend_products.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
            mAdapter = new RecommendtItemsAdapter(context,items.get(position), items.get(position).getRecommendedBidInfoPOJOS());
            holder1.rv_recommend_products.setAdapter(mAdapter);
            holder1.rv_recommend_products.setNestedScrollingEnabled(false);
//            mAdapter.setItemCheckedListener(new OnItemCheckedListener() {
//                @Override
//                public void onItemChecked(boolean isChecked) {
//                    if(isChecked){
//                        holder1.cb_check_complete.setChecked(isChecked);
//                    }
//                }
//            });
            holder1.bt_order.setVisibility(View.GONE);
        }else{
            holder1.bt_order.setVisibility(View.VISIBLE);
        }

        final RecommendtItemsAdapter finalMAdapter = mAdapter;
//        holder1.cb_check_complete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                items.get(position).setIs_selected(isChecked);
//                if(!isChecked){
//                    if(items.get(position).getRecommendedBidInfoPOJOS()!=null
//                            && finalMAdapter !=null
//                            &&items.get(position).getRecommendedBidInfoPOJOS().size()>0){
//                        finalMAdapter.removeAllChecks();
//                    }
//                }
//            }
//        });
        holder1.bt_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(context instanceof ViewOrderRecommended){
                    ViewOrderRecommended viewOrderRecommended= (ViewOrderRecommended) context;
                    viewOrderRecommended.placeOrder(items.get(position),"0");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_view_recommended_name)
        AppCompatTextView tvViewRecommendedName;
        @BindView(R.id.tv_replacement)
        AppCompatTextView tv_replacement;
        @BindView(R.id.rv_recommend_products)
        RecyclerView rv_recommend_products;
        @BindView(R.id.bt_order)
        Button bt_order;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
