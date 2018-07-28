package com.appentus.materialking.views.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.appentus.materialking.R;
import com.appentus.materialking.Util.TagUtils;
import com.appentus.materialking.adapter.ResultFoundAdapter;
import com.appentus.materialking.helper.ItemOffsetDecoration;
import com.appentus.materialking.pojo.home.BrandPOJO;
import com.appentus.materialking.pojo.home.ColorPOJO;
import com.appentus.materialking.pojo.home.ProductPOJO;
import com.appentus.materialking.pojo.home.SizePOJO;
import com.appentus.materialking.pojo.home.TypePOJO;
import com.appentus.materialking.utility.ProgressView;
import com.appentus.materialking.webservice.WebServiceUrl;
import com.appentus.materialking.views.activity.MainActivity;
import com.appentus.materialking.webservice.WebServiceBase;
import com.appentus.materialking.webservice.WebServicesCallBack;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ResultPageFragment extends Fragment {
    View view;

    ResultFoundAdapter mAdapter;
    List<ProductPOJO> resultFoundModels = new ArrayList<>();
    @BindView(R.id.recycler_result_found)
    RecyclerView recyclerResultFound;
    @BindView(R.id.spinner_brand)
    Spinner spinner_brand;
    @BindView(R.id.spinner_type)
    Spinner spinner_type;
    @BindView(R.id.spinner_color)
    Spinner spinner_color;
    @BindView(R.id.spinner_size)
    Spinner spinner_size;
    @BindView(R.id.iv_filter)
    ImageView iv_filter;
    @BindView(R.id.transition)
    ViewGroup transition;
    @BindView(R.id.ll_filter)
    LinearLayout ll_filter;
    @BindView(R.id.iv_sub_image)
    ImageView iv_sub_image;
    @BindView(R.id.tv_sub_name)
    TextView tv_sub_name;
    @BindView(R.id.tv_description)
    TextView tv_description;
    @BindView(R.id.btn_submit)
    Button btn_submit;

    int numberOfColumns = 2;

    Unbinder unbinder;

    String catId = "";
    String catName = "";
    String subcatName = "";
    String sub_category_id = "";
    String sub_category_image = "";
    String sub_category_description = "";

    boolean is_first = true;

    String brand_id = "0";
    String type_id = "0";
    String color_id = "0";
    String size_id = "0";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_result_page, container, false);
        unbinder = ButterKnife.bind(this, view);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("12 Result Found");

        Log.d(TagUtils.getTag(),"fragment loaded");

        catId = getArguments().getString("catId");
        catName = getArguments().getString("catName");
        subcatName = getArguments().getString("subcatName");
        sub_category_id = getArguments().getString("sub_category_id");
        sub_category_image = getArguments().getString("sub_category_image");
        sub_category_description = getArguments().getString("sub_category_description");

        tv_sub_name.setText(subcatName);
        tv_description.setText(sub_category_image);
        Picasso.with(getActivity().getApplicationContext())
                .load(WebServiceUrl.IMAGEBASEURL + sub_category_image)
                .placeholder(R.drawable.ic_default_category)
                .error(R.drawable.ic_default_category)
                .into(iv_sub_image);

        ViewCompat.setNestedScrollingEnabled(recyclerResultFound, false);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getActivity(), R.dimen.item_offset);
        recyclerResultFound.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
        recyclerResultFound.addItemDecoration(itemDecoration);
        mAdapter = new ResultFoundAdapter(getActivity(), resultFoundModels, catName, subcatName);
        recyclerResultFound.setAdapter(mAdapter);

        registerStepFirst();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        iv_filter.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//            @Override
