package com.appentus.materialking.views.activity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.appentus.materialking.R;
import com.appentus.materialking.helper.Config;
import com.appentus.materialking.utility.MyApplication;
import com.appentus.materialking.utility.PrefsData;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivityDemo extends AppCompatActivity {

    @BindView(R.id.logo)
    ImageView logo;


    private BroadcastReceiver mRegistrationBroadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_demo);
        ButterKnife.bind(this);


        ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(logo, PropertyValuesHolder.ofFloat("scaleX", 1.2f), PropertyValuesHolder.ofFloat("scaleY", 1.2f));
        scaleDown.setDuration(400);
        scaleDown.setRepeatCount(ObjectAnimator.INFINITE);
        scaleDown.setRepeatMode(ObjectAnimator.REVERSE);
        scaleDown.start();

        Log.e("FCM","fcmmmm"+ MyApplication.readStringPref(PrefsData.PREF_FCMTOCKEN));


        if(!MyApplication.readStringPref(PrefsData.PREF_FCMTOCKEN).equalsIgnoreCase("")) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Log.e("FCM",MyApplication.readStringPref(PrefsData.PREF_FCMTOCKEN));
                    if (MyApplication.readBooleanPref(PrefsData.PREF_LOGINSTATUS)) {

                        Intent intent = new Intent(SplashActivityDemo.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(SplashActivityDemo.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }, 3000);
        }



         mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    Log.e("FIREBASE", "I am received");
                    Intent intent1 = new Intent(SplashActivityDemo.this, LoginActivity.class);
                    startActivity(intent1);
                    finish();

                }
            }
        };






    }



    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

    }

    @Override
    protected void onStop() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onStop();
    }


}
