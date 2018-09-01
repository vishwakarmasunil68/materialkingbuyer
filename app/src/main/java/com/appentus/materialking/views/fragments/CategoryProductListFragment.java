package com.appentus.materialking.views.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appentus.materialking.R;
import com.appentus.materialking.Util.TagUtils;
import com.appentus.materialking.adapter.ProductVariantsAdapter;
import com.appentus.materialking.pojo.ResponseListPOJO;
import com.appentus.materialking.pojo.home.CategoryPOJO;
import com.appentus.materialking.pojo.home.ProductVariantSizePOJO;
import com.appentus.materialking.pojo.home.SubCategoryPOJO;
import com.appentus.materialking.pojo.product.FilterProductPOJO;
import com.appentus.materialking.webservice.WebServiceUrl;
import com.appentus.materialking.webservice.ResponseListCallback;
import com.appentus.materialking.webservice.WebServiceBaseResponseList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryProductListFragment extends Fragment{

    @BindView(R.id.rv_products)
    RecyclerView rv_products;

    CategoryPOJO categoryPOJO;
    SubCategoryPOJO subCategoryPOJO;
    String brand_id;
    String type_id;
    String color_id;
    String size_id;

    ProductVariantsAdapter mAdapter;
    List<FilterProductPOJO> productVariantSizePOJOS = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_category_products,container,false);
        ButterKnife.bind(this,view);

        categoryPOJO= (CategoryPOJO) getArguments().getSerializable("category");
        subCategoryPOJO= (SubCategoryPOJO) getArguments().getSerializable("subcategory");

        brand_id=getArguments().getString("brand_id","0");
        size_id=getArguments().getString("size_id","0");
        type_id=getArguments().getString("type_id","0");
        color_id=getArguments().getString("color_id","0");

        Log.d(TagUtils.getTag(),"brand_id:-"+brand_id);
        Log.d(TagUtils.getTag(),"size_id:-"+size_id);
        Log.d(TagUtils.getTag(),"type_id:-"+type_id);
        Log.d(TagUtils.getTag(),"color_id:-"+color_id);
        Log.d(TagUtils.getTag(),"category_id:-"+categoryPOJO.getId());
        Log.d(TagUtils.getTag(),"subcategory_id:-"+subCategoryPOJO.getId());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        rv_products.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        mAdapter = new ProductVariantsAdapter(getActivity(),this, productVariantSizePOJOS);
        rv_products.setAdapter(mAdapter);

        callProductAPI();
    }


    public void callProductAPI(){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("category_id",categoryPOJO.getId()));
        nameValuePairs.add(new BasicNameValuePair("sub_category_id",subCategoryPOJO.getId()));
        nameValuePairs.add(new BasicNameValuePair("brand_id",brand_id));
        nameValuePairs.add(new BasicNameValuePair("type_id",type_id));
        nameValuePairs.add(new BasicNameValuePair("color_id",color_id));
        nameValuePairs.add(new BasicNameValuePair("size_id",size_id));
        new WebServiceBaseResponseList<FilterProductPOJO>(nameValuePairs, getActivity(), new ResponseListCallback<FilterProductPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<FilterProductPOJO> responseListPOJO) {
                try{
                    if(responseListPOJO.isSuccess()){
                        productVariantSizePOJOS.clear();
                        productVariantSizePOJOS.addAll(responseListPOJO.getResultList());
                        mAdapter.notifyDataSetChanged();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },FilterProductPOJO.class,"GET_PRODUCTS",true).execute(WebServiceUrl.GET_FILTER_PRODUCTS);
    }
}
