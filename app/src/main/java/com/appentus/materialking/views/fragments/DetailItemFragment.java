package com.appentus.materialking.views.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.appentus.materialking.R;
import com.appentus.materialking.Util.TagUtils;
import com.appentus.materialking.Util.ToastClass;
import com.appentus.materialking.adapter.ProductColorAdapter;
import com.appentus.materialking.adapter.ProductVariantSizesAdapter;
import com.appentus.materialking.adapter.SelectColorAdapter;
import com.appentus.materialking.model.SelectColorModel;
import com.appentus.materialking.pojo.home.ProductPOJO;
import com.appentus.materialking.pojo.home.VariationPOJO;
import com.appentus.materialking.pojo.home.VariationSizePOJO;
import com.appentus.materialking.utility.MyApplication;
import com.appentus.materialking.utility.PrefsData;
import com.appentus.materialking.webservice.WebServiceUrl;
import com.appentus.materialking.views.activity.MainActivity;
import com.appentus.materialking.views.activity.ProductImageViewActivity;
import com.appentus.materialking.webservice.WebServiceBase;
import com.appentus.materialking.webservice.WebServicesCallBack;
import com.bumptech.glide.Glide;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Hp on 1/18/2018.
 */

@SuppressLint("ValidFragment")
public class DetailItemFragment extends Fragment {
    View view;
    Unbinder unbinder;

    ArrayList<String> selectBrand = new ArrayList<String>();
    ArrayList<String> selectType = new ArrayList<String>();
    ArrayList<String> selectSize = new ArrayList<String>();

    @BindView(R.id.actv_title)
    AppCompatTextView actv_title;
    @BindView(R.id.iv_product_image)
    ImageView iv_product_image;
    @BindView(R.id.actv_description)
    TextView actv_description;
    @BindView(R.id.actv_brand)
    AppCompatTextView actv_brand;
    @BindView(R.id.btn_item_add_to_cart)
    Button btnItemAddToCart;
    @BindView(R.id.tv_trace)
    TextView tv_trace;
    @BindView(R.id.ll_type)
    LinearLayout ll_type;
    @BindView(R.id.tv_type)
    TextView tv_type;
    //    @BindView(R.id.rv_sizes)
//    RecyclerView rv_sizes;
    @BindView(R.id.rv_colors)
    RecyclerView rv_colors;
    @BindView(R.id.et_quantity)
    EditText et_quantity;
    @BindView(R.id.spinner_color)
    Spinner spinner_color;


    @BindView(R.id.sp_select_brand)
    Spinner spSelectBrand;
    @BindView(R.id.sp_select_type)
    Spinner spSelectType;
    @BindView(R.id.spinner_select_size)
    Spinner spinnerSelectSize;
    @BindView(R.id.recycler_select_color)
    RecyclerView recyclerSelectColor;
    SelectColorAdapter mAdapter;
    List<SelectColorModel> selectColorModels = new ArrayList<>();

    ProductPOJO productPOJO;

    String category_name = "";
    String subcategory_name = "";

    public DetailItemFragment(ProductPOJO productPOJO, String category_name, String subcategory_name) {
        this.productPOJO = productPOJO;
        this.category_name = category_name;
        this.subcategory_name = subcategory_name;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_detail_item, container, false);
        unbinder = ButterKnife.bind(this, view);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("ITEM DETAILS");

        fillDataSpinnerBrand();
        fillDataSpinnerType();
//        fillDataSpinnerSize();


