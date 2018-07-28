package com.appentus.materialking.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.appentus.materialking.R;
import com.appentus.materialking.pojo.home.SubCategoryPOJO;
import com.appentus.materialking.views.activity.MainActivity;
import com.appentus.materialking.views.fragments.ResultPageFragment;
import com.appentus.materialking.views.fragments.SingleCategorySelectedFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hp on 1/17/2018.
 */

public class CategorySubHomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<SubCategoryPOJO> categorySubHomeModels=new ArrayList<>();
    String category_name;
    String cat_id;
    Fragment fragment;

    public CategorySubHomeAdapter(Context context, Fragment fragment, List<SubCategoryPOJO> categorySubHomeModels, String category_name, String cat_id) {
        this.context = context;
        this.fragment=fragment;
        this.categorySubHomeModels = categorySubHomeModels;
        this.category_name=category_name;
        this.cat_id=cat_id;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_category_sub_home_layout,parent,false);
        return new CategorySubHomeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder holder1=(ViewHolder) holder;
        holder1.productName.setText(categorySubHomeModels.get(position).getName());
        holder1.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(fragment instanceof SingleCategorySelectedFragment){
                    SingleCategorySelectedFragment singleCategorySelectedFragment= (SingleCategorySelectedFragment) fragment;
                    singleCategorySelectedFragment.showSubCategoryFilterFragment(categorySubHomeModels.get(position));
                }

//                ResultPageFragment singleCategorySelectedFragment =  new ResultPageFragment();
//
//                Bundle mBundle =  new Bundle();
//                mBundle.putString("catId",cat_id);
//                mBundle.putString("catName",category_name);
//                mBundle.putString("subcatName",categorySubHomeModels.get(position).getName());
//                mBundle.putString("sub_category_id",categorySubHomeModels.get(position).getId());
//                mBundle.putString("sub_category_image",categorySubHomeModels.get(position).getImage());
//                mBundle.putString("sub_category_description",categorySubHomeModels.get(position).getName());
//                singleCategorySelectedFragment.setArguments(mBundle);
//
//                ((MainActivity)context).addFragmentHome(singleCategorySelectedFragment,"result");
            }
        });

    }

    @Override
    public int getItemCount() {
        return categorySubHomeModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView productImage;
        AppCompatTextView productName;
        public ViewHolder(View itemView) {
            super(itemView);
            productImage=itemView.findViewById(R.id.iv_category_sub_home);
            productName=itemView.findViewById(R.id.tv_category_sub_home_title);
        }
    }
}
