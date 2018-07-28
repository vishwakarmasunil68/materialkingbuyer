package com.appentus.materialking.adapter;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.appentus.materialking.R;
import com.appentus.materialking.pojo.home.ProductPOJO;
import com.appentus.materialking.utility.Utilis;
import com.appentus.materialking.webservice.WebServiceUrl;
import com.appentus.materialking.views.activity.MainActivity;
import com.appentus.materialking.views.fragments.DetailItemFragment;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Hp on 1/17/2018.
 */

public class ResultFoundAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<ProductPOJO> resultFoundModels = new ArrayList<>();

    String cat_name;
    String sub_cat_name;

    public ResultFoundAdapter(Context context, List<ProductPOJO> resultFoundModels,String category_name,String sub_cat_name) {
        this.context = context;
        this.cat_name=category_name;
        this.sub_cat_name=sub_cat_name;
        this.resultFoundModels = resultFoundModels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_result_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder holder1 = (ViewHolder) holder;
        holder1.subcaterogy_title.setText(resultFoundModels.get(position).getName());

        holder1.subcaterogy_title.setHorizontallyScrolling(true);
        holder1.subcaterogy_title.setSelected(true);

        holder1.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) context).changeFragmentHome(new DetailItemFragment(resultFoundModels.get(position),cat_name,sub_cat_name), "detailItemPage");
            }
        });


        Glide.with(context).load(WebServiceUrl.IMAGEBASEURL+resultFoundModels.get(position).getImage()).
                into(holder1.subcategoryImage);


        holder1.btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilis.showToast(context, "Please select the variants first");
                ((MainActivity) context).changeFragmentHome(new DetailItemFragment(resultFoundModels.get(position),cat_name,sub_cat_name), "detailItemPage");
            }
        });
    }

    @Override
    public int getItemCount() {
        return resultFoundModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView subcategoryImage;
        AppCompatTextView subcaterogy_title;
        AppCompatButton btnCart;
        @BindView(R.id.root_layout)
        CoordinatorLayout rootLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            subcategoryImage = itemView.findViewById(R.id.iv_subcategory_item);
            btnCart = itemView.findViewById(R.id.btn_addtocart);
            subcaterogy_title = itemView.findViewById(R.id.tv_subcategory_name);
        }
    }
}