        recyclerSelectColor.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mAdapter = new SelectColorAdapter(getActivity(), selectColorModels);
        recyclerSelectColor.setAdapter(mAdapter);

//        attachAdapter();
        attachColorAdapter();
        prepareData();
        setValues();
        btnItemAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callAddToCartAPI();
            }
        });

        return view;
    }

    public void setValues() {
        if (productPOJO != null) {
            actv_title.setText(productPOJO.getName());
            setImageVIew(WebServiceUrl.IMAGEBASEURL + productPOJO.getImage());

            actv_description.setText(productPOJO.getDescription());

            tv_trace.setText(category_name + " > " + subcategory_name + " > " + productPOJO.getBrandName());

            if (productPOJO.getTypeId().equals("0")) {
                ll_type.setVisibility(View.GONE);
            } else {
                tv_type.setText(productPOJO.getTypeName());
            }

            actv_brand.setText(productPOJO.getBrandName());


            if (productPOJO.getVariationPOJOS().size() > 0) {

                for (VariationPOJO variationPOJO : productPOJO.getVariationPOJOS()) {
                    for (VariationSizePOJO variationSizePOJO : variationPOJO.getVariationSizePOJOS()) {
                        productVariantSizes.add(variationSizePOJO);
                        productSizesSet.add(variationSizePOJO.getSizeName());
                    }
                }
//                productVariantSizesAdapter.notifyDataSetChanged();

                for (VariationPOJO variationPOJO : productPOJO.getVariationPOJOS()) {
                    productColorList.add(variationPOJO);
                    productVariationList.add(variationPOJO);
                }


//                fillDataSpinnerSize(new ArrayList<String>(productSizesSet));

                productColorAdapter.notifyDataSetChanged();

            } else {

            }

            iv_product_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), ProductImageViewActivity.class);
                    intent.putExtra("url", image_url);
                    startActivity(intent);
                }
            });

            setVariationSpinner();
            Log.d(TagUtils.getTag(), "spinner size position:-" + spinnerSelectSize.getSelectedItemPosition());
        }
    }

    List<VariationPOJO> productVariationList = new ArrayList<>();

    public void setVariationSpinner() {
        if (productColorList.size() > 0) {
            List<String> productColors = new ArrayList<>();
            productColors.add("Select Color");
            for (VariationPOJO variationPOJO : productVariationList) {
                productColors.add(variationPOJO.getColorName());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, productColors);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_color.setAdapter(adapter);

            spinner_color.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    variationPOJO = null;
                    if (position != 0) {
                        variationPOJO = productVariationList.get(position - 1);
                    } else {
                        variationPOJO = null;
                    }
                    showVariationSizes(variationPOJO);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spinner_color.setSelection(1);
        }
    }

    VariationSizePOJO selecVariationSizePOJO;

    public void showVariationSizes(final VariationPOJO variationPOJO) {
        List<String> productSizes = new ArrayList<>();
        if (variationPOJO == null) {
            selecVariationSizePOJO=null;
            setImageVIew(WebServiceUrl.IMAGEBASEURL + productPOJO.getImage());
        } else {
            for (VariationSizePOJO variationSizePOJO : variationPOJO.getVariationSizePOJOS()) {
                productSizes.add(variationSizePOJO.getSizeName());
            }
            showImage(variationPOJO);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, productSizes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSelectSize.setAdapter(adapter);

        spinnerSelectSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selecVariationSizePOJO=variationPOJO.getVariationSizePOJOS().get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    String image_url = "";

    public void setImageVIew(String url) {
        this.image_url = url;
        Glide.with(getActivity().getApplicationContext())
                .load(url)
                .into(iv_product_image);
    }

    VariationPOJO variationPOJO;

    public void showImage(VariationPOJO variationPOJO) {
        this.variationPOJO = variationPOJO;
//        setImageVIew(WebServiceUrl.IMAGEBASEURL + variationSizePOJO.getImage());
        String image = getImageFromVariation(variationPOJO);

        if (image.length() > 0) {
            Glide.with(getActivity().getApplicationContext())
                    .load(WebServiceUrl.IMAGEBASEURL + image)
                    .into(iv_product_image);
        } else {
            iv_product_image.setBackgroundColor(Color.parseColor(variationPOJO.getColorCode()));
        }
    }

    public void showAdapterImage(VariationPOJO variationPOJO,int position) {
        this.variationPOJO = variationPOJO;
//        setImageVIew(WebServiceUrl.IMAGEBASEURL + variationSizePOJO.getImage());
        String image = getImageFromVariation(variationPOJO);
        spinner_color.setSelection(position+1);
        if (image.length() > 0) {
            Glide.with(getActivity().getApplicationContext())
                    .load(WebServiceUrl.IMAGEBASEURL + image)
                    .into(iv_product_image);
        } else {
            iv_product_image.setBackgroundColor(Color.parseColor(variationPOJO.getColorCode()));
        }
    }

    public String getImageFromVariation(VariationPOJO variationPOJO) {
        for (VariationSizePOJO variationSizePOJO : variationPOJO.getVariationSizePOJOS()) {
            return variationSizePOJO.getImage();
        }
        return "";
    }

    List<VariationSizePOJO> productVariantSizes = new ArrayList<>();
    Set<String> productSizesSet = new HashSet<>();
    ProductVariantSizesAdapter productVariantSizesAdapter;

//    public void attachAdapter() {
//
//        productVariantSizesAdapter = new ProductVariantSizesAdapter(getActivity(), this, productVariantSizes);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//        rv_sizes.setHasFixedSize(true);
//        rv_sizes.setAdapter(productVariantSizesAdapter);
//        rv_sizes.setLayoutManager(linearLayoutManager);
//        rv_sizes.setItemAnimator(new DefaultItemAnimator());
//    }

    List<VariationPOJO> productColorList = new ArrayList<>();
    ProductColorAdapter productColorAdapter;

    public void attachColorAdapter() {

        productColorAdapter = new ProductColorAdapter(getActivity(), this, productColorList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rv_colors.setHasFixedSize(true);
        rv_colors.setAdapter(productColorAdapter);
        rv_colors.setLayoutManager(linearLayoutManager);
        rv_colors.setItemAnimator(new DefaultItemAnimator());
    }


    private void fillDataSpinnerBrand() {
        selectBrand.add("TMT");
        selectBrand.add("Zindal");
        selectBrand.add("Tata");
        selectBrand.add("Zigma");
        selectBrand.add("Danny");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, selectBrand);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSelectBrand.setAdapter(adapter);
    }

    private void fillDataSpinnerType() {
        selectType.add("Straight");
        selectType.add("Rounded");
        selectType.add("Tilted");
        selectType.add("Curved");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, selectType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSelectType.setAdapter(adapter);
    }

    private void fillDataSpinnerSize(List<String> selectSizes) {

    }

    private void callAddToCartAPI() {

        if (spinner_color.getSelectedItemPosition() != 0 || spinner_color.getSelectedItemPosition() != -1) {
            if (spinnerSelectSize.getSelectedItemPosition() != -1) {
                if ( selecVariationSizePOJO!=null){
                    if (et_quantity.getText().toString().length() > 0) {
                        try {

                            int entered_quantity = Integer.parseInt(et_quantity.getText().toString());
                            int available_quantity = Integer.parseInt(selecVariationSizePOJO.getQty());
                            if (entered_quantity <= available_quantity) {

                                ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
                                nameValuePairs.add(new BasicNameValuePair("product_id", productPOJO.getProductId()));
                                nameValuePairs.add(new BasicNameValuePair("user_id", MyApplication.readStringPref(PrefsData.PREF_USERID)));
                                nameValuePairs.add(new BasicNameValuePair("variation_id", selecVariationSizePOJO.getVariationId()));
                                nameValuePairs.add(new BasicNameValuePair("color_id", getColorID(selecVariationSizePOJO.getVariationId())));
                                nameValuePairs.add(new BasicNameValuePair("product_variation_id", selecVariationSizePOJO.getProductVariationId()));
                                nameValuePairs.add(new BasicNameValuePair("size_id", selecVariationSizePOJO.getSizeId()));
                                nameValuePairs.add(new BasicNameValuePair("qty", et_quantity.getText().toString()));
                                new WebServiceBase(nameValuePairs, getActivity(), new WebServicesCallBack() {
                                    @Override
                                    public void onGetMsg(String apicall, String response) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            if (jsonObject.optInt("status") == 1) {
                                                openConfirmationDialog();
                                            } else {
                                                ToastClass.showShortToast(getActivity().getApplicationContext(), jsonObject.optString("message"));
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, "CALL_CART_ADD_API", true).execute(WebServiceUrl.ADD_PRODUCT_TO_CART);
                            } else {
                                ToastClass.showShortToast(getActivity().getApplicationContext(), "only " + selecVariationSizePOJO.getQty() + " quantity available.");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastClass.showShortToast(getActivity().getApplicationContext(), "Please Enter proper quantity");
                        }
                    } else {
                        ToastClass.showShortToast(getActivity().getApplicationContext(), "Please Enter Quantity");
                    }
                } else{
                    ToastClass.showShortToast(getActivity().getApplicationContext(), "Please Select Variant");
                }
            } else {
                ToastClass.showShortToast(getActivity().getApplicationContext(), "Please Select the Variation Size");
            }
        } else {
            ToastClass.showShortToast(getActivity().getApplicationContext(), "Please Select the color variation");
        }
    }

    private String getColorID(String selected_variant_id) {
        if (productPOJO.getVariationPOJOS() != null) {
            for (VariationPOJO variationPOJO : productPOJO.getVariationPOJOS()) {
                if (variationPOJO.getVariationId().equals(selected_variant_id)) {
                    return variationPOJO.getColorId();
                }
            }
        }
        return "";
    }

    private void openConfirmationDialog() {
        MaterialStyledDialog dialog = new MaterialStyledDialog.Builder(getActivity())
                .setTitle("Successful")
                .setDescription("Your Item has been added to the cart. You can review edit, delete and place the order from the cart")
                .setIcon(R.drawable.thumbs_up)
                .withDarkerOverlay(true)
                .withIconAnimation(true)
                .setPositiveText("Continue Shopping")
                .setCancelable(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        ((MainActivity) getActivity()).changeFragmentHome(new CartFragment(), "result");
                    }
                })

                .build();
        dialog.show();

    }

    private void prepareData() {
        selectColorModels.clear();

        SelectColorModel model = new SelectColorModel("#000000", false);
        selectColorModels.add(model);

        model = new SelectColorModel("#ffffff", false);
        selectColorModels.add(model);

        model = new SelectColorModel("#a2a2a2", false);
        selectColorModels.add(model);

        model = new SelectColorModel("#e8e8e8", false);
        selectColorModels.add(model);

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
