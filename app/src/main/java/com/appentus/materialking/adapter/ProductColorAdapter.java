package com.appentus.materialking.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.appentus.materialking.R;
import com.appentus.materialking.Util.TagUtils;
import com.appentus.materialking.pojo.home.VariationPOJO;
import com.appentus.materialking.pojo.home.VariationSizePOJO;
import com.appentus.materialking.webservice.WebServiceUrl;
import com.appentus.materialking.views.fragments.DetailItemFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by sunil on 03-11-2017.
 */

public class ProductColorAdapter extends RecyclerView.Adapter<ProductColorAdapter.ViewHolder> {
    private List<VariationPOJO> items;
    Activity activity;
    Fragment fragment;

    public ProductColorAdapter(Activity activity, Fragment fragment, List<VariationPOJO> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_product_colors, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        try {
            String image = getImage(items.get(position).getVariationSizePOJOS());

            Log.d(TagUtils.getTag(),"product color image:-"+ WebServiceUrl.IMAGEBASEURL+image);

            if (image.length() > 0) {
                Picasso.with(activity.getApplicationContext())
                        .load(WebServiceUrl.IMAGEBASEURL + image)
                        .into(holder.view);
            } else {
                holder.view.setBackgroundColor(Color.parseColor(items.get(position).getColorCode()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.ll_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragment instanceof DetailItemFragment) {
                    DetailItemFragment detailItemFragment = (DetailItemFragment) fragment;
                    detailItemFragment.showAdapterImage(items.get(position),position);
                }
            }
        });

        holder.itemView.setTag(items.get(position));
    }

    public String getImage(List<VariationSizePOJO> variationSizePOJOS) {
        String image = "";
        for (VariationSizePOJO variationSizePOJO : variationSizePOJOS) {
            if (!variationSizePOJO.getImage().equals("")) {
                image = variationSizePOJO.getImage();
                return image;
            }
        }

        return image;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView view;
        public LinearLayout ll_product;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.view);
            ll_product = itemView.findViewById(R.id.ll_product);
        }
    }
}
