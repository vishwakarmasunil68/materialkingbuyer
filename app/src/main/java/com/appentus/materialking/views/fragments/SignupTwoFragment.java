package com.appentus.materialking.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.appentus.materialking.R;
import com.appentus.materialking.utility.MyApplication;
import com.appentus.materialking.utility.PrefsData;
import com.appentus.materialking.utility.ProgressView;
import com.appentus.materialking.webservice.WebServiceUrl;
import com.appentus.materialking.views.activity.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Hp on 1/15/2018.
 */

public class SignupTwoFragment extends Fragment {
    View view;
    Unbinder unbinder;
    @BindView(R.id.btn_signup_continue)
    AppCompatButton btnSignupContinue;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.titleToolbar)
    AppCompatTextView titleToolbar;

    @BindView(R.id.etAddress)
    AppCompatEditText etAddress;
    @BindView(R.id.etPinCode)
    AppCompatEditText etPinCode;
    @BindView(R.id.etState)
    AppCompatEditText etState;
    @BindView(R.id.etCity)
    AppCompatEditText etCity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signup_two, container, false);
        unbinder = ButterKnife.bind(this, view);

        titleToolbar.setText("Enter your Address\n2/2");

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        //((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Enter your Address");
        //((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("2/2");
       // ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);


        btnSignupContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getActivity(), MainActivity.class));

                String etAddressString  = etAddress.getText().toString().trim();
                String etPinCodeString  = etPinCode.getText().toString().trim();
                String etStateString  = etState.getText().toString().trim();
                String etCityString  = etCity.getText().toString().trim();

                if(etAddressString.isEmpty())
                {
                    Toast.makeText(getActivity(),"Please Enter Address !",5000).show();
                    return;
                }


                if(etAddressString.startsWith(" "))
                {
                    Toast.makeText(getActivity(),"Pre Space Not Allowed in  Address !",5000).show();
                    return;
                }

                if(countContinuosOccurence(etAddressString))
                {
                    Toast.makeText(getActivity(),"Continuous Multiple Space Not Allowed in  Address !",5000).show();
                    return;
                }



                if(etPinCodeString.isEmpty())
                {
                    Toast.makeText(getActivity(),"Please Enter PinCode !",5000).show();
                    return;
                }

                if(etPinCodeString.length()<6)
                {
                    Toast.makeText(getActivity(),"Please Enter valid 6 digit PinCode !",5000).show();
                    return;
                }


                if(etStateString.isEmpty())
                {
                    Toast.makeText(getActivity(),"Please Enter State !",5000).show();
                    return;
                }


                if(etStateString.startsWith(" "))
                {
                    Toast.makeText(getActivity(),"Pre Space Not Allowed in  State !",5000).show();
                    return;
                }

                if(countContinuosOccurence(etStateString))
                {
                    Toast.makeText(getActivity(),"Continuous Multiple Space Not Allowed in  State !",5000).show();
                    return;
                }



                if(etCityString.isEmpty())
                {
                    Toast.makeText(getActivity(),"Please Enter City !",5000).show();
                    return;
                }


                if(etCityString.startsWith(" "))
                {
                    Toast.makeText(getActivity(),"Pre Space Not Allowed in  City !",5000).show();
                    return;
                }

                if(countContinuosOccurence(etCityString))
                {
                    Toast.makeText(getActivity(),"Continuous Multiple Space Not Allowed in City !",5000).show();
                    return;
                }



                registerStepFinal(etAddressString,etPinCodeString,etStateString,etCityString);


            }
        });


        return view;
    }


    public static boolean countContinuosOccurence(String first) {

        StringBuffer result = new StringBuffer();
        int count = 1;
        int i;
        for (i = 1; i < first.length(); i++) {

            if (first.charAt(i) == (first.charAt(i - 1)  )) {
                if(first.charAt(i)==' ')
                    count++;
            } else {
                if (count > 1) {
                    result.append(String.valueOf(count) + first.charAt(i - 1));
                } else {
                    result.append(first.charAt(i - 1));
                }
                count = 1;
            }
        }

        // ADD THIS - to take care of the last run.
        if (count > 1) {
            result.append(String.valueOf(count) + first.charAt(i - 1));
        } else {
            result.append(first.charAt(i - 1));
        }

        System.out.println("First String is:"+ first);
        System.out.println("Result is:" + result);


        return result.toString().matches(".*\\d+.*");
    }





    private void registerStepFinal(final String etAddressString,
                                   final String etPinCodeString,
                                   final String etStateString,
                                   final String etCityString) {
        final ProgressView progressView = new ProgressView(getActivity());
        progressView.showLoader();


        MyApplication.getInstance().cancelPendingRequests("user_register");
        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                WebServiceUrl.user_register, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.e("user_register", response);

                progressView.hideLoader();
                //  verifyOtp();
                try {
                    JSONObject mObject = new JSONObject(response);
                    String status = mObject.getString("status");
                    String message = mObject.getString("message");
                    if (status.equalsIgnoreCase("1")) {
                        String result = mObject.getString("result");

                       // MyApplication.writeStringPref(PrefsData.PREF_USERID, user_id);
                       // MyApplication.writeStringPref(PrefsData.PREF_mobileNumber, mobileNumber);
                       // ((SignupHandler) getActivity()).changeFragment(new SignupOtp(), "otp");

                        JSONArray mJsonArray =  new JSONArray(result);
                        JSONObject mJsonObject = mJsonArray.getJSONObject(0);


                        MyApplication.writeStringPref(PrefsData.PREF_USERID,mJsonObject.getString("user_id"));
                        MyApplication.writeStringPref(PrefsData.PREF_mobileNumber,mJsonObject.getString("mobile"));
                        MyApplication.writeStringPref(PrefsData.name,mJsonObject.getString("name"));
                        MyApplication.writeStringPref(PrefsData.gender,mJsonObject.getString("gender"));
                        MyApplication.writeStringPref(PrefsData.email,mJsonObject.getString("email"));
                        MyApplication.writeStringPref(PrefsData.address,mJsonObject.getString("address"));
                        MyApplication.writeStringPref(PrefsData.pincode,mJsonObject.getString("pincode"));
                        MyApplication.writeStringPref(PrefsData.state,mJsonObject.getString("state"));
                        MyApplication.writeStringPref(PrefsData.city,mJsonObject.getString("city"));
                        MyApplication.writeStringPref(PrefsData.created,mJsonObject.getString("created"));
                        MyApplication.writeStringPref(PrefsData.image,mJsonObject.getString("image"));


                        MyApplication.writeBooleanPref(PrefsData.PREF_LOGINSTATUS, true);

                        //Toast.makeText(getActivity(), message, 5000).show();


                        Intent mIntent =  new Intent(getActivity(), MainActivity.class);
                        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(mIntent);

                        getActivity().finish();

                    }
                    else
                    {
                        // user name or password wrong
                        Toast.makeText(getActivity(), message, 5000).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


                progressView.hideLoader();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    MyApplication.showError(getActivity(), getString(R.string.noConnection), getString(R.string.checkInternet));

                } else if (error instanceof AuthFailureError) {
                    //TODO
                    MyApplication.showError(getActivity(), getString(R.string.error), getString(R.string.tryAfterSomeTime));
                } else if (error instanceof ServerError) {
                    //TODO

                    MyApplication.showError(getActivity(), getString(R.string.error), getString(R.string.tryAfterSomeTime));
                } else if (error instanceof NetworkError) {
                    //TODO
                    MyApplication.showError(getActivity(), getString(R.string.error), getString(R.string.tryAfterSomeTime));

                } else if (error instanceof ParseError) {
                    //TODO
                    MyApplication.showError(getActivity(), getString(R.string.error), getString(R.string.tryAfterSomeTime));
                }

            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getParams() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("address", etAddressString);
                headers.put("pincode", etPinCodeString);
                headers.put("state", etStateString);
                headers.put("city", etCityString);
                headers.put("device_id", Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID));
                headers.put("osType", "Android");
                headers.put("fcmToken", MyApplication.readStringPref(PrefsData.PREF_FCMTOCKEN));
                headers.put("user_id", MyApplication.readStringPref(PrefsData.PREF_USERID));

                Log.e("POST DATA", headers.toString());


                return headers;
            }

        };

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyApplication.getInstance().addToRequestQueue(jsonObjReq, "user_register");


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
