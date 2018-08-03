package com.appentus.materialking.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.appentus.materialking.R;
import com.appentus.materialking.pojo.home.VariationSizePOJO;
import com.appentus.materialking.webservice.WebServiceUrl;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by sunil on 03-11-2017.
 */

public class ProductVariantSizesAdapter extends RecyclerView.Adapter<ProductVariantSizesAdapter.ViewHolder> {
    private List<VariationSizePOJO> items;
    Activity activity;
    Fragment fragment;

    public ProductVariantSizesAdapter(Activity activity, Fragment fragment, List<VariationSizePOJO> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_product_variant_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Picasso.with(activity.getApplicationContext())
                .load(WebServiceUrl.IMAGEBASEURL+items.get(position).getImage())
                .into(holder.iv_size);

        holder.iv_size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(fragment!=null&&fragment instanceof DetailItemFragment){
//                    DetailItemFragment detailItemFragment= (DetailItemFragment) fragment;
//                    detailItemFragment.showImage(items.get(position));
//                }
            }
        });

        holder.itemView.setTag(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_size;
        public LinearLayout ll_analyze;
        public ViewHolder(View itemView) {
            super(itemView);
            iv_size=itemView.findViewById(R.id.iv_size);
        }
    }
}
