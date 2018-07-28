package com.appentus.materialking.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appentus.materialking.R;
import com.appentus.materialking.adapter.CategoryHomeAdapter;
import com.appentus.materialking.pojo.home.CategoryPOJO;
import com.appentus.materialking.webservice.WebServiceUrl;
import com.appentus.materialking.views.activity.MainActivity;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Hp on 1/17/2018.
 */

public class FragmentHome extends Fragment {
    View view;

    public  static SliderLayout slider;
    Unbinder unbinder;

    @BindView(R.id.recycler_home_category)
    RecyclerView recyclerHomeCategory;

    public static CategoryHomeAdapter mAdapter;
    public static List<CategoryPOJO> categoryHomeAdapters;
    int numberOfColumns=2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);

        categoryHomeAdapters=new ArrayList<>();

        slider = view.findViewById(R.id.slider);

        infiniteSlider(getActivity());

        ViewCompat.setNestedScrollingEnabled(recyclerHomeCategory,false);
        recyclerHomeCategory.setLayoutManager(new GridLayoutManager(getActivity(),numberOfColumns));
        recyclerHomeCategory.addItemDecoration(new DividerItemDecoration(getActivity(),0));
        recyclerHomeCategory.addItemDecoration(new DividerItemDecoration(getActivity(),1));
        mAdapter = new CategoryHomeAdapter(getActivity(), categoryHomeAdapters);
        recyclerHomeCategory.setAdapter(mAdapter);

        prepareData();


        return view;
    }

    public static void prepareData() {
        categoryHomeAdapters.clear();

        if(MainActivity.categoryModelArrayList!=null && MainActivity.categoryModelArrayList.size()>0)
        {
            categoryHomeAdapters.addAll(MainActivity.categoryModelArrayList);
        }

        mAdapter.notifyDataSetChanged();
    }

    public static  void infiniteSlider(Context mContext) {
        HashMap<String, String> file_maps = new HashMap<String, String>();

        if(MainActivity.bannersModelArrayList!=null)
        {
            if(MainActivity.bannersModelArrayList.size()>0)
            {
                for (int i = 0 ; i < MainActivity.bannersModelArrayList.size();i++)
                {
                    file_maps.put(""+i, MainActivity.bannersModelArrayList.get(i).getImage());
                    Log.e("image",MainActivity.bannersModelArrayList.get(i).getImage());
                }
            }
        }



        for (String name : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(mContext);
            // initialize a SliderLayout
            textSliderView
                    //  .description(name)
                    .image(WebServiceUrl.IMAGEBASEURL+file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);

            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra", name);
            slider.addSlider(textSliderView);
        }
        slider.setPresetTransformer(SliderLayout.Transformer.Default);
        slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        slider.setDuration(4000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
