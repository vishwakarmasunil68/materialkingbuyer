package com.appentus.materialking.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.appentus.materialking.R;
import com.appentus.materialking.pojo.ResponsePOJO;
import com.appentus.materialking.pojo.home.BannerPOJO;
import com.appentus.materialking.pojo.home.CategoryPOJO;
import com.appentus.materialking.pojo.home.HomePOJO;
import com.appentus.materialking.webservice.WebServiceUrl;
import com.appentus.materialking.views.fragments.CartFragment;
import com.appentus.materialking.views.fragments.DrawerFragmentFirst;
import com.appentus.materialking.views.fragments.FragmentCart;
import com.appentus.materialking.views.fragments.FragmentHome;
import com.appentus.materialking.views.fragments.FragmentMyBids;
import com.appentus.materialking.views.fragments.FragmentProfile;
import com.appentus.materialking.views.fragments.SingleCategorySelectedFragment;
import com.appentus.materialking.webservice.ResponseCallBack;
import com.appentus.materialking.webservice.WebServiceBaseResponse;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_notification)
    ImageView iv_notification;
    @BindView(R.id.navigationview)
    NavigationView navigationview;

    public static DrawerLayout drawerLayout;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    public static String ATTACHPIC = "";

    public static ArrayList<CategoryPOJO> categoryModelArrayList;
    public static ArrayList<BannerPOJO> bannersModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);
        ButterKnife.bind(this);


        drawerLayout = findViewById(R.id.drawer_layout);

        categoryModelArrayList = new ArrayList<>();
        bannersModelArrayList = new ArrayList<>();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        changeFragment(new DrawerFragmentFirst(), "drawerFirst");

        changeFragmentHome(new FragmentHome(), "homefrag");


        getHomeCatData();

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        changeFragmentHome(new FragmentHome(), "homefrag");
                        break;
                    case R.id.menu_cart:
                        changeFragmentHome(new CartFragment(), "cartfrag");
                        break;
                    case R.id.menu_mybids:
                        changeFragmentHome(new FragmentMyBids(), "profileAccount");
                        break;
                    case R.id.menu_profile:
                        changeFragmentHome(new FragmentProfile(), "profilefrag");
                        break;

                }
                return true;
            }
        });

        iv_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,NotificationActivity.class));
            }
        });
    }

    public void changeFragmentHome(Fragment targetFragment, String name) {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_home_screen, targetFragment)
                .addToBackStack(name)
                .commit();
    }

    public void addFragmentHome(Fragment targetFragment, String name) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_home_screen, targetFragment)
                .addToBackStack(name)
                .commit();
    }

    public void changeFragment(Fragment targetFragment, String name) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_container_drawer, targetFragment)
                .addToBackStack(name)
                .commit();
    }

    private void getHomeCatData() {

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("", ""));

        new WebServiceBaseResponse<HomePOJO>(nameValuePairs, this, new ResponseCallBack<HomePOJO>() {
            @Override
            public void onGetMsg(ResponsePOJO<HomePOJO> responsePOJO) {
                try {
                    if (responsePOJO.isSuccess()) {
                        categoryModelArrayList.addAll(responsePOJO.getResult().getCategoryPOJOS());
                        bannersModelArrayList.addAll(responsePOJO.getResult().getBannerPOJOS());
                        FragmentHome.infiniteSlider(MainActivity.this);
                        FragmentHome.prepareData();
                        DrawerFragmentFirst.prepareListData(MainActivity.this);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, HomePOJO.class, "Home pojo", true).execute(WebServiceUrl.get_home_data);

//        final ProgressView progressView = new ProgressView(MainActivity.this);
//        progressView.showLoader();
//
//
//        MyApplication.getInstance().cancelPendingRequests("get_home_data");
//        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
//                WebServiceUrl.get_home_data, new Response.Listener<String>() {
//
//            @Override
//            public void onResponse(String response) {
//
//                Log.e("get_home_data",response);
//
//                progressView.hideLoader();
        //  verifyOtp();
//                try {
//                    JSONObject mObject = new JSONObject(response);
//                    String status = mObject.getString("status");
//                    String message = mObject.getString("message");
//                    if(status.equalsIgnoreCase("1"))
//                    {
//                        categoryModelArrayList.clear();
//                        bannersModelArrayList.clear();
//
//
//                        String banner =  mObject.getString("banner");
//                        JSONArray mJsonArrayBanners = new JSONArray(banner);
//
//                        for (int i =0; i < mJsonArrayBanners.length();i++)
//                        {
//                            JSONObject mJsonObjectBanner = mJsonArrayBanners.getJSONObject(i);
//                            BannersModel model =  new BannersModel();
//                            model.setImageUrl(mJsonObjectBanner.getString("image"));
//                            bannersModelArrayList.add(model);
//                        }
//
//
//
//                        String categories =  mObject.getString("categories");
//                        JSONArray mJsonArraycategories = new JSONArray(categories);
//
//                        for (int i =0; i < mJsonArraycategories.length();i++)
//                        {
//                            JSONObject mJsonObjectcategories = mJsonArraycategories.getJSONObject(i);
//                            CategoryModel model =  new CategoryModel();
//                            model.setId(mJsonObjectcategories.getString("id"));
//                            model.setBanner(mJsonObjectcategories.getString("banner"));
//                            model.setName(mJsonObjectcategories.getString("name"));
//                            model.setImage(mJsonObjectcategories.getString("image"));
//                            model.setIsParent(mJsonObjectcategories.getString("isParent"));
//
//
//                            String isChilds = mJsonObjectcategories.getString("isChilds");
//                            JSONArray mJsonArrayChilds = new JSONArray(isChilds);
//
//                            ArrayList<CategoryModel> childsModelList= new ArrayList<>();
//                            for (int j = 0 ; j < mJsonArrayChilds.length();j++)
//                            {
//                                JSONObject mJsonObjectchilds = mJsonArraycategories.getJSONObject(i);
//                                CategoryModel modelChilds =  new CategoryModel();
//                                modelChilds.setId(mJsonObjectchilds.getString("id"));
//                                modelChilds.setName(mJsonObjectchilds.getString("name"));
//                                modelChilds.setImage(mJsonObjectchilds.getString("image"));
//                                modelChilds.setIsParent(mJsonObjectchilds.getString("isParent"));
//                                childsModelList.add(modelChilds);
//                            }
//
//                            model.setIsChilds(childsModelList);
//                            categoryModelArrayList.add(model);
//                        }
//
//                        FragmentHome.infiniteSlider(MainActivity.this);
//                        FragmentHome.prepareData();
//
//                        DrawerFragmentFirst.prepareListData(MainActivity.this);
//
//
//                    }
//
//                    else
//                    {
//                        // user name or password wrong
//                        Toast.makeText(MainActivity.this,message,5000).show();
//                    }
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//
//                progressView.hideLoader();
//                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//                    MyApplication.showError(MainActivity.this,getString(R.string.noConnection),getString(R.string.checkInternet));
//
//                } else if (error instanceof AuthFailureError) {
//                    //TODO
//                    MyApplication.showError(MainActivity.this,getString(R.string.error),getString(R.string.tryAfterSomeTime));
//                } else if (error instanceof ServerError) {
//                    //TODO
//
//                    MyApplication.showError(MainActivity.this,getString(R.string.error),getString(R.string.tryAfterSomeTime));
//                } else if (error instanceof NetworkError) {
//                    //TODO
//                    MyApplication.showError(MainActivity.this,getString(R.string.error),getString(R.string.tryAfterSomeTime));
//
//                } else if (error instanceof ParseError) {
//                    //TODO
//                    MyApplication.showError(MainActivity.this,getString(R.string.error),getString(R.string.tryAfterSomeTime));
//                }
//
//            }
//        }){
//
//            /**
//             * Passing some request headers
//             * */
//            @Override
//            public Map<String, String> getParams()  {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                Log.e("POST DATA", headers.toString());
//
//
//
//                return headers;
//            }
//
//        };
//
//        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(100000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        MyApplication.getInstance().addToRequestQueue(jsonObjReq,"get_home_data");


    }


    @Override
    public void onBackPressed() {
        Fragment selectedFragment = getSupportFragmentManager().findFragmentById(R.id.frame_home_screen);
        Fragment currFragment = getSupportFragmentManager().findFragmentById(R.id.frame_container_drawer);

        if (selectedFragment instanceof FragmentHome) {
            finish();
        } else if (selectedFragment instanceof SingleCategorySelectedFragment) {
            selectedFragment.getChildFragmentManager().popBackStack("homefrag", 0);
        } else if (selectedFragment instanceof FragmentCart) {
            selectedFragment.getChildFragmentManager().popBackStack("homefrag", 0);
            navigation.getMenu().getItem(0).setChecked(true);
        } else if (selectedFragment instanceof FragmentProfile) {
            selectedFragment.getChildFragmentManager().popBackStack("cartfrag", 0);
            navigation.getMenu().getItem(1).setChecked(true);
        } else if (selectedFragment instanceof FragmentMyBids) {
            selectedFragment.getChildFragmentManager().popBackStack("profilefrag", 0);
            navigation.getMenu().getItem(2).setChecked(true);
        }


        super.onBackPressed();

    }
}
