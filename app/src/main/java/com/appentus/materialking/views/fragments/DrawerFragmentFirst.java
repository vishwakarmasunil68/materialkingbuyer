package com.appentus.materialking.views.fragments;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.appentus.materialking.R;
import com.appentus.materialking.Util.ToastClass;
import com.appentus.materialking.adapter.CategoryDrawerAdapter;
import com.appentus.materialking.adapter.ExpandableListAdapterCat;
import com.appentus.materialking.model.MainCategories;
import com.appentus.materialking.model.SubCategories;
import com.appentus.materialking.utility.MyApplication;
import com.appentus.materialking.utility.NonScrollExpandableListView;
import com.appentus.materialking.utility.PrefsData;
import com.appentus.materialking.views.activity.LoginActivity;
import com.appentus.materialking.views.activity.MainActivity;
import com.appentus.materialking.views.activity.OrderItemViewActivity;
import com.thoughtbot.expandablerecyclerview.listeners.GroupExpandCollapseListener;
import com.thoughtbot.expandablerecyclerview.listeners.OnGroupClickListener;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Hp on 1/11/2018.
 */

public class DrawerFragmentFirst extends Fragment {
    View view;
    public static CategoryDrawerAdapter mAdapter;

    public static RecyclerView recyclerCategory;
    public static LinearLayoutManager layoutManager;
    public ExpandableGroup expandedGroup;
    Unbinder unbinder;

    public static List<MainCategories> mainCategories;
    @BindView(R.id.btLogout)
    AppCompatButton btLogout;
    @BindView(R.id.ll_my_orders)
    LinearLayout ll_my_orders;


    public static NonScrollExpandableListView lvExpCategory;

