package com.appentus.materialking.views.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import com.appentus.materialking.helper.PinView;
import com.appentus.materialking.receiver.SmsListener;
import com.appentus.materialking.receiver.SmsReceiver;
import com.appentus.materialking.utility.MyApplication;
import com.appentus.materialking.utility.PrefsData;
import com.appentus.materialking.utility.ProgressView;
import com.appentus.materialking.webservice.WebServiceUrl;
import com.appentus.materialking.views.activity.SignupHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Hp on 1/16/2018.
 */

public class SignupOtp extends Fragment {
    View view;

    @BindView(R.id.appCompatTextView)
    AppCompatTextView appCompatTextView;
    @BindView(R.id.text)
    AppCompatTextView text;
    @BindView(R.id.otp_view)
    PinView otpView;
    @BindView(R.id.tvTimer)
    TextView tvTimer;
    @BindView(R.id.btnOtpSubmit)
    AppCompatButton btnOtpSubmit;
    @BindView(R.id.tvResendOtp)
    AppCompatTextView tvResendOtp;
    @BindView(R.id.root)
    RelativeLayout root;
    Unbinder unbinder;
    @BindView(R.id.back_arrow)
    ImageView backArrow;
    CountDownTimer countDownTimer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_otp, container, false);
        unbinder = ButterKnife.bind(this, view);


        btnOtpSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (otpView.getText().toString().trim().length()==6)
                {
                  verifyOtp(otpView.getText().toString().trim());
                }
                else
                {
                    Toast.makeText(getActivity(),"Please enter 6 digit OTP.",5000).show();
                }

               // ((SignupHandler) getActivity()).changeFragment(new SignupTwoFragment(), "signupTwo");


            }
        });

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((SignupHandler)getActivity()).onBackPressed();
            }
        });

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        countDownTimer =   new CountDownTimer(1000*2*60, 1000) {

            public void onTick(long millisUntilFinished) {

                tvResendOtp.setText("Time Left : " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                tvResendOtp.setText("Resend Code ?");
            }

        };

        countDownTimer.start();


        text.setText("Please sit back and relax. We will automatically verify your mobile number.");

        SmsReceiver.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {


                if(!MyApplication.isAppIsInBackground(getActivity()))
                {

                    Log.e("Text", messageText);
                    String otp = getVerificationCode(messageText);

                    otpView.setText(otp);

                    if(countDownTimer!=null)
                    {
                        countDownTimer.cancel();
                    }

                   // verifyOtpHere(otp);
                    verifyOtp(otp);
                    //Toast.makeText(getActivity(), "Message: " + messageText, Toast.LENGTH_LONG).show();
                }
            }
        });


        tvResendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(tvResendOtp.getText().toString().equalsIgnoreCase("Resend Code ?")) {
                    resendOtpHere();
                }
            }
        });




    }

    private void resendOtpHere() {

        final ProgressView progressView = new ProgressView(getActivity());
        progressView.showLoader();


        MyApplication.getInstance().cancelPendingRequests("resend_otp");
        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                WebServiceUrl.resend_otp, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.e("resend_otp",response);

                progressView.hideLoader();
                //  verifyOtp();
                try {
                    JSONObject mObject = new JSONObject(response);
                    String status = mObject.getString("status");
                    String message = mObject.getString("message");
                    if(status.equalsIgnoreCase("1"))
                    {
                        //String user_id = mObject.getString("user_id");

                        // MyApplication.writeStringPref(PrefsData.PREF_USERID,user_id);
                        //  ((SignupHandler) getActivity()).changeFragment(new SignupOtp(), "otp");

                        if(countDownTimer!=null)
                        {
                            countDownTimer.start();
                        }

                       // ((SignupHandler) getActivity()).changeFragment(new SignupTwoFragment(), "signupTwo");

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
                headers.put("mobile", MyApplication.readStringPref(PrefsData.PREF_mobileNumber));
                headers.put("user_id", MyApplication.readStringPref(PrefsData.PREF_USERID));

                Log.e("POST DATA", headers.toString());



                return headers;
            }

        };

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyApplication.getInstance().addToRequestQueue(jsonObjReq,"resend_otp");



    }


    private void verifyOtp(final String otpText) {

        final ProgressView progressView = new ProgressView(getActivity());
        progressView.showLoader();


        MyApplication.getInstance().cancelPendingRequests("authenticate_otp");
        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                WebServiceUrl.authenticate_otp, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.e("authenticate_otp",response);

                progressView.hideLoader();
                //  verifyOtp();
                try {
                    JSONObject mObject = new JSONObject(response);
                    String status = mObject.getString("status");
                    String message = mObject.getString("message");
                    if(status.equalsIgnoreCase("1"))
                    {
                        //String user_id = mObject.getString("user_id");

                       // MyApplication.writeStringPref(PrefsData.PREF_USERID,user_id);
                      //  ((SignupHandler) getActivity()).changeFragment(new SignupOtp(), "otp");

                        if(countDownTimer!=null)
                        {
                            countDownTimer.cancel();
                        }

                        ((SignupHandler) getActivity()).changeFragment(new SignupTwoFragment(), "signupTwo");
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
                headers.put("otp", otpText);
                headers.put("user_id", MyApplication.readStringPref(PrefsData.PREF_USERID));

                Log.e("POST DATA", headers.toString());



                return headers;
            }

        };

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyApplication.getInstance().addToRequestQueue(jsonObjReq,"authenticate_otp");



    }


    private String getVerificationCode(String message) {

        message = message.replaceAll("\\s+", "");

        String code = null;

        code = message.split(":")[1];

      /*  int index = message.indexOf(":");

        if (index != -1) {
            int start = index + 2;
            int length = 6;
            code = message.substring(start, start + length);
            return code;
        }*/

        return code;
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

        if(countDownTimer!=null)
        {
            countDownTimer.cancel();
        }

    }
}
