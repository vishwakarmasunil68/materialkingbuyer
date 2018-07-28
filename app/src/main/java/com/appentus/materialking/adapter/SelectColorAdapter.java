package com.appentus.materialking.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.appentus.materialking.R;
import com.appentus.materialking.model.SelectColorModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Hp on 2/7/2018.
 */

public class SelectColorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<SelectColorModel> selectColorModels = new ArrayList<>();


    public SelectColorAdapter(Context context, List<SelectColorModel> selectColorModels) {
        this.context = context;
        this.selectColorModels = selectColorModels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_select_color_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder holder1 = (ViewHolder) holder;

        holder1.linSelectColor.setBackgroundColor(Color.parseColor(selectColorModels.get(position).getColor()));


        holder1.linSelectRootBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < selectColorModels.size(); i++) {
                    if (i == position) {
                        selectColorModels.get(position).setColorSelected(true);
                    } else {
                        selectColorModels.get(position).setColorSelected(false);
                    }
                }
                notifyDataSetChanged();
            }
        });

        if (selectColorModels.get(position).isColorSelected()) {
            holder1.linSelectRootBackground.setBackgroundColor(Color.RED);
        } else {
            holder1.linSelectRootBackground.setBackgroundColor(Color.WHITE);
        }


    }

    @Override
    public int getItemCount() {
        return selectColorModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.lin_select_color)
        LinearLayout linSelectColor;

        @BindView(R.id.lin_select_root_background)
        LinearLayout linSelectRootBackground;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
