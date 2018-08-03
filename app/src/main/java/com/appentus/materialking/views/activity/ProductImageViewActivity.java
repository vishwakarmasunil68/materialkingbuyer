package com.appentus.materialking.views.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.appentus.materialking.R;
import com.jsibbold.zoomage.ZoomageView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductImageViewActivity extends AppCompatActivity {

    String image_url="";
    @BindView(R.id.myZoomageView)
    ZoomageView myZoomageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_image_view);
        ButterKnife.bind(this);

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            image_url=bundle.getString("url");
            Picasso.with(getApplicationContext())
                    .load(image_url)
                    .into(myZoomageView);
        }

    }
}
