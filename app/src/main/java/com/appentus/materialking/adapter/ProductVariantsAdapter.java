package com.appentus.materialking.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appentus.materialking.R;
import com.appentus.materialking.Util.TagUtils;
import com.appentus.materialking.Util.ToastClass;
import com.appentus.materialking.pojo.product.FilterProductPOJO;
import com.appentus.materialking.utility.MyApplication;
import com.appentus.materialking.utility.PrefsData;
import com.appentus.materialking.webservice.WebServiceBase;
import com.appentus.materialking.webservice.WebServiceUrl;
import com.appentus.materialking.webservice.WebServicesCallBack;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hp on 1/17/2018.
 */

public class ProductVariantsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<FilterProductPOJO> items = new ArrayList<>();
    Fragment fragment;

    public ProductVariantsAdapter(Context context, Fragment fragment, List<FilterProductPOJO> items) {
        this.context = context;
        this.fragment = fragment;
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_product_variant_size_item, parent, false);
        return new ProductVariantsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, final int position) {
        final ViewHolder holder = (ViewHolder) holder1;

        holder.tv_product_name.setText(items.get(position).getName());
        holder.tv_description.setText(items.get(position).getDescription());

        if (items.get(position).getSize_detail() != null) {
            holder.ll_size.setVisibility(View.VISIBLE);
            holder.tv_size.setText(items.get(position).getSize_detail().getSizeName());
        } else {
            holder.ll_size.setVisibility(View.GONE);
        }
        if (items.get(position).getBrand_detail() != null) {
            holder.ll_brand.setVisibility(View.VISIBLE);
            holder.tv_brand.setText(items.get(position).getBrand_detail().getBrandName());
        } else {
            holder.ll_brand.setVisibility(View.GONE);
        }

        holder.tv_quantity.setText(items.get(position).getQty());

        Log.d(TagUtils.getTag(), "image:-" + WebServiceUrl.IMAGEBASEURL + items.get(position).getVariationImage());

        Picasso.with(context)
                .load(WebServiceUrl.IMAGEBASEURL + items.get(position).getVariationImage())
                .into(holder.iv_product_image);

        holder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
                    nameValuePairs.add(new BasicNameValuePair("product_id", items.get(position).getProductId()));
                    nameValuePairs.add(new BasicNameValuePair("user_id", MyApplication.readStringPref(PrefsData.PREF_USERID)));
                    nameValuePairs.add(new BasicNameValuePair("variation_id", items.get(position).getVariationId()));
                    nameValuePairs.add(new BasicNameValuePair("color_id", items.get(position).getColorId()));
                    nameValuePairs.add(new BasicNameValuePair("brand_id", items.get(position).getBrandId()));
                    nameValuePairs.add(new BasicNameValuePair("type_id", items.get(position).getTypeId()));
                    nameValuePairs.add(new BasicNameValuePair("product_variation_id", items.get(position).getProductVariationId()));
                    nameValuePairs.add(new BasicNameValuePair("size_id", items.get(position).getSizeId()));
                    nameValuePairs.add(new BasicNameValuePair("qty", "1"));
                    new WebServiceBase(nameValuePairs, context, new WebServicesCallBack() {
                        @Override
                        public void onGetMsg(String apicall, String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.optInt("status") == 1) {
                                    holder.btn_add.setText("Added");
                                }
                                ToastClass.showShortToast(context.getApplicationContext(), jsonObject.optString("message"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, "CALL_CART_ADD_API", true).execute(WebServiceUrl.ADD_PRODUCT_TO_CART);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_product_name;
        TextView tv_description;
        ImageView iv_product_image;
        TextView tv_brand;
        TextView tv_size;
        TextView tv_quantity;
        Button btn_add;
        LinearLayout ll_brand;
        LinearLayout ll_size;
        LinearLayout ll_quantity;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_product_name = itemView.findViewById(R.id.tv_product_name);
            tv_description = itemView.findViewById(R.id.tv_description);
            iv_product_image = itemView.findViewById(R.id.iv_product_image);
            btn_add = itemView.findViewById(R.id.btn_add);
            tv_brand = itemView.findViewById(R.id.tv_brand);
            tv_size = itemView.findViewById(R.id.tv_size);
            tv_quantity = itemView.findViewById(R.id.tv_quantity);
            ll_brand = itemView.findViewById(R.id.ll_brand);
            ll_size = itemView.findViewById(R.id.ll_size);
            ll_quantity = itemView.findViewById(R.id.ll_quantity);
        }
    }
}
