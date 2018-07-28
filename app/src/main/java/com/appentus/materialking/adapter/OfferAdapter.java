package com.appentus.materialking.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.appentus.materialking.pojo.BidInfoPOJO;
import com.appentus.materialking.views.activity.RecommendedProductDetailActivity;
import com.appentus.materialking.views.activity.SellerDetailActivity;
import com.appentus.materialking.webservice.WebServiceBase;
import com.appentus.materialking.webservice.WebServiceUrl;
import com.appentus.materialking.webservice.WebServicesCallBack;
import com.bumptech.glide.Glide;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Hp on 2/7/2018.
 */

public class OfferAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<BidInfoPOJO> items = new ArrayList<>();

    public OfferAdapter(Context context, List<BidInfoPOJO> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_offer_items, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder holder1 = (ViewHolder) holder;
        holder1.tvViewCompleteName.setText(items.get(position).getName());
        holder1.tvViewCompleteAmount.setText("Offers: " + items.get(position).getPriceHave() + " INR");

        Glide.with(context)
                .load(WebServiceUrl.IMAGEBASEURL + items.get(position).getImage())
                .into(holder1.iv_product_image);

        if (items.get(position).getRecommendedBidInfoPOJO() != null) {
            holder1.ll_rec.setVisibility(View.VISIBLE);

            holder1.tv_rec_name.setText(items.get(position).getRecommendedBidInfoPOJO().getName());
            holder1.tv_rec_price.setText(items.get(position).getRecommendedBidInfoPOJO().getPriceHave());

            Glide.with(context)
                    .load(WebServiceUrl.IMAGEBASEURL + items.get(position).getRecommendedBidInfoPOJO().getImage())
                    .into(holder1.iv_rec_image);

            holder1.ll_rec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, RecommendedProductDetailActivity.class);
                    intent.putExtra("bid_product_recommended_id", items.get(position).getRecommendedBidInfoPOJO().getBidProductRecommendedId());
                    context.startActivity(intent);
                }
            });

        } else {
            holder1.ll_rec.setVisibility(View.GONE);
        }

        if(items.get(position).getSeller_status().equalsIgnoreCase("0")){
            holder1.tv_seller_status.setText("PENDING");
        }else if(items.get(position).getSeller_status().equalsIgnoreCase("1")){
            holder1.tv_seller_status.setText("ACCEPTED");
            holder1.tv_seller_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, SellerDetailActivity.class);
                    intent.putExtra("seller_id",items.get(position).getSeller_id());
                    context.startActivity(intent);
                }
            });
        }else if(items.get(position).getSeller_status().equalsIgnoreCase("-1")){
            holder1.tv_seller_status.setText("DECLINED");
        }

        holder1.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (items.get(position).getSeller_status().equalsIgnoreCase("0")) {
                    ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
                    nameValuePairs.add(new BasicNameValuePair("id", items.get(position).getOffer_id()));
                    new WebServiceBase(nameValuePairs, context, new WebServicesCallBack() {
                        @Override
                        public void onGetMsg(String apicall, String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.optInt("status") == 1) {
                                    items.remove(position);
                                    notifyDataSetChanged();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, "DELET_OFFER", true).execute(WebServiceUrl.DELETE_OFFER_DATA);
                } else {
                    ToastClass.showShortToast(context,"Seller confirmed the bid");
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_view_complete_name)
        AppCompatTextView tvViewCompleteName;
        @BindView(R.id.tv_view_complete_amount)
        AppCompatTextView tvViewCompleteAmount;
        @BindView(R.id.iv_product_image)
        ImageView iv_product_image;
        @BindView(R.id.iv_delete)
        ImageView iv_delete;
        @BindView(R.id.iv_rec_image)
        ImageView iv_rec_image;
        @BindView(R.id.tv_rec_name)
        TextView tv_rec_name;
        @BindView(R.id.tv_rec_price)
        TextView tv_rec_price;
        @BindView(R.id.ll_rec)
        LinearLayout ll_rec;
        @BindView(R.id.tv_seller_status)
        TextView tv_seller_status;
        @BindView(R.id.tv_seller_details)
        TextView tv_seller_details;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
