package com.appentus.materialking.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.appentus.materialking.R;
import com.appentus.materialking.model.CartModel;
import com.appentus.materialking.pojo.SellerTypePOJO;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Hp on 2/5/2018.
 */

public class SellerTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<SellerTypePOJO> sellerTypePOJOS = new ArrayList<>();
    int count=0;


    public SellerTypeAdapter(Context context, List<SellerTypePOJO> sellerTypePOJOS) {
        this.context = context;
        this.sellerTypePOJOS = sellerTypePOJOS;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_seller_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder holder1 = (ViewHolder) holder;

        holder1.check_type.setText(sellerTypePOJOS.get(position).getSellerTypeName());

        holder1.check_type.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sellerTypePOJOS.get(position).setIs_checked(isChecked);
            }
        });

    }

    @Override
    public int getItemCount() {
        return sellerTypePOJOS.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.check_type)
        CheckBox check_type;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
