package com.appentus.materialking.views.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.appentus.materialking.R;
import com.appentus.materialking.Util.ToastClass;
import com.appentus.materialking.adapter.SellerTypeAdapter;
import com.appentus.materialking.pojo.ResponseListPOJO;
import com.appentus.materialking.pojo.SellerTypePOJO;
import com.appentus.materialking.pojo.location.CityPOJO;
import com.appentus.materialking.pojo.location.StatesPOJO;
import com.appentus.materialking.utility.MyApplication;
import com.appentus.materialking.utility.PrefsData;
import com.appentus.materialking.views.activity.MainActivity;
import com.appentus.materialking.webservice.ResponseListCallback;
import com.appentus.materialking.webservice.WebServiceBase;
import com.appentus.materialking.webservice.WebServiceBaseResponseList;
import com.appentus.materialking.webservice.WebServiceUrl;
import com.appentus.materialking.webservice.WebServicesCallBack;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScheduleOrderFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    @BindView(R.id.rv_seller_type)
    RecyclerView rv_seller_type;
    @BindView(R.id.iv_calendar)
    ImageView iv_calendar;
    @BindView(R.id.et_shipping_address)
    EditText et_shipping_address;
    @BindView(R.id.et_date_of_delivery)
    EditText et_date_of_delivery;
    @BindView(R.id.btn_place_order)
    Button btn_place_order;
    @BindView(R.id.spinner_state)
    Spinner spinner_state;
    @BindView(R.id.spinner_city)
    Spinner spinner_city;

    List<SellerTypePOJO> sellerTypePOJOS = new ArrayList<>();
    String is_recommended = "0";

    List<StatesPOJO> statesPOJOS = new ArrayList<>();
    List<CityPOJO> cityPOJOS = new ArrayList<>();

    String selected_state_id = "";
    String selected_city_id = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_schedule_order, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        is_recommended = getArguments().getString("is_recommended", "0");

        attachSellerTypeAdapter();
        iv_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        ScheduleOrderFragment.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getActivity().getFragmentManager(), "Date of delivery");
            }
        });
        getSellerType();
        getALLStates();
        btn_place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_shipping_address.getText().toString().length() > 0
                        && et_date_of_delivery.getText().toString().length() > 0
                        ) {

                    boolean is_selected = false;
                    for (SellerTypePOJO sellerTypePOJO : sellerTypePOJOS) {
                        if (sellerTypePOJO.isIs_checked()) {
                            is_selected = true;
                        }
                    }

                    if (is_selected) {
                        callOrderPlaceAPI();
                    } else {
                        ToastClass.showShortToast(getActivity().getApplicationContext(), "Select atleast 1 Seller Type");
                    }
                } else {
                    ToastClass.showShortToast(getActivity().getApplicationContext(), "Please Enter Fields Properly");
                }
            }
        });
    }

    public String selectedSellerTypes(){
        List<SellerTypePOJO> selectedSellerType=new ArrayList<>();
        for(SellerTypePOJO sellerTypePOJO:sellerTypePOJOS){
            if(sellerTypePOJO.isIs_checked()){
                selectedSellerType.add(sellerTypePOJO);
            }
        }

        String selected_seller_types="";
        for(int i=0;i<selectedSellerType.size();i++){
            if((selectedSellerType.size()-1)==i){
                selected_seller_types+=selectedSellerType.get(i).getSellerTypeId();
            }else{
                selected_seller_types+=selectedSellerType.get(i).getSellerTypeId()+",";
            }
        }

        return selected_seller_types;
    }

    public void callOrderPlaceAPI() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", MyApplication.readStringPref(PrefsData.PREF_USERID)));
        nameValuePairs.add(new BasicNameValuePair("recommend_allow", is_recommended));
        nameValuePairs.add(new BasicNameValuePair("expected_date_of_delivery", et_date_of_delivery.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("state", selected_state_id));
        nameValuePairs.add(new BasicNameValuePair("city", selected_city_id));
        int count = 0;
//        for (SellerTypePOJO sellerTypePOJO : sellerTypePOJOS) {
//            if (sellerTypePOJO.isIs_checked()) {
//                nameValuePairs.add(new BasicNameValuePair("for_seller_types[" + count + "]", sellerTypePOJO.getSellerTypeId()));
//            }
//        }
        nameValuePairs.add(new BasicNameValuePair("for_seller_types",selectedSellerTypes()));
        new WebServiceBase(nameValuePairs, getActivity(), new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optInt("status") == 1) {
                        if (getActivity() instanceof MainActivity) {
                            MainActivity mainActivity = (MainActivity) getActivity();
                            mainActivity.changeFragmentHome(new FragmentHome(), "FragmentHome");
                        }
                    }
                    ToastClass.showShortToast(getActivity(), jsonObject.optString("message"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "CALL_ORDER_PLACE_API", true).execute(WebServiceUrl.GENERATE_MY_ORDER);
    }

    public void getSellerType() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        new WebServiceBaseResponseList<SellerTypePOJO>(nameValuePairs, getActivity(), new ResponseListCallback<SellerTypePOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<SellerTypePOJO> responseListPOJO) {
                sellerTypePOJOS.clear();
                try {
                    if (responseListPOJO.isSuccess()) {
                        sellerTypePOJOS.addAll(responseListPOJO.getResultList());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                sellerTypeAdapter.notifyDataSetChanged();
            }
        }, SellerTypePOJO.class, "GET_SELLER_TYPE", true).execute(WebServiceUrl.GET_SELLER_TYPES);
    }

    SellerTypeAdapter sellerTypeAdapter;

    public void attachSellerTypeAdapter() {

        sellerTypeAdapter = new SellerTypeAdapter(getActivity(), sellerTypePOJOS);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_seller_type.setHasFixedSize(true);
        rv_seller_type.setAdapter(sellerTypeAdapter);
        rv_seller_type.setLayoutManager(linearLayoutManager);
        rv_seller_type.setItemAnimator(new DefaultItemAnimator());
    }


    public void getALLStates() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