//            public void onClick(View v) {
//                TransitionManager.beginDelayedTransition(transition);
//                if (ll_filter.getVisibility() == View.VISIBLE) {
//                    ll_filter.setVisibility(View.GONE);
//                    iv_filter.setImageResource(R.drawable.ic_down);
//                } else {
//                    ll_filter.setVisibility(View.VISIBLE);
//                    iv_filter.setImageResource(R.drawable.ic_up);
//                }
//            }
//        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductListFragment productListFragment = new ProductListFragment();
                Bundle bundle = new Bundle();

                bundle.putString("brand_id", brand_id);
                bundle.putString("type_id", type_id);
                bundle.putString("color_id", color_id);
                bundle.putString("size_id", size_id);
                bundle.putString("sub_category_id", sub_category_id);
                bundle.putString("catId", catId);
                bundle.putString("catName", catName);
                bundle.putString("subcatName", subcatName);

                productListFragment.setArguments(bundle);
                if (getActivity() instanceof MainActivity) {
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.addFragmentHome(productListFragment, "productListFragment");
                }

            }
        });
    }

    private void registerStepFirst() {
        final ProgressView progressView = new ProgressView(getActivity());
        progressView.showLoader();

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
                        recyclerResultFound.setVisibility(View.VISIBLE);
                        JSONArray jsonArray = jsonObject.optJSONObject("results").optJSONArray("Products");


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

                        JSONArray BrandJSON = jsonObject.optJSONObject("results").optJSONObject("Filters").optJSONArray("Brand");
                        JSONArray ColorJSON = jsonObject.optJSONObject("results").optJSONObject("Filters").optJSONArray("Color");
                        JSONArray TypeJSON = jsonObject.optJSONObject("results").optJSONObject("Filters").optJSONArray("Type");
                        JSONArray SizeJSON = jsonObject.optJSONObject("results").optJSONObject("Filters").optJSONArray("Size");

                        if (is_first) {

                            is_first = false;

                            Type brandListType = new TypeToken<List<BrandPOJO>>() {
                            }.getType();
                            List<BrandPOJO> brandPOJOS = new Gson().fromJson(BrandJSON.toString(), brandListType);
                            Log.d(TagUtils.getTag(), "brand pojos:-" + brandPOJOS.toString());
                            if (brandPOJOS != null) {
                                setBrandSpinner(brandPOJOS);
                            }
                            Type typeListType = new TypeToken<List<TypePOJO>>() {
                            }.getType();
                            List<TypePOJO> typePOJOS = new Gson().fromJson(TypeJSON.toString(), typeListType);
                            if (typePOJOS != null) {
                                setTypeSpinner(typePOJOS);
                            }
                            Type colorType = new TypeToken<List<ColorPOJO>>() {
                            }.getType();
                            List<ColorPOJO> colorPOJOS = new Gson().fromJson(ColorJSON.toString(), colorType);
                            if (colorPOJOS != null) {
                                setColorSpinner(colorPOJOS);
                            }
                            Type sizeType = new TypeToken<List<SizePOJO>>() {
                            }.getType();
                            List<SizePOJO> sizePOJOS = new Gson().fromJson(SizeJSON.toString(), sizeType);
                            if (sizePOJOS != null) {
                                setSizeSpinner(sizePOJOS);
                            }
                        }
                    } else {
                        recyclerResultFound.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                progressView.hideLoader();
            }
        }, "GET_DATA", false).execute(WebServiceUrl.GET_PRODUCTS_BY_FILTERS);

    }

    List<BrandPOJO> brandPOJOS = new ArrayList<>();

    public void setBrandSpinner(final List<BrandPOJO> brandPOJOS) {

        if (brandPOJOS.size() > 0) {
            this.brandPOJOS.clear();
            this.brandPOJOS.addAll(brandPOJOS);
            List<String> stringList = new ArrayList<>();
            stringList.add("Select");
            for (BrandPOJO brandPOJO : brandPOJOS) {
                stringList.add(brandPOJO.getBrandName());
            }

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                    (getActivity(), android.R.layout.simple_spinner_item,
                            stringList); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                    .simple_spinner_dropdown_item);
            spinner_brand.setAdapter(spinnerArrayAdapter);
            spinner_brand.setVisibility(View.VISIBLE);

            spinner_brand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position != 0) {
                        brand_id = brandPOJOS.get(position - 1).getBrand_id();
//                        registerStepFirst();
                    } else {
                        brand_id = "0";
//                        registerStepFirst();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        } else {
            brand_id = "0";
            spinner_brand.setVisibility(View.GONE);
        }
    }

    List<TypePOJO> typePOJOS = new ArrayList<>();

    public void setTypeSpinner(final List<TypePOJO> typePOJOS) {
        if (typePOJOS.size() > 0) {
            this.typePOJOS.clear();
            this.typePOJOS.addAll(typePOJOS);
            List<String> stringList = new ArrayList<>();
            stringList.add("Select");
            for (TypePOJO typePOJO : typePOJOS) {
                stringList.add(typePOJO.getTypeName());
            }

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                    (getActivity(), android.R.layout.simple_spinner_item,
                            stringList); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                    .simple_spinner_dropdown_item);
            spinner_type.setAdapter(spinnerArrayAdapter);

            spinner_type.setVisibility(View.VISIBLE);

            spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position != 0) {
                        type_id = typePOJOS.get(position - 1).getType_id();
//                        registerStepFirst();
                    } else {
                        type_id = "0";
//                        registerStepFirst();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } else {
            type_id = "0";
            spinner_type.setVisibility(View.GONE);
        }
    }

    List<ColorPOJO> colorPOJOS = new ArrayList<>();

    public void setColorSpinner(final List<ColorPOJO> colorPOJOS) {
        if (colorPOJOS.size() > 0) {
            this.colorPOJOS.clear();
            this.colorPOJOS.addAll(colorPOJOS);
            List<String> stringList = new ArrayList<>();
            stringList.add("Select");
            for (ColorPOJO colorPOJO : colorPOJOS) {
                stringList.add(colorPOJO.getColorName());
            }

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                    (getActivity(), android.R.layout.simple_spinner_item,
                            stringList); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                    .simple_spinner_dropdown_item);
            spinner_color.setAdapter(spinnerArrayAdapter);
            spinner_color.setVisibility(View.VISIBLE);

            spinner_color.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position != 0) {
                        color_id = colorPOJOS.get(position - 1).getColor_id();
//                        registerStepFirst();
                    } else {
                        color_id = "0";
//                        registerStepFirst();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        } else {
            color_id = "0";
            spinner_color.setVisibility(View.GONE);
        }
    }

    List<SizePOJO> sizePOJOS = new ArrayList<>();

    public void setSizeSpinner(final List<SizePOJO> sizePOJOS) {
        if (sizePOJOS.size() > 0) {
            spinner_size.setVisibility(View.VISIBLE);
            this.sizePOJOS.clear();
            this.sizePOJOS.addAll(sizePOJOS);
            List<String> stringList = new ArrayList<>();
            stringList.add("Select");
            for (SizePOJO sizePOJO : sizePOJOS) {
                stringList.add(sizePOJO.getSizeName());
            }

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                    (getActivity(), android.R.layout.simple_spinner_item,
                            stringList); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                    .simple_spinner_dropdown_item);
            spinner_size.setAdapter(spinnerArrayAdapter);

            spinner_size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position != 0) {
                        size_id = sizePOJOS.get(position - 1).getSize_id();
//                        registerStepFirst();
                    } else {
                        size_id = "0";
//                        registerStepFirst();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        } else {
            size_id = "0";
            spinner_size.setVisibility(View.GONE);
        }
    }

