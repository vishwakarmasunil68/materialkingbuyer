package com.appentus.materialking.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.appentus.materialking.R;
import com.appentus.materialking.model.MainCategories;
import com.appentus.materialking.model.SubCategories;
import com.appentus.materialking.webservice.WebServiceUrl;
import com.appentus.materialking.views.activity.MainActivity;
import com.appentus.materialking.views.fragments.ResultPageFragment;
import com.bumptech.glide.Glide;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import java.util.List;

/**
 * Created by Hp on 1/12/2018.
 */

public class CategoryDrawerAdapter extends ExpandableRecyclerViewAdapter<CategoryDrawerAdapter.ProductViewHolder, CategoryDrawerAdapter.SubProductViewHolder> {

    final Context context;
    public ExpandableGroup expandedGroup;

    public CategoryDrawerAdapter(Context context, List<? extends ExpandableGroup> groups) {
        super(groups);
        this.context = context;
    }

    @Override
    public ProductViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandable_recycler_group_layout, parent, false);

        return new CategoryDrawerAdapter.ProductViewHolder(view);
    }

    @Override
    public SubProductViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View views = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandable_recycler_child_layout, parent, false);

        return new CategoryDrawerAdapter.SubProductViewHolder(views);
    }

    @Override
    public void onBindGroupViewHolder(ProductViewHolder holder, int flatPosition, ExpandableGroup group) {
        ProductViewHolder holder1 = (ProductViewHolder) holder;




        holder1.setCategoryNow(group);



    }

    @Override
    public void onBindChildViewHolder(SubProductViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        //ChildViewHolder holder=(ChildViewHolder) holder2;

        SubCategories sub = ((MainCategories) group).getItems().get(childIndex);
        holder.setSubCateroryName(sub.getSubCategory());
        holder.setSubCateroryImage(sub.getSubCategoryImg());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // context.startActivity(new Intent(context,ResultPageFragment.class));


                if(MainActivity.drawerLayout!=null)
                {
                    MainActivity.drawerLayout.closeDrawer(Gravity.LEFT);
                }

                ((MainActivity)context).changeFragmentHome(new ResultPageFragment(),"result");
            }
        });


    }

    public  class ProductViewHolder extends GroupViewHolder {
        ImageView imageCategory;
        public  AppCompatTextView tvCcategotyName;
        public  LinearLayout groupLayout;
        public  ImageView icon;

        public ProductViewHolder(View itemView) {
            super(itemView);
            imageCategory = itemView.findViewById(R.id.iv_categoty_group);
            tvCcategotyName = itemView.findViewById(R.id.tv_categoty_group);
            groupLayout = itemView.findViewById(R.id.lin_drawer_group);
            icon=itemView.findViewById(R.id.iv_plus_minus);
        }

        public void setCategoryNow(ExpandableGroup mainCategory) {
            if (mainCategory instanceof MainCategories) {
                tvCcategotyName.setText(((MainCategories) mainCategory).getCategory());
                Glide.with(context).load(WebServiceUrl.IMAGEBASEURL+((MainCategories) mainCategory).getCategoryImg()).
                        into(imageCategory);

            }
        }

        @Override
        public void expand() {
            super.expand();


            groupLayout.setBackgroundColor(context.getResources().getColor(R.color.drawerSelectedBackground));
            tvCcategotyName.setTextColor(context.getResources().getColor(R.color.colorWhite));
            icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_remove));


        }

        @Override
        public void collapse() {
            super.collapse();
            groupLayout.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
            tvCcategotyName.setTextColor(context.getResources().getColor(R.color.colorBlack));
            icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_add));
        }







    }

    public class SubProductViewHolder extends ChildViewHolder {
        ImageView imageSubcategory;
        AppCompatTextView tv_subcategoty_name;

        public SubProductViewHolder(View itemView) {
            super(itemView);
            imageSubcategory = itemView.findViewById(R.id.iv_subcategoty_child);
            tv_subcategoty_name = itemView.findViewById(R.id.tv_subcategoty_child);
        }

        public void setSubCateroryName(String name) {
            tv_subcategoty_name.setText(name);
        }

        public void setSubCateroryImage(String subCategory) {

            Glide.with(context).load(WebServiceUrl.IMAGEBASEURL+subCategory).
                    into(imageSubcategory);
        }
    }

}
