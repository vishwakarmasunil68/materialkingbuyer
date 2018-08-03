package com.appentus.materialking.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;

import com.appentus.materialking.R;
import com.appentus.materialking.pojo.home.CategoryPOJO;
import com.appentus.materialking.views.activity.MainActivity;
import com.appentus.materialking.views.fragments.ResultPageFragment;
import com.appentus.materialking.webservice.WebServiceUrl;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;




/**
 * Created by ggg on 2/20/2018.
 */

public class ExpandableListAdapterCat extends BaseExpandableListAdapter {

    private Context _context;
   // private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
   // private HashMap<String, List<String>> _listDataChild;

    ArrayList<CategoryPOJO> categoryModelArrayList;

    public ExpandableListAdapterCat(Context context, List<String> listDataHeader,
                                 HashMap<String, List<String>> listChildData) {
        this._context = context;
       // this._listDataHeader = listDataHeader;
       // this._listDataChild = listChildData;
    }

    public ExpandableListAdapterCat(MainActivity mainActivity,
                                    ArrayList<CategoryPOJO> categoryModelArrayList) {
        this._context = mainActivity;
        this.categoryModelArrayList = categoryModelArrayList;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.categoryModelArrayList.get(groupPosition).getSubCategoryPOJOS().get(childPosititon).getName();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.expandable_recycler_child_layout, null);
        }


        ImageView iv_subcategoty_child = convertView.findViewById(R.id.iv_subcategoty_child);
        AppCompatTextView tv_subcategoty_child = convertView.findViewById(R.id.tv_subcategoty_child);

        Picasso.with(_context).load(WebServiceUrl.IMAGEBASEURL+categoryModelArrayList.get(groupPosition).getImage()).
                into(iv_subcategoty_child);

        tv_subcategoty_child.setText(categoryModelArrayList.get(groupPosition).getSubCategoryPOJOS().get(childPosition).getName());



    /*    TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);

        txtListChild.setText(childText);*/



        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //categoryModelArrayList.get(groupPosition).getIsChilds().get(childPosition).getName();

               // Toast.makeText(_context,""+categoryModelArrayList.get(groupPosition).getIsChilds().get(childPosition).getName(),5000).show();

                if(MainActivity.drawerLayout!=null)
                {
                    MainActivity.drawerLayout.closeDrawer(Gravity.LEFT);
                }


                ResultPageFragment singleCategorySelectedFragment =  new ResultPageFragment();

                Bundle mBundle =  new Bundle();
                mBundle.putString("catId",categoryModelArrayList.get(groupPosition).getSubCategoryPOJOS().get(childPosition).getId());
                singleCategorySelectedFragment.setArguments(mBundle);

                ((MainActivity)_context).changeFragmentHome(singleCategorySelectedFragment,"result");

            }
        });

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        return this.categoryModelArrayList.get(groupPosition).getSubCategoryPOJOS()
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.categoryModelArrayList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.categoryModelArrayList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.expandable_recycler_group_layout, null);
        }






       /* TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);*/

        ImageView iv_categoty_group = convertView.findViewById(R.id.iv_categoty_group);
        AppCompatTextView tv_categoty_group = convertView.findViewById(R.id.tv_categoty_group);
        ImageView iv_plus_minus = convertView.findViewById(R.id.iv_plus_minus);

        if(isExpanded)
        {
            iv_plus_minus.setImageDrawable(_context.getResources().getDrawable(R.drawable.ic_remove));
            convertView.setBackgroundColor(_context.getResources().getColor(R.color.drawerSelectedBackground));
            tv_categoty_group.setTextColor(_context.getResources().getColor(R.color.colorWhite));

            if(categoryModelArrayList.get(groupPosition).getSubCategoryPOJOS().size()>0)
            {

            }
            else
            {
                if(MainActivity.drawerLayout!=null)
                {
                    MainActivity.drawerLayout.closeDrawer(Gravity.LEFT);
                }

                ResultPageFragment singleCategorySelectedFragment =  new ResultPageFragment();

                Bundle mBundle =  new Bundle();
                mBundle.putString("catId",categoryModelArrayList.get(groupPosition).getId());
                singleCategorySelectedFragment.setArguments(mBundle);

                ((MainActivity)_context).changeFragmentHome(singleCategorySelectedFragment,"result");
            }


        }
        else
        {
            convertView.setBackgroundColor(_context.getResources().getColor(R.color.colorWhite));
            tv_categoty_group.setTextColor(_context.getResources().getColor(R.color.colorBlack));
            iv_plus_minus.setImageDrawable(_context.getResources().getDrawable(R.drawable.ic_add));
        }


        Picasso.with(_context).load(WebServiceUrl.IMAGEBASEURL+categoryModelArrayList.get(groupPosition).getImage()).
                into(iv_categoty_group);

        tv_categoty_group.setText(categoryModelArrayList.get(groupPosition).getName());


        if(categoryModelArrayList.get(groupPosition).getSubCategoryPOJOS().size()>0)
        {
            iv_plus_minus.setVisibility(View.VISIBLE);
        }
        else
        {
            iv_plus_minus.setVisibility(View.INVISIBLE);
        }




        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
