package com.appentus.materialking.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appentus.materialking.R;
import com.appentus.materialking.Util.ToastClass;
import com.appentus.materialking.pojo.cart.CartItemPOJO;
import com.appentus.materialking.webservice.WebServiceBase;
import com.appentus.materialking.webservice.WebServiceUrl;
import com.appentus.materialking.webservice.WebServicesCallBack;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Hp on 1/17/2018.
 */

public class CartListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<CartItemPOJO> resultFoundModels = new ArrayList<>();


    public CartListAdapter(Context context, List<CartItemPOJO> resultFoundModels) {
        this.context = context;
        this.resultFoundModels = resultFoundModels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_cart_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder holder1 = (ViewHolder) holder;

        holder1.tv_item_name.setText(resultFoundModels.get(position).getProductName());
        holder1.tv_minus_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int qty = Integer.parseInt(holder1.tv_quantity_cart.getText().toString());
                    if (qty != 1) {
                        qty = qty - 1;
                        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
                        nameValuePairs.add(new BasicNameValuePair("cart_id", resultFoundModels.get(position).getCartId()));
                        nameValuePairs.add(new BasicNameValuePair("qty_required", String.valueOf(qty)));
                        final int finalQty = qty;
                        new WebServiceBase(nameValuePairs, context, new WebServicesCallBack() {
                            @Override
                            public void onGetMsg(String apicall, String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject.optInt("status") == 1) {
                                        holder1.tv_quantity_cart.setText(String.valueOf(finalQty));
                                    }
                                    ToastClass.showShortToast(context, jsonObject.optString("message"));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }, "UPDATE_CART_ITEM", true).execute(WebServiceUrl.UPDATE_CART_ITEM);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        holder1.tv_quantity_cart.setText(resultFoundModels.get(position).getQtyRequired());
        holder1.tv_plus_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int qty = Integer.parseInt(holder1.tv_quantity_cart.getText().toString());
                    qty = qty + 1;
                    ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
                    nameValuePairs.add(new BasicNameValuePair("cart_id", resultFoundModels.get(position).getCartId()));
                    nameValuePairs.add(new BasicNameValuePair("qty_required", String.valueOf(qty)));
                    final int finalQty = qty;
                    new WebServiceBase(nameValuePairs, context, new WebServicesCallBack() {
                        @Override
                        public void onGetMsg(String apicall, String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.optInt("status") == 1) {
                                    holder1.tv_quantity_cart.setText(String.valueOf(finalQty));
                                }
                                ToastClass.showShortToast(context, jsonObject.optString("message"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, "UPDATE_CART_ITEM", true).execute(WebServiceUrl.UPDATE_CART_ITEM);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        holder1.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair("cart_id", resultFoundModels.get(position).getCartId()));
                new WebServiceBase(nameValuePairs, context, new WebServicesCallBack() {
                    @Override
                    public void onGetMsg(String apicall, String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.optInt("status") == 1) {
                                resultFoundModels.remove(position);
                                notifyDataSetChanged();
                            }
                            ToastClass.showShortToast(context, jsonObject.optString("message"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, "UPDATE_CART_ITEM", true).execute(WebServiceUrl.DELETE_CART_ITEM);
            }
        });

        if(resultFoundModels.get(position).getBrand_detail()!=null){
            holder1.ll_brand.setVisibility(View.VISIBLE);
            holder1.tv_brand.setText(resultFoundModels.get(position).getBrand_detail().getBrandName());
        }else{
            holder1.ll_brand.setVisibility(View.GONE);
        }

        if(resultFoundModels.get(position).getSize_detail()!=null){
            holder1.ll_size.setVisibility(View.VISIBLE);
            holder1.tv_size.setText(resultFoundModels.get(position).getSize_detail().getSizeName());
        }else{
            holder1.ll_size.setVisibility(View.GONE);
        }

        Picasso.with(context).load(WebServiceUrl.IMAGEBASEURL + resultFoundModels.get(position).getProductSizeImage()).
                into(holder1.iv_item_image_cart);

    }

    @Override
    public int getItemCount() {
        return resultFoundModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_item_image_cart)
        ImageView iv_item_image_cart;
//        @BindView(R.id.iv_delete)
//        ImageView iv_delete;
        @BindView(R.id.tv_item_name)
        AppCompatTextView tv_item_name;
        @BindView(R.id.tv_minus_item)
        AppCompatTextView tv_minus_item;
        @BindView(R.id.tv_quantity_cart)
        AppCompatTextView tv_quantity_cart;
        @BindView(R.id.tv_plus_item)
        AppCompatTextView tv_plus_item;

        @BindView(R.id.ll_brand)
        LinearLayout ll_brand;
        @BindView(R.id.tv_brand)
        TextView tv_brand;
        @BindView(R.id.ll_size)
        LinearLayout ll_size;
        @BindView(R.id.tv_size)
        TextView tv_size;
        @BindView(R.id.iv_delete)
        ImageView iv_delete;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
