package com.appentus.materialking.views.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.appentus.materialking.R;
import com.appentus.materialking.Util.TagUtils;
import com.appentus.materialking.adapter.CategorySubHomeAdapter;
import com.appentus.materialking.pojo.home.CategoryPOJO;
import com.appentus.materialking.pojo.home.SubCategoryPOJO;
import com.appentus.materialking.webservice.WebServiceUrl;
import com.appentus.materialking.views.activity.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Hp on 1/17/2018.
 */

@SuppressLint("ValidFragment")
public class SingleCategorySelectedFragment extends Fragment {
    View view;
    @BindView(R.id.recycler_sub_category)
    RecyclerView recyclerSubCategory;

    @BindView(R.id.bannerImageView)
    ImageView bannerImageView;

    @BindView(R.id.catIconIm)
    ImageView catIconIm;

    @BindView(R.id.catNameTv)
    AppCompatTextView catNameTv;

    @BindView(R.id.totalItemsTv)
    AppCompatTextView totalItemsTv;


    CategorySubHomeAdapter mAdapter;
    List<SubCategoryPOJO> categorySubHomeModels = new ArrayList<>();
    Unbinder unbinder;

    String catId = "";
    CategoryPOJO categoryPOJO;

    public SingleCategorySelectedFragment(CategoryPOJO categoryPOJO) {
        this.categoryPOJO = categoryPOJO;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_single_category_selected, container, false);
        unbinder = ButterKnife.bind(this, view);

        ViewCompat.setNestedScrollingEnabled(recyclerSubCategory, false);

        recyclerSubCategory.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        mAdapter = new CategorySubHomeAdapter(getActivity(),this, categorySubHomeModels,categoryPOJO.getName(),categoryPOJO.getId());
        recyclerSubCategory.setAdapter(mAdapter);

        Log.d(TagUtils.getTag(),"In category model:-"+categoryPOJO.getName());
        catId = getArguments().getString("catId");

        prepareData();

        return view;
    }

    private void prepareData() {

        categorySubHomeModels.clear();

        if(categoryPOJO.getSubCategoryPOJOS()!=null&&categoryPOJO.getSubCategoryPOJOS().size()>0) {
            categorySubHomeModels.addAll(categoryPOJO.getSubCategoryPOJOS());

            Picasso.with(getActivity()).load(WebServiceUrl.IMAGEBASEURL + categoryPOJO.getBanner()).
                    into(bannerImageView);

            Picasso.with(getActivity()).load(WebServiceUrl.IMAGEBASEURL + categoryPOJO.getImage()).
                    into(catIconIm);

            catNameTv.setText(categoryPOJO.getName());
//            totalItemsTv.setText(totalItemsTvString);

            mAdapter.notifyDataSetChanged();

            totalItemsTv.setText(categoryPOJO.getSubCategoryPOJOS().size()+" items");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void showSubCategoryFilterFragment(SubCategoryPOJO subCategoryPOJO) {
        SubCategoryFilterFragment subCategoryFilterFragment=new SubCategoryFilterFragment(categoryPOJO,subCategoryPOJO);
        if(getActivity() instanceof MainActivity){
            MainActivity mainActivity= (MainActivity) getActivity();
            mainActivity.changeFragmentHome(subCategoryFilterFragment,"subCategoryFilterFragment");
        }
    }
}