    public static ExpandableListAdapterCat listAdapter;
    public static List<String> listDataHeader;
    public static HashMap<String, List<String>> listDataChild;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.drawer_fragment_first_layout, container, false);
        unbinder = ButterKnife.bind(this, view);

        mainCategories = new ArrayList<>();
        recyclerCategory = view.findViewById(R.id.recycler_category);
        //  ViewCompat.setNestedScrollingEnabled(recyclerCategory,false);
        layoutManager = new LinearLayoutManager(getActivity());



        lvExpCategory = view.findViewById(R.id.lvExpCategory);



        lvExpCategory.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousItem = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if(groupPosition != previousItem )
                    lvExpCategory.collapseGroup(previousItem );
                previousItem = groupPosition;
            }
        });

        ll_my_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(getActivity() instanceof MainActivity){
//                    MainActivity mainActivity= (MainActivity) getActivity();
//                    mainActivity.addFragmentHome(new OrderItemsFragment(),"orderItemFragment");
//                }
                startActivity(new Intent(getActivity(), OrderItemViewActivity.class));
            }
        });


        // preparing list data
      //  prepareListData();


        // Listview Group click listener



        //makeGenres(MainActivity.this);

       /* mAdapter.setOnGroupExpandCollapseListener(new GroupExpandCollapseListener() {
            @Override
            public void onGroupExpanded(ExpandableGroup group) {
                if (expandedGroup !=null){
                    mAdapter.toggleGroup(expandedGroup);
                    *//*CategoryDrawerAdapter.ProductViewHolder.groupLayout.setBackgroundColor(getResources().getColor(R.color.drawerSelectedBackground));
                    CategoryDrawerAdapter.ProductViewHolder.tvCcategotyName.setTextColor(getResources().getColor(R.color.colorWhite));
                    CategoryDrawerAdapter.ProductViewHolder.icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_remove));*//*
                }

                expandedGroup=group;
            }

            @Override
            public void onGroupCollapsed(ExpandableGroup group) {
                *//*CategoryDrawerAdapter.ProductViewHolder.groupLayout.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                CategoryDrawerAdapter.ProductViewHolder.tvCcategotyName.setTextColor(getResources().getColor(R.color.colorBlack));
                CategoryDrawerAdapter.ProductViewHolder.icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_add));*//*
            }
        });
*/
        return view;
    }



    /*
     * Preparing the list data
     */
    public static void prepareListData(MainActivity mainActivity) {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();


        listDataHeader.clear();
        listDataChild.clear();

        // Adding child data

        if (MainActivity.categoryModelArrayList != null && MainActivity.categoryModelArrayList.size() > 0) {
            for (int i = 0; i < MainActivity.categoryModelArrayList.size(); i++) {

                listDataHeader.add(MainActivity.categoryModelArrayList.get(i).getName());

               // String id = MainActivity.categoryModelArrayList.get(i).getId();
                //String name = MainActivity.categoryModelArrayList.get(i).getName();
               // String image = MainActivity.categoryModelArrayList.get(i).getImage();


              //  mainCategories.add(new MainCategories(id, name, image, makeRockArtists(i)));

                List<String> top250 = new ArrayList<String>();
                for (int j = 0; j< MainActivity.categoryModelArrayList.get(i).getSubCategoryPOJOS().size(); j++)
                {
                    top250.add(MainActivity.categoryModelArrayList.get(i).getSubCategoryPOJOS().get(j).getName());
                }


                listDataChild.put(MainActivity.categoryModelArrayList.get(i).getName(), top250);
            }


            listAdapter = new ExpandableListAdapterCat(mainActivity, MainActivity.categoryModelArrayList);

            // setting list adapter
            lvExpCategory.setAdapter(listAdapter);

        }


    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = MyApplication.readStringPref(PrefsData.PREF_FCMTOCKEN);

                MyApplication.clearPref();
                MyApplication.writeStringPref(PrefsData.PREF_FCMTOCKEN,token);


                NotificationManager notificationManager = (NotificationManager) getActivity().getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.cancelAll();

                ToastClass.showLongToast(getActivity().getApplicationContext(),"Logout successfully!");

                Intent mIntent =  new Intent(getActivity(), LoginActivity.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mIntent);
                getActivity().finish();


                if(MainActivity.drawerLayout!=null) {
                   // if (MainActivity.drawerLayout.isDrawerOpen()) {
                        MainActivity.drawerLayout.closeDrawer(Gravity.LEFT);
                   // } else {
                    //    MainActivity.drawerLayout.openDrawer(left_drawer);
                    //}


                }
            }
        });
    }

    public static void makeGenres(final MainActivity mainActivity) {

        // List<MainCategories> mainCategories = new ArrayList<>();
        mainCategories.clear();
        if (MainActivity.categoryModelArrayList != null && MainActivity.categoryModelArrayList.size() > 0) {
            for (int i = 0; i < MainActivity.categoryModelArrayList.size(); i++) {

                String id = MainActivity.categoryModelArrayList.get(i).getId();
                String name = MainActivity.categoryModelArrayList.get(i).getName();
                String image = MainActivity.categoryModelArrayList.get(i).getImage();

                //List<SubCategories> mSubCategories = ;

                mainCategories.add(new MainCategories(id, name, image, makeRockArtists(i)));

            }
        }


        Log.e("mainCategories", "" + mainCategories.size());
        mAdapter = new CategoryDrawerAdapter(mainActivity, mainCategories);

        recyclerCategory.setLayoutManager(layoutManager);
        recyclerCategory.setAdapter(mAdapter);


        mAdapter.setOnGroupExpandCollapseListener(new GroupExpandCollapseListener() {

            @Override
            public void onGroupExpanded(ExpandableGroup expandedGroup) {


            }

            @Override
            public void onGroupCollapsed(ExpandableGroup group) {

            }
        });


        mAdapter.setOnGroupClickListener(new OnGroupClickListener() {

            int previousItem = -1;

            @Override
            public boolean onGroupClick(int flatPos) {

              //  Toast.makeText(mainActivity,"Clicked : "+.getName(),5000).show();
                if(flatPos != previousItem )
                   // mAdapter.collapseGroup(previousItem );
             //   mAdapter.onGroupCollapsed(previousItem,1);
                previousItem = flatPos;


               /* if(MainActivity.categoryModelArrayList.get(flatPos).getIsChilds().size()>0)
                {

                }
                else
                {
                    // collapse drawer here
                    if (MainActivity.drawerLayout!=null)
                    {
                        MainActivity.drawerLayout.closeDrawer(Gravity.LEFT);
                    }

                    mainActivity.changeFragmentHome(new ResultPageFragment(),"result");
                }*/
                return false;
            }
        });

    }

    public static List<SubCategories> makeRockArtists(int position) {

        List<SubCategories> mSubCategories = new ArrayList<>();

        for (int i = 0; i < MainActivity.categoryModelArrayList.get(position).getSubCategoryPOJOS().size(); i++) {

            String subCatId = MainActivity.categoryModelArrayList.get(position).getSubCategoryPOJOS().get(i).getId();
            String subCatName = MainActivity.categoryModelArrayList.get(position).getSubCategoryPOJOS().get(i).getName();
            String subCatImage = MainActivity.categoryModelArrayList.get(position).getSubCategoryPOJOS().get(i).getImage();

            SubCategories id = new SubCategories(subCatId, subCatName, subCatImage);
            mSubCategories.add(id);
        }

        return mSubCategories;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
