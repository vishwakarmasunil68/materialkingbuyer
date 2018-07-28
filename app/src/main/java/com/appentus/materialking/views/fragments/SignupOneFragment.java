package com.appentus.materialking.views.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import com.appentus.materialking.utility.NoDefaultSpinner;
import com.appentus.materialking.utility.PrefsData;
import com.appentus.materialking.utility.ProgressView;
import com.appentus.materialking.webservice.WebServiceUrl;
import com.appentus.materialking.views.activity.LoginActivity;
import com.appentus.materialking.views.activity.SignupHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Hp on 1/15/2018.
 */

public class SignupOneFragment extends Fragment {
    View view;
    Unbinder unbinder;

    @BindView(R.id.btn_email_continue)
    AppCompatButton btnEmailContinue;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.titleToolbar)
    AppCompatTextView titleToolbar;

    @BindView(R.id.tv_email_signin)
    AppCompatTextView tvEmailSignin;
    @BindView(R.id.etName)
    AppCompatEditText etName;

    @BindView(R.id.spinnerGenderSpinner)
    NoDefaultSpinner spinnerGender;

    @BindView(R.id.etMobile)
    AppCompatEditText etMobile;
    @BindView(R.id.etEmail)
    AppCompatEditText etEmail;
    @BindView(R.id.etPassword)
    AppCompatEditText etPassword;
    @BindView(R.id.tv_conditions)
    AppCompatTextView tvConditions;


    List<String> genderData = new ArrayList<>();
    private final static int REQUEST_READ_SMS_PERMISSION = 3004;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signup_one, container, false);
        unbinder = ButterKnife.bind(this, view);


        titleToolbar.setText("Sign Up\n1/2");

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
      //  ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");
        //((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("1/2");
        //((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ///((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);

        btnEmailContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((SignupHandler) getActivity()).changeFragment(new SignupTwoFragment(), "signupTwo");







                if(etName.getText().toString().isEmpty())
                {
                    Toast.makeText(getActivity(),"Please Enter Your Name !",5000).show();
                    return;
                }


                if(etName.getText().toString().startsWith(" "))
                {
                    Toast.makeText(getActivity(),"Pre Space not allowed in Name!",5000).show();
                    return;
                }


                if( countContinuosOccurence(etName.getText().toString()))
                {
                    Toast.makeText(getActivity(),"Multiple Space not allowed in Name!",5000).show();
                    return;
                }








                if(spinnerGender.getSelectedItemPosition()== -1)
                {
                    Toast.makeText(getActivity(),"Please Select Your Gender !",5000).show();
                    return;
                }

                if(etMobile.getText().toString().isEmpty())
                {
                    Toast.makeText(getActivity(),"Mobile Number field is empty !",5000).show();
                    return;
                }


                if(etMobile.getText().toString().startsWith(" "))
                {
                    Toast.makeText(getActivity(),"Pre Space not allowed in Mobile Number!",5000).show();
                    return;
                }



                if(etMobile.getText().toString().trim().length()!=10)
                {
                    Toast.makeText(getActivity(),"Mobile Number should be 10 digits only. Ex. 97XXXXXX45 ",5000).show();
                    return;
                }


                if(etEmail.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(getActivity(),"Email field is empty !",5000).show();
                    return;
                }



                if(etEmail.getText().toString().startsWith(" "))
                {
                    Toast.makeText(getActivity(),"Pre Space not allowed in Email!",5000).show();
                    return;
                }





                if(!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString().trim()).matches())
                {
                    Toast.makeText(getActivity(),"Enter Valid Email Address !",5000).show();
                    return;
                }


                if(etPassword.getText().toString().isEmpty())
                {
                    Toast.makeText(getActivity(),"Password field is empty !",5000).show();
                    return;
                }


                if(etPassword.getText().toString().contains(" "))
                {
                    Toast.makeText(getActivity(),"Space not allowed in Password!",5000).show();
                    return;
                }





                if(etPassword.getText().toString().trim().length()<=7)
                {
                    Toast.makeText(getActivity(),"Minimum password length is 8 !",5000).show();
                    return;
                }


                String nameOfBuyer = etName.getText().toString().trim();
                String gender = spinnerGender.getSelectedItem().toString();
                String mobileNumber = etMobile.getText().toString().trim();
                String emailId = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();



                if (ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED ) {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.READ_SMS},
                            REQUEST_READ_SMS_PERMISSION);
                } else {
                    registerStepFirst(nameOfBuyer,gender,mobileNumber,emailId,password);
                }








            }
        });

        tvEmailSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            if (REQUEST_READ_SMS_PERMISSION == requestCode) {
                // TODO Request Granted for READ_SMS.
                System.out.println("REQUEST_READ_SMS_PERMISSION Permission Granted");
            }


        }
    }





    private void registerStepFirst(final String nameOfBuyer,
                                   final  String gender,
                                   final String mobileNumber,
                                   final String emailId,
                                   final String password) {
        final ProgressView progressView = new ProgressView(getActivity());
        progressView.showLoader();


        MyApplication.getInstance().cancelPendingRequests("create_user");
        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                WebServiceUrl.create_user, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.e("create_user",response);

                progressView.hideLoader();
                //  verifyOtp();
                try {
                    JSONObject mObject = new JSONObject(response);
                    String status = mObject.getString("status");
                    String message = mObject.getString("message");
                    if(status.equalsIgnoreCase("1"))
                    {
                        String user_id = mObject.getString("user_id");

                        MyApplication.writeStringPref(PrefsData.PREF_USERID,user_id);
                        MyApplication.writeStringPref(PrefsData.PREF_mobileNumber,mobileNumber);
                        ((SignupHandler) getActivity()).changeFragment(new SignupOtp(), "otp");

                    }
                    else if(status.equalsIgnoreCase("2"))
                    {
                        // send on otp
                    }
                    else if(status.equalsIgnoreCase("3"))
                    {
                        // send on second registration
                    }
                    else
                    {
                        // user name or password wrong
                        Toast.makeText(getActivity(),message,5000).show();
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
                    MyApplication.showError(getActivity(),getString(R.string.noConnection),getString(R.string.checkInternet));

                } else if (error instanceof AuthFailureError) {
                    //TODO
                    MyApplication.showError(getActivity(),getString(R.string.error),getString(R.string.tryAfterSomeTime));
                } else if (error instanceof ServerError) {
                    //TODO

                    MyApplication.showError(getActivity(),getString(R.string.error),getString(R.string.tryAfterSomeTime));
                } else if (error instanceof NetworkError) {
                    //TODO
                    MyApplication.showError(getActivity(),getString(R.string.error),getString(R.string.tryAfterSomeTime));

                } else if (error instanceof ParseError) {
                    //TODO
                    MyApplication.showError(getActivity(),getString(R.string.error),getString(R.string.tryAfterSomeTime));
                }

            }
        }){

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getParams()  {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("name", nameOfBuyer);
                headers.put("email", emailId);
                headers.put("mobile", mobileNumber);
                headers.put("gender", gender);
                headers.put("password", password);
                headers.put("created", ""+System.currentTimeMillis());

                Log.e("POST DATA", headers.toString());



                return headers;
            }

        };

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyApplication.getInstance().addToRequestQueue(jsonObjReq,"create_user");



    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        genderData.clear();
        spinnerGender.setPrompt("Select Gender");

        genderData.add("Male");
        genderData.add("Female");
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, genderData);
        spinnerGender.setAdapter(genderAdapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