//        nameValuePairs.add(new BasicNameValuePair("",""));
        new WebServiceBaseResponseList<StatesPOJO>(nameValuePairs, getActivity(), new ResponseListCallback<StatesPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<StatesPOJO> responseListPOJO) {
                try {
                    statesPOJOS.addAll(responseListPOJO.getResultList());
                    List<String> stringList = new ArrayList<>();
                    for (StatesPOJO statesPOJO : statesPOJOS) {
                        stringList.add(statesPOJO.getStateName());
                    }

                    setStatesSpinnerAdapter(spinner_state, stringList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, StatesPOJO.class, "get_all_states", true).execute(WebServiceUrl.GET_STATES_OF_INDIA);
    }


    public void setStatesSpinnerAdapter(Spinner spinner, List<String> items) {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_spinner_item,
                        items); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_state_id = statesPOJOS.get(position).getStateId();
                getCities(statesPOJOS.get(position).getStateId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void setCitiesSpinnerAdapter(Spinner spinner, List<String> items) {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_spinner_item,
                        items); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_city_id = cityPOJOS.get(position).getCityId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void getCities(String state_id) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("StateId", state_id));

        new WebServiceBaseResponseList<CityPOJO>(nameValuePairs, getActivity(), new ResponseListCallback<CityPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<CityPOJO> responseListPOJO) {
                try {
                    cityPOJOS.clear();
                    cityPOJOS.addAll(responseListPOJO.getResultList());
                    List<String> stringList = new ArrayList<>();
                    for (CityPOJO cityPOJO : cityPOJOS) {
                        stringList.add(cityPOJO.getCityName());
                    }

                    setCitiesSpinnerAdapter(spinner_city, stringList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, CityPOJO.class, "get_cities", true).execute(WebServiceUrl.GET_CITIES);
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String month = "";
        String day = "";
        if ((monthOfYear + 1) < 10) {
            month = "0" + (monthOfYear + 1);
        } else {
            month = String.valueOf(monthOfYear + 1);
        }

        if (dayOfMonth < 10) {
            day = "0" + dayOfMonth;
        } else {
            day = String.valueOf(dayOfMonth);
        }

//        String date = day + "-" + month + "-" + year;
        String date = year + "-" + month + "-" + day;
        et_date_of_delivery.setText(date);
    }
}
