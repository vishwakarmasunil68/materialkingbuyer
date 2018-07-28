package com.appentus.materialking.views.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.linear_root)
    LinearLayout linearRoot;
    @BindView(R.id.etUserName)
    AppCompatEditText etUserName;
    @BindView(R.id.etPassword)
    AppCompatEditText etPassword;
    @BindView(R.id.tvForgotPassword)
    AppCompatTextView tvForgotPassword;
    @BindView(R.id.btnLogin)
    AppCompatButton btnLogin;
    @BindView(R.id.tvSignup)
    AppCompatTextView tvSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        setupUI(linearRoot);
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public void setupUI(View view) {
        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(LoginActivity.this);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    @OnClick({R.id.tvForgotPassword, R.id.btnLogin, R.id.tvSignup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvForgotPassword:
                break;
            case R.id.btnLogin:
               // startActivity(new Intent(LoginActivity.this, MainActivity.class));

                if(etUserName.getText().toString().isEmpty())
                {
                    Toast.makeText(LoginActivity.this,"Please enter username !",5000).show();
                    return;
                }

                if(etUserName.getText().toString().trim().contains("@"))
                {
                    if(!Patterns.EMAIL_ADDRESS.matcher(etUserName.getText().toString().trim()).matches())
                    {
                        Toast.makeText(LoginActivity.this,"Enter Valid Email Address !",5000).show();
                        return;
                    }
                }


                if(etUserName.getText().toString().startsWith(" "))
                {
                    Toast.makeText(LoginActivity.this,"Pre Space not allowed in username!",5000).show();
                    return;
                }



                if(etUserName.getText().toString().split(" ", -1).length - 1>1)
                {
                    Toast.makeText(LoginActivity.this,"Multiple Space not allowed in username!",5000).show();
                    return;
                }



                if(etPassword.getText().toString().isEmpty())
                {
                    Toast.makeText(LoginActivity.this,"Please enter password.",5000).show();
                    return;
                }



                if(etPassword.getText().toString().startsWith(" "))
                {
                    Toast.makeText(LoginActivity.this,"Pre Space not allowed in password!",5000).show();
                    return;
                }


                if(etPassword.getText().toString().split(" ", -1).length - 1>1)
                {
                    Toast.makeText(LoginActivity.this,"Multiple Space not allowed in password!",5000).show();
                    return;
                }



                loginOnServer(etUserName.getText().toString(),etPassword.getText().toString());
                break;


            case R.id.tvSignup:
                startActivity(new Intent(LoginActivity.this, SignupHandler.class));
                finish();
                break;
        }
    }

    private void loginOnServer(final String userName, final String userPassword) {
        final ProgressView progressView = new ProgressView(LoginActivity.this);
        progressView.showLoader();


        MyApplication.getInstance().cancelPendingRequests("LOGINBUYER");
        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                WebServiceUrl.LOGINBUYER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.e("LOGINBUYER",response);

                progressView.hideLoader();
                //  verifyOtp();
                try {
                    JSONObject mObject = new JSONObject(response);
                    String status = mObject.getString("status");
                    String message = mObject.getString("message");
                    if(status.equalsIgnoreCase("1"))
                    {

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

                        Intent mIntent =  new Intent(LoginActivity.this, MainActivity.class);
                        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(mIntent);
                        finish();

                    }
                    else if(status.equalsIgnoreCase("2"))
                    {
                        // send on otp
                        Intent mIntent =  new Intent(LoginActivity.this, SignupHandler.class);
                        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        mIntent.putExtra("screenPrefs","otp");
                        startActivity(mIntent);
                        finish();
                    }
                    else if(status.equalsIgnoreCase("3"))
                    {
                     // send on second registration
                        Intent mIntent =  new Intent(LoginActivity.this, SignupHandler.class);
                        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        mIntent.putExtra("screenPrefs","finalRegister");
                        startActivity(mIntent);
                        finish();
                    }
                    else
                    {
                        // user name or password wrong
                        Toast.makeText(LoginActivity.this,message,5000).show();
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
                    MyApplication.showError(LoginActivity.this,getString(R.string.noConnection),getString(R.string.checkInternet));

                } else if (error instanceof AuthFailureError) {
                    //TODO
                    MyApplication.showError(LoginActivity.this,getString(R.string.error),getString(R.string.tryAfterSomeTime));
                } else if (error instanceof ServerError) {
                    //TODO

                    MyApplication.showError(LoginActivity.this,getString(R.string.error),getString(R.string.tryAfterSomeTime));
                } else if (error instanceof NetworkError) {
                    //TODO
                    MyApplication.showError(LoginActivity.this,getString(R.string.error),getString(R.string.tryAfterSomeTime));

                } else if (error instanceof ParseError) {
                    //TODO
                    MyApplication.showError(LoginActivity.this,getString(R.string.error),getString(R.string.tryAfterSomeTime));
                }

            }
        }){

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getParams()  {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("email", userName);
                headers.put("password", userPassword);
                headers.put("osType", "Android");
                headers.put("fcmToken", MyApplication.readStringPref(PrefsData.PREF_FCMTOCKEN));

                Log.e("POST DATA", headers.toString());



                return headers;
            }

        };

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyApplication.getInstance().addToRequestQueue(jsonObjReq,"LOGINBUYER");



    }


}
