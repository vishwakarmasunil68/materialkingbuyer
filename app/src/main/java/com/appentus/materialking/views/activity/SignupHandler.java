package com.appentus.materialking.views.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.appentus.materialking.R;
import com.appentus.materialking.views.fragments.SignupOneFragment;
import com.appentus.materialking.views.fragments.SignupOtp;
import com.appentus.materialking.views.fragments.SignupTwoFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class SignupHandler extends AppCompatActivity {

    String screenPrefs = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_signup_handler);
        ButterKnife.bind(this);

        Bundle mBundle = getIntent().getExtras();
        if(mBundle!=null)
        {
            screenPrefs = mBundle.getString("screenPrefs");
        }

        if(screenPrefs.equalsIgnoreCase("otp"))
        {
            changeFragment(new SignupOtp(), "oneFrag");
        }
        else if (screenPrefs.equalsIgnoreCase("finalRegister"))
        {
            changeFragment(new SignupTwoFragment(), "oneFrag");
        }

        else {
            changeFragment(new SignupOneFragment(), "oneFrag");
        }






    }

    public void changeFragment(Fragment targetFragment, String name) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_signup, targetFragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        Fragment currentFrag = getSupportFragmentManager().findFragmentById(R.id.frame_signup);
        if (currentFrag instanceof SignupOneFragment) {
            finish();
        } else if (currentFrag instanceof SignupOtp) {
          //  currentFrag.getChildFragmentManager().popBackStack("oneFrag", 0);
        }
        else if (currentFrag instanceof SignupTwoFragment) {
            //currentFrag.getChildFragmentManager().popBackStack("otp", 0);
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
