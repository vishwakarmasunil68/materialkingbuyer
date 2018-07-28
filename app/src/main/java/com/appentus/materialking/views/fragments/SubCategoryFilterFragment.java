package com.appentus.materialking.views.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.appentus.materialking.R;
import com.appentus.materialking.pojo.ResponsePOJO;
import com.appentus.materialking.pojo.home.BrandPOJO;
import com.appentus.materialking.pojo.home.CategoryPOJO;
import com.appentus.materialking.pojo.home.ColorPOJO;
import com.appentus.materialking.pojo.home.FilterResultPOJO;
import com.appentus.materialking.pojo.home.SizePOJO;
import com.appentus.materialking.pojo.home.SubCategoryPOJO;
import com.appentus.materialking.pojo.home.TypePOJO;
import com.appentus.materialking.webservice.WebServiceUrl;
import com.appentus.materialking.views.activity.MainActivity;
import com.appentus.materialking.webservice.ResponseCallBack;
import com.appentus.materialking.webservice.WebServiceBaseResponse;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("ValidFragment")
public class SubCategoryFilterFragment extends Fragment {

    @BindView(R.id.ll_brand_filter)
    LinearLayout ll_brand_filter;
    @BindView(R.id.spinner_brand)
    Spinner spinner_brand;
    @BindView(R.id.ll_type_filter)
    LinearLayout ll_type_filter;
    @BindView(R.id.spinner_type)
    Spinner spinner_type;
    @BindView(R.id.ll_color_filter)
    LinearLayout ll_color_filter;
    @BindView(R.id.spinner_color)
    Spinner spinner_color;
    @BindView(R.id.ll_size_filter)
    LinearLayout ll_size_filter;
    @BindView(R.id.spinner_size)
    Spinner spinner_size;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.tv_sub_name)
    TextView tv_sub_name;
    @BindView(R.id.tv_description)
    TextView tv_description;

    CategoryPOJO categoryPOJO;
    SubCategoryPOJO subCategoryPOJO;


    List<BrandPOJO> brandPOJOS = new ArrayList<>();
    List<TypePOJO> typePOJOS = new ArrayList<>();
    List<SizePOJO> sizePOJOS = new ArrayList<>();
    List<ColorPOJO> colorPOJOS = new ArrayList<>();

    public SubCategoryFilterFragment(CategoryPOJO categoryPOJO, SubCategoryPOJO subCategoryPOJO) {
        this.categoryPOJO = categoryPOJO;
        this.subCategoryPOJO = subCategoryPOJO;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_sub_cat_filters, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        callFilterAPI();

        tv_description.setText(subCategoryPOJO.getName());
        tv_sub_name.setText(subCategoryPOJO.getName());


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("brand_id", getBrandSpinnerID(spinner_brand));
                bundle.putString("type_id", getTypeSpinnerID(spinner_type));
                bundle.putString("size_id", getSizeSpinnerID(spinner_size));
                bundle.putString("color_id", getColorSpinnerID(spinner_color));
                bundle.putSerializable("category", categoryPOJO);
                bundle.putSerializable("subcategory", subCategoryPOJO);

                CategoryProductListFragment categoryProductListFragment=new CategoryProductListFragment();
                categoryProductListFragment.setArguments(bundle);

                if(getActivity() instanceof MainActivity){
                    MainActivity mainActivity= (MainActivity) getActivity();
                    mainActivity.changeFragmentHome(categoryProductListFragment,"categoryProductListFragment");
                }

            }
        });

    }

    public String getBrandSpinnerID(Spinner spinner_brand) {
        if (spinner_brand.getSelectedItemPosition() != -1 && spinner_brand.getSelectedItemPosition() == 0) {
            return "0";
        } else {
            return brandPOJOS.get(spinner_brand.getSelectedItemPosition() - 1).getBrand_id();
        }
    }


    public String getTypeSpinnerID(Spinner spinner_type) {
        if (spinner_type.getSelectedItemPosition() != -1 && spinner_type.getSelectedItemPosition() == 0) {
            return "0";
        } else {
            return typePOJOS.get(spinner_type.getSelectedItemPosition() - 1).getType_id();
        }
    }


    public String getSizeSpinnerID(Spinner spinner_size) {
        if (spinner_size.getSelectedItemPosition() != -1 && spinner_size.getSelectedItemPosition() == 0) {
            return "0";
        } else {
            return sizePOJOS.get(spinner_size.getSelectedItemPosition() - 1).getSize_id();
        }
    }


    public String getColorSpinnerID(Spinner spinner_color) {
        if (spinner_color.getSelectedItemPosition() != -1 && spinner_color.getSelectedItemPosition() == 0) {
            return "0";
        } else {
            return colorPOJOS.get(spinner_color.getSelectedItemPosition() - 1).getColor_id();
        }
    }


    public void callFilterAPI() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("category_id", categoryPOJO.getId()));
        nameValuePairs.add(new BasicNameValuePair("sub_category_id", subCategoryPOJO.getId()));
        new WebServiceBaseResponse<FilterResultPOJO>(nameValuePairs, getActivity(), new ResponseCallBack<FilterResultPOJO>() {
            @Override
            public void onGetMsg(ResponsePOJO<FilterResultPOJO> responsePOJO) {
                try {
                    if (responsePOJO.isSuccess()) {
                        showBrandFilter(responsePOJO.getResult().getFileFilterPOJO().getBrandPOJOS());
                        showColorPOJOS(responsePOJO.getResult().getFileFilterPOJO().getColorPOJOS());
                        showSizePOJOS(responsePOJO.getResult().getFileFilterPOJO().getSizePOJOS());
                        showTypePOJOS(responsePOJO.getResult().getFileFilterPOJO().getTypePOJOS());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, FilterResultPOJO.class, "GET_FILTERS", true).execute(WebServiceUrl.GET_FILTERS);
    }


    public void showBrandFilter(List<BrandPOJO> brandPOJOS) {

        this.brandPOJOS.clear();
        if (brandPOJOS != null && brandPOJOS.size() > 0) {

            this.brandPOJOS.addAll(brandPOJOS);
            spinner_brand.setVisibility(View.VISIBLE);
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
        } else {
            spinner_brand.setVisibility(View.GONE);
        }
    }

    public void showTypePOJOS(List<TypePOJO> typePOJOS) {

        this.typePOJOS.clear();
        if (typePOJOS != null && typePOJOS.size() > 0) {

            this.typePOJOS.addAll(typePOJOS);
            spinner_type.setVisibility(View.VISIBLE);
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
        } else {
            spinner_type.setVisibility(View.GONE);
        }
    }

    public void showSizePOJOS(List<SizePOJO> sizePOJOS) {

        this.sizePOJOS.clear();
        if (sizePOJOS != null && sizePOJOS.size() > 0) {
            this.sizePOJOS.addAll(sizePOJOS);
            spinner_size.setVisibility(View.VISIBLE);
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
        } else {
            spinner_size.setVisibility(View.GONE);
        }
    }


    public void showColorPOJOS(List<ColorPOJO> colorPOJOS) {
        this.colorPOJOS.clear();
        if (colorPOJOS != null && colorPOJOS.size() > 0) {
            this.colorPOJOS.addAll(colorPOJOS);
            spinner_color.setVisibility(View.VISIBLE);
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
        } else {
            spinner_color.setVisibility(View.GONE);
        }
    }


}