//    private void registerStepFirst(final String catId) {
//        final ProgressView progressView = new ProgressView(getActivity());
//        progressView.showLoader();
//
//
//        MyApplication.getInstance().cancelPendingRequests("get_products_by_id");
//        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
//                WebServiceUrl.get_products_by_id, new Response.Listener<String>() {
//
//            @Override
//            public void onResponse(String response) {
//
//                Log.e("create_user",response);
//
//                progressView.hideLoader();
//                //  verifyOtp();
//                try {
//                    JSONObject mObject = new JSONObject(response);
//                    String status = mObject.getString("status");
//                    String message = mObject.getString("message");
//                    if(status.equalsIgnoreCase("1"))
//                    {
//
//                        resultFoundModels.clear();
//                        String results = mObject.getString("results");
//                        JSONArray mJsonArray =  new JSONArray(results);
//
//                        for (int i = 0 ; i < mJsonArray.length(); i++)
//                        {
//                            JSONObject mJsonObject =  mJsonArray.getJSONObject(i);
//
//                            ResultFoundModel model =  new ResultFoundModel();
//
//                            model.setName(mJsonObject.getString("name"));
//                            model.setBrand_id(mJsonObject.getString("brand_id"));
//                            model.setCategory_id(mJsonObject.getString("category_id"));
////                            model.setDefault_color_id(mJsonObject.getString("default_color_id"));
//                            model.setDefault_size_id(mJsonObject.getString("default_size_id"));
//                            model.setDefault_type_id(mJsonObject.getString("default_type_id"));
//                            model.setDescription(mJsonObject.getString("description"));
//                            model.setImage(mJsonObject.getString("image"));
//                            model.setProduct_id(mJsonObject.getString("product_id"));
//                            model.setProductNumber(mJsonObject.getString("productNumber"));
//
//
//                            resultFoundModels.add(model);
//                        }
//
//
//                        mAdapter.notifyDataSetChanged();
//
//
//
//                    }
//
//                    else
//                    {
//                        // user name or password wrong
//                        Toast.makeText(getActivity(),message,5000).show();
//                    }
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//
//                progressView.hideLoader();
//                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//                    MyApplication.showError(getActivity(),getString(R.string.noConnection),getString(R.string.checkInternet));
//
//                } else if (error instanceof AuthFailureError) {
//                    //TODO
//                    MyApplication.showError(getActivity(),getString(R.string.error),getString(R.string.tryAfterSomeTime));
//                } else if (error instanceof ServerError) {
//                    //TODO
//
//                    MyApplication.showError(getActivity(),getString(R.string.error),getString(R.string.tryAfterSomeTime));
//                } else if (error instanceof NetworkError) {
//                    //TODO
//                    MyApplication.showError(getActivity(),getString(R.string.error),getString(R.string.tryAfterSomeTime));
//
//                } else if (error instanceof ParseError) {
//                    //TODO
//                    MyApplication.showError(getActivity(),getString(R.string.error),getString(R.string.tryAfterSomeTime));
//                }
//
//            }
//        }){
//
//            /**
//             * Passing some request headers
//             * */
//            @Override
//            public Map<String, String> getParams()  {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("category_id", catId);
//
//
//                Log.e("POST DATA", headers.toString());
//
//
//
//                return headers;
//            }
//
//        };
//
//        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(100000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        MyApplication.getInstance().addToRequestQueue(jsonObjReq,"get_products_by_id");
//
//
//
//    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
