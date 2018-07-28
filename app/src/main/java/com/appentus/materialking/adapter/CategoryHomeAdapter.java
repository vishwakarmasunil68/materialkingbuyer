package com.appentus.materialking.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.appentus.materialking.R;
import com.appentus.materialking.pojo.home.CategoryPOJO;
import com.appentus.materialking.webservice.WebServiceUrl;
import com.appentus.materialking.views.activity.MainActivity;
import com.appentus.materialking.views.fragments.ResultPageFragment;
import com.appentus.materialking.views.fragments.SingleCategorySelectedFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Hp on 1/17/2018.
 */

public class CategoryHomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<CategoryPOJO> categoryHomeModels;

    public CategoryHomeAdapter(Context context, List<CategoryPOJO> categoryHomeModels) {
        this.context = context;
        this.categoryHomeModels = categoryHomeModels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_home_category_layout, parent, false);

        return new CategoryHomeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder holder1 = (ViewHolder) holder;

        holder1.title.setText(categoryHomeModels.get(position).getName());
        //holder1.description.setText(categoryHomeModels.get(position).getDescription());
        //holder1.additionalData.setText(categoryHomeModels.get(position).getOtherData());


//        Glide.with(context).load(WebServiceUrl.IMAGEBASEURL + categoryHomeModels.get(position).getImage())
//                .placeholder(R.drawable.ic_default_profile_pic)
//                .error(R.drawable.ic_default_profile_pic)
//                .dontAnimate()
//                .into(holder1.ivCategoryMain);
        Picasso.with(context)
                .load(WebServiceUrl.IMAGEBASEURL + categoryHomeModels.get(position).getImage())
                .placeholder(R.drawable.ic_default_category)
                .error(R.drawable.ic_default_category)
                .into(holder1.ivCategoryMain);


        holder1.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (categoryHomeModels.get(position).getSubCategoryPOJOS().size() > 0) {
                    SingleCategorySelectedFragment singleCategorySelectedFragment = new SingleCategorySelectedFragment(categoryHomeModels.get(position));
//
                    Bundle mBundle = new Bundle();
                    mBundle.putString("catId", categoryHomeModels.get(position).getId());
                    singleCategorySelectedFragment.setArguments(mBundle);

//                    SubCategoryListFragment subCategoryListFragment=new SubCategoryListFragment();
//                    Bundle bundle=new Bundle();
//                    bundle.putSerializable("category",categoryHomeModels.get(position));

                    ((MainActivity) context).changeFragmentHome(singleCategorySelectedFragment, "singlecatfrag");

                } else {
                    ResultPageFragment singleCategorySelectedFragment = new ResultPageFragment();

                    Bundle mBundle = new Bundle();
                    mBundle.putString("catId", categoryHomeModels.get(position).getId());
                    singleCategorySelectedFragment.setArguments(mBundle);

                    ((MainActivity) context).changeFragmentHome(singleCategorySelectedFragment, "result");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryHomeModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCategoryMain;
        AppCompatTextView title, description, additionalData;

        public ViewHolder(View itemView) {
            super(itemView);
            ivCategoryMain = itemView.findViewById(R.id.iv_category_home);
            title = itemView.findViewById(R.id.tv_main_title);
            description = itemView.findViewById(R.id.tv_description);
            additionalData = itemView.findViewById(R.id.tv_additional_data);
        }
    }
}
