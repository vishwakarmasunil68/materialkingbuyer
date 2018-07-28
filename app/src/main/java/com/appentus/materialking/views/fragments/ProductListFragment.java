package com.appentus.materialking.views.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appentus.materialking.R;
import com.appentus.materialking.Util.ToastClass;
import com.appentus.materialking.adapter.ResultFoundAdapter;
import com.appentus.materialking.helper.ItemOffsetDecoration;
import com.appentus.materialking.pojo.home.ProductPOJO;
import com.appentus.materialking.webservice.WebServiceUrl;
import com.appentus.materialking.webservice.WebServiceBase;
import com.appentus.materialking.webservice.WebServicesCallBack;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductListFragment extends Fragment {

    String brand_id = "";
    String type_id = "";
    String color_id = "";
    String size_id = "";
    String sub_category_id = "";
    String catName = "";
    String subcatName = "";
    String catId = "";

    ResultFoundAdapter mAdapter;
    List<ProductPOJO> resultFoundModels = new ArrayList<>();
    @BindView(R.id.rv_products)
    RecyclerView rv_products;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_product_list, container, false);
        ButterKnife.bind(this, view);

        brand_id = getArguments().getString("brand_id");
        type_id = getArguments().getString("type_id");
        color_id = getArguments().getString("color_id");
        size_id = getArguments().getString("size_id");
        sub_category_id = getArguments().getString("sub_category_id");
        catId = getArguments().getString("catId");
        catName = getArguments().getString("catName");
        subcatName = getArguments().getString("subcatName");

        ViewCompat.setNestedScrollingEnabled(rv_products, false);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getActivity(), R.dimen.item_offset);
        rv_products.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rv_products.addItemDecoration(itemDecoration);
        mAdapter = new ResultFoundAdapter(getActivity(), resultFoundModels, catName, subcatName);
        rv_products.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        callProductListAPI();

    }

    public void callProductListAPI() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("category_id", catId));
        nameValuePairs.add(new BasicNameValuePair("brand_id", String.valueOf(brand_id)));
        nameValuePairs.add(new BasicNameValuePair("type_id", String.valueOf(type_id)));
        nameValuePairs.add(new BasicNameValuePair("color_id", String.valueOf(color_id)));
        nameValuePairs.add(new BasicNameValuePair("size_id", String.valueOf(size_id)));

        if (sub_category_id != null && sub_category_id.length() > 0) {
            nameValuePairs.add(new BasicNameValuePair("sub_category_id", sub_category_id));
        }

        new WebServiceBase(nameValuePairs, getActivity(), new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equals("1")) {
                        rv_products.setVisibility(View.VISIBLE);
                        JSONArray jsonArray = jsonObject.optJSONObject("results").optJSONArray("Products");

                        if(jsonArray.length()>0) {
                            List<ProductPOJO> productPOJOS = new ArrayList<>();

                            for (int i = 0; i < jsonArray.length(); i++) {
                                Gson gson = new Gson();
                                JSONObject jsonObject1 = jsonArray.optJSONObject(i);
                                ProductPOJO productPOJO = gson.fromJson(jsonObject1.optJSONObject("product").toString(), ProductPOJO.class);
                                productPOJOS.add(productPOJO);
                            }

                            resultFoundModels.clear();
                            resultFoundModels.addAll(productPOJOS);
                            mAdapter.notifyDataSetChanged();
                        }else{
                            ToastClass.showShortToast(getActivity().getApplicationContext(),"No Products Found");
                            rv_products.setVisibility(View.GONE);
                        }
                    } else {
                        rv_products.setVisibility(View.GONE);
                        ToastClass.showShortToast(getActivity().getApplicationContext(),"No Products Found");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },"CALL_API",true).execute(WebServiceUrl.GET_PRODUCTS_BY_FILTERS);

    }

}
