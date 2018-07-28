package com.appentus.materialking.views.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appentus.materialking.R;
import com.appentus.materialking.adapter.CategorySubHomeAdapter;
import com.appentus.materialking.pojo.home.CategoryPOJO;
import com.appentus.materialking.pojo.home.SubCategoryPOJO;
import com.appentus.materialking.webservice.WebServiceUrl;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubCategoryListFragment extends Fragment {

    @BindView(R.id.rv_sub_cat)
    RecyclerView rv_sub_cat;
    @BindView(R.id.tv_cat_name)
    TextView tv_cat_name;
    @BindView(R.id.iv_cat)
    ImageView iv_cat;

    CategoryPOJO categoryPOJO;
    CategorySubHomeAdapter mAdapter;
    List<SubCategoryPOJO> categorySubHomeModels = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_sub_category_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            categoryPOJO = (CategoryPOJO) bundle.getSerializable("category");
        }

//        rv_sub_cat.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
//        mAdapter = new CategorySubHomeAdapter(getActivity(), categorySubHomeModels, categoryPOJO.getName(), categoryPOJO.getId());
//        rv_sub_cat.setAdapter(mAdapter);


        prepareData();
    }

    public void prepareData() {

        categorySubHomeModels.clear();

        if (categoryPOJO.getSubCategoryPOJOS() != null && categoryPOJO.getSubCategoryPOJOS().size() > 0) {
            categorySubHomeModels.addAll(categoryPOJO.getSubCategoryPOJOS());
            Glide.with(getActivity()).load(WebServiceUrl.IMAGEBASEURL + categoryPOJO.getImage()).
                    into(iv_cat);

            tv_cat_name.setText(categoryPOJO.getName());
//            totalItemsTv.setText(totalItemsTvString);

            mAdapter.notifyDataSetChanged();

//            totalItemsTv.setText(categoryPOJO.getSubCategoryPOJOS().size() + " items");
        }
    }

}
