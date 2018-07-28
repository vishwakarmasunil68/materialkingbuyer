package com.appentus.materialking.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.appentus.materialking.R;
import com.appentus.materialking.pojo.MyOrdersPOJO;
import com.appentus.materialking.views.fragments.FragmentCompleteOrder;
import com.appentus.materialking.views.fragments.FragmentPartialOrder;
import com.appentus.materialking.views.fragments.FragmentRecommendedOrder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_selected_offers)
    Button btn_selected_offers;
    @BindView(R.id.viewPager_orders)
    ViewPager viewPagerOrders;
    @BindView(R.id.rb_completed_order)
    RadioButton rbCompletedOrder;
    @BindView(R.id.rb_partial_order)
    RadioButton rbPartialOrder;
    @BindView(R.id.rb_recommended_order)
    RadioButton rbRecommendedOrder;
    @BindView(R.id.rg_select_tabs)
    RadioGroup rgSelectTabs;
    @BindView(R.id.tv_view_item)
    TextView tv_view_item;

    ViewPagerAdapter vpAdapter;

    MyOrdersPOJO myOrdersPOJO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);

        myOrdersPOJO = (MyOrdersPOJO) getIntent().getSerializableExtra("MyOrdersPOJO");

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Order Id: 1234567");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        vpAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerOrders.setAdapter(vpAdapter);
        viewPagerOrders.setOffscreenPageLimit(3);

        rbCompletedOrder.setChecked(true);


        viewPagerOrders.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    rbCompletedOrder.setChecked(true);
                } else if (position == 1) {
                    rbPartialOrder.setChecked(true);
                } else if (position == 2) {
                    rbRecommendedOrder.setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        rgSelectTabs.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_completed_order) {
                    viewPagerOrders.setCurrentItem(0);
                } else if (checkedId == R.id.rb_partial_order) {
                    viewPagerOrders.setCurrentItem(1);
                } else if (checkedId == R.id.rb_recommended_order) {
                    viewPagerOrders.setCurrentItem(2);
                }
            }
        });

        btn_selected_offers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderActivity.this, OfferActivity.class);
                intent.putExtra("order_id", myOrdersPOJO.getOrderId());
                startActivity(intent);
            }
        });

        tv_view_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderActivity.this, OrderItemViewActivity.class);
                intent.putExtra("order_id", myOrdersPOJO.getOrderId());
                startActivity(intent);
            }
        });
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if (position == 0) {
                fragment = new FragmentCompleteOrder(myOrdersPOJO);
            } else if (position == 1) {
                fragment = new FragmentPartialOrder(myOrdersPOJO);
            } else if (position == 2) {
                fragment = new FragmentRecommendedOrder(myOrdersPOJO);
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            String title = null;
            if (position == 0) {
                title = "Complete Order";
            } else if (position == 1) {
                title = "Partial Order";
            } else if (position == 2) {
                title = "Recommended";
            }
            return title;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
